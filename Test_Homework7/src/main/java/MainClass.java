import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;

public class MainClass {

    /*1. Создать класс, который может выполнять «тесты», в качестве тестов выступают классы с наборами методов с
    аннотациями @Test. Для этого у него должен быть статический метод start(), которому в качестве параметра передается
     или объект типа Class, или имя класса. Из «класса-теста» вначале должен быть запущен метод с аннотацией
     @BeforeSuite, если такой имеется, далее запущены методы с аннотациями @Test, а по завершению всех тестов – метод с
      аннотацией @AfterSuite. К каждому тесту необходимо также добавить приоритеты (int числа от 1 до 10), в
      соответствии с которыми будет выбираться порядок их выполнения, если приоритет одинаковый, то порядок не имеет
      значения. Методы с аннотациями @BeforeSuite и @AfterSuite должны присутствовать в единственном экземпляре, иначе
      необходимо бросить RuntimeException при запуске «тестирования».*/

    public static void main(String[] args) {

        start(TestClass.class); //Вводим сюда, какой класс будем "тестировать"
        start(TestClass2.class);    //"Тестируем" класс с лишним @Before методом

    }


    public static void start(Class clazz) throws RuntimeException {
        try {
            LinkedList<Method> methodsList;

            methodsList = getSortedMethodsList(clazz);  //этим сформируем список методов в отсортированном порядке

            for (Method m : methodsList) {
                int modifiers = m.getModifiers();
                boolean doPublic = false;
                //Это условие не должно использоваться (т.к. приватные методы не тестируются обычно: не ставят @Test им)
                if (!Modifier.isPublic(modifiers)) {
                    m.setAccessible(true);  //на случай, если вдруг метод будет приватным
                    doPublic = true;
                }
                //для формирования массива параметров/аргументов в вызываемые тестом методы
                Class[] methodParameterTypes = m.getParameterTypes();
                Object[] methodParams = new Object[methodParameterTypes.length];  //под тест будем передавать null аргументы

                Constructor[] constructors = clazz.getConstructors();
                for (Constructor constructor : constructors) {  //Для всех видов конструкторов класса тестим методы

                    Class[] constructorParameterTypes = constructor.getParameterTypes();
                    Object[] constructorParams = new Object[constructorParameterTypes.length];  //null параметры опять же

                    m.invoke(constructor.newInstance(constructorParams), methodParams);
                    if (doPublic) {
                        m.setAccessible(false);  //на случай, если вдруг метод был приватным, вернем его недоступность
                    }
                }
            }
        } catch (RuntimeException | InstantiationException | InvocationTargetException |
                IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static LinkedList<Method> getSortedMethodsList(Class clazz) throws RuntimeException {
        Method[] methods = clazz.getDeclaredMethods();
        LinkedList<Method> methodsList = new LinkedList<>();

        int countBeforeSuites = 0;
        int countAfterSuites = 0;
        int countTestMethods = 0;

        for (Method m : methods) {
            Annotation a;
            if ((a = m.getAnnotation(BeforeSuite.class)) != null) {
                methodsList.addFirst(m);    //Добавляем первым в очередь метод с аннотацией Before
                countBeforeSuites++;    //Счетчик, не позволяющий более 1 Before-метода увеличиваем
                if (countBeforeSuites > 1) {
                    throw new RuntimeException("Обнаружено более одного BeforeSuite метода");
                    //выкидываем исключение, если больше 1 объявлено Before-метода
                }
            } else if ((a = m.getAnnotation(AfterSuite.class)) != null) {
                methodsList.addLast(m);    //Добавляем последним в очередь метод с аннотацией After
                countAfterSuites++;    //Счетчик, не позволяющий более 1 After-метода увеличиваем
                if (countAfterSuites > 1) {
                    throw new RuntimeException("Обнаружено более одного AfterSuite метода");
                    //выкидываем исключение, если больше 1 объявлено After-метода
                }
            } else if ((a = m.getAnnotation(Test.class)) != null) {
                //Для методов с аннотацией @Test тоже сделаем условие включения в лист методов на исполнение
                if (countTestMethods == 0) {    //Если это первый тестовый метод в листе, то просто добавим его
                    methodsList.add(countBeforeSuites, m);
                    countTestMethods++;
                } else {  //Иначе ищем его сортировочный индекс вхождения
                    int sortIndex = ((Test) a).value();

                    for (int i = countBeforeSuites; ; i++) {
                        //Смотрим сортировочные индексы всех методов в листе (ограничиваемся на методы с аннотацией @Test
                        Annotation annotation = methodsList.get(i).getAnnotation(Test.class);
                        if (annotation != null && sortIndex <= ((Test) annotation).value()) {
                            methodsList.add(i, m);   //Добавляем метод в список на вызов УЖЕ с его индексом сортировки
                            countTestMethods++;
                            break;
                        }
                        if (i + 1 == methodsList.size() - countAfterSuites) {
                            //Если сортировочный индекс этого метода больше, чем индексы ВСЕХ предшествующих, всё равно...
                            methodsList.add(i + 1, m);   //Добавляем метод в список на вызов УЖЕ с его индексом сортировки
                            countTestMethods++;
                            break;
                        }
                    }
                }
            }
        }
        return methodsList;
    }

}
