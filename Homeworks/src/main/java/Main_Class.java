import FruitBoxesQuest.Apple;
import FruitBoxesQuest.Box;
import FruitBoxesQuest.FruitTypes;
import FruitBoxesQuest.Orange;

import java.util.ArrayList;
import java.util.Arrays;

public class Main_Class {
    //1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
    //2. Написать метод, который преобразует массив в ArrayList;
    //3. Большая задача:
    //a. Есть классы Fruit -> Apple, Orange;(больше фруктов не надо)
    //b. Класс Box в который можно складывать фрукты, коробки условно сортируются по типу фрукта, поэтому в одну коробку
    // нельзя сложить и яблоки, и апельсины;
    //c. Для хранения фруктов внутри коробки можете использовать ArrayList;
    //d. Сделать метод getWeight() который высчитывает вес коробки, зная количество фруктов и вес одного фрукта(вес
    // яблока - 1.0f, апельсина - 1.5f, не важно в каких это единицах);
    //e. Внутри класса коробка сделать метод compare, который позволяет сравнить текущую коробку с той, которую подадут
    // в compare в качестве параметра, true - если их веса равны, false в противном случае(коробки с яблоками мы можем
    // сравнивать с коробками с апельсинами);
    //f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку(помним про сортировку
    // фруктов, нельзя яблоки высыпать в коробку с апельсинами), соответственно в текущей коробке фруктов не остается, а
    // в другую перекидываются объекты, которые были в этой коробке;
    //g. Не забываем про метод добавления фрукта в коробку.
    public static void main(String[] args) {
        //Протестим метод_1 на String-значениях
        Exercise1<String> test1Ex1 = new Exercise1<>(new String[]{"Ноль", "Один", "Два", "Три", "Четыре", "Пять"});
        //Поменяем местами 1 и 4 элементы массива
        test1Ex1.rotationElements(1, 4);
        System.out.println("Выведем итоговый массив после преобразования:");
        System.out.println(Arrays.toString(test1Ex1.getArr()));
        //Протестим метод_1 на float-значениях
        Exercise1<Float> test2Ex1 = new Exercise1<>(new Float[]{0.61F, 1F, 2.71F, 3.14F, 4000000000F, 511F});
        //Поменяем местами 2 и 0 элементы массива
        test2Ex1.rotationElements(2, 0);
        System.out.println("Выведем итоговый массив после преобразования:");
        System.out.println(Arrays.toString(test2Ex1.getArr()));

        //Протестируем метод_2 на массиве данных символьного типа
        Exercise2<Character> test1Ex2 = new Exercise2<>();
        ArrayList<Character> testList1 = test1Ex2.arrToArrayList(new Character[]{'н', 'о', 'д', 'т'});

        //Тест метода_3
        Box<Apple> appleBox = new Box<>();
        Box<Orange> orangeBox = new Box<>();
        Box<Apple> appleBox2 = new Box<>();
        appleBox.addFruit(new Apple(FruitTypes.APPLE), 6);
        orangeBox.addFruit(new Orange(FruitTypes.ORANGE), 4);
        appleBox2.addFruit(new Apple(FruitTypes.APPLE), 10);

        //Выводы методов для теста_3 задания
        System.out.println("Вес всех фруктов в коробке с апельсинами: " + orangeBox.getSumWeight());
        System.out.println("Вес всех фруктов в коробке с яблоками: " + appleBox.getSumWeight());
        System.out.println("Вес всех фруктов в коробке_2 c яблоками: " + appleBox2.getSumWeight());
        System.out.println("Условие равенства веса коробок апельсинов и яблок - " + appleBox.compare(orangeBox));

        appleBox.fruitTransfer(appleBox2);

        System.out.println("Вес всех фруктов в коробке_2 c яблоками: " + appleBox2.getSumWeight());
        System.out.println("Вес всех фруктов в коробке с яблоками: " + appleBox.getSumWeight());
        System.out.println("Условие равенства веса коробок апельсинов и яблок - " + appleBox.compare(orangeBox));
    }


}
