import My_Exceptions.MyArrayDataException;
import My_Exceptions.MyArraySizeException;

public class Main_Class {
    //Создали пустой main класс

    /*Напишите метод, на вход которого подаётся двумерный строковый массив размером 4х4. При подаче массива другого
    размера необходимо бросить исключение MyArraySizeException.
    Далее метод должен пройтись по всем элементам массива, преобразовать в int и просуммировать. Если в каком-то
    элементе массива преобразование не удалось (например, в ячейке лежит символ или текст вместо числа), должно быть
    брошено исключение MyArrayDataException с детализацией, в какой именно ячейке лежат неверные данные. В методе
    main() вызвать полученный метод, обработать возможные исключения MyArraySizeException и MyArrayDataException и
    вывести результат расчета (сумму элементов, при условии что подали на вход корректный массив).
    Заметка: Для преобразования строки к числу используйте статический метод класса Integer:
    Integer.parseInt(сюда_подать_строку);*/

    public static void main(String[] args) {

        //Для 1-го теста подадим на вход Недопустимый для корректной работы метода массив №1, который пустой
        String[][] incorrectArr1 = new String[0][];
        try {
            System.out.println("Сумма элементов массива равна " + sumArrOnlyInt4x4(incorrectArr1));
        } catch (MyArraySizeException | MyArrayDataException myEx) {
            myEx.printStackTrace();
        }

        //Для 2-го теста подадим на вход Недопустимый для корректной работы метода массив №2, где размер не 4x4
        String[][] incorrectArr2 = {{"0", "1", "2", "3", "4"}, {"-1", "2", "3", "4"}, {"0", "-1", "-2", "-3", "5"},
                {"1", "2", "3"}};
        try {
            System.out.println("Сумма элементов массива равна " + sumArrOnlyInt4x4(incorrectArr2));
        } catch (MyArraySizeException | MyArrayDataException myEx) {
            myEx.printStackTrace();
        }
        //Для 3-го теста подадим на вход Недопустимый для корректной работы метода массив №3, где числа не в формате int
        String[][] incorrectArr3 = {{"1", "2", "3", "31"}, {"-1", "2", "три", "4"}, {"ноль", "-1.1", "-2.2", "-3"},
                {"О", "два", "e", "3.3"}};
        try {
            System.out.println("Сумма элементов массива равна " + sumArrOnlyInt4x4(incorrectArr3));
        } catch (MyArraySizeException | MyArrayDataException myEx) {
            myEx.printStackTrace();
        }
        //Для 4-го теста подадим на вход корректный массив
        String[][] incorrectArr4 = {{"0", "1", "2", "3"}, {"-1", "2", "3", "4"}, {"0", "-1", "-2", "5"},
                {"0", "0", "3", "3"}};
        try {
            System.out.println("Сумма элементов массива равна " + sumArrOnlyInt4x4(incorrectArr4));
        } catch (MyArraySizeException | MyArrayDataException myEx) {
            myEx.printStackTrace();
        }

    }

    public static int sumArrOnlyInt4x4(String[][] inputArr) throws MyArraySizeException, MyArrayDataException {
        if (inputArr.length != 4 || inputArr[0].length != 4 || inputArr[1].length != 4 || inputArr[2].length != 4 ||
                inputArr[3].length != 4) {
            throw new MyArraySizeException("Недопустимый размер массива. Для метода допустим только массив 4x4");
        }
        //будем вычислять сумму элементов массива, попутно проверяя на исключения формат данных в его ячейках-строках
        int sumArr = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                try {
                    sumArr += Integer.parseInt(inputArr[i][j]);
                } catch (NumberFormatException ex) {
                    throw new MyArrayDataException("Не удалось корректно преобразовать \"" + inputArr[i][j] + "\" в " +
                            "целое число. Проблемное значение во входном массиве находится в столбце [" + (i + 1) + "]," +
                            " в строке [" + (j + 1) + "].");
                    //Местоположение элемента выводится в понятном для пользователя виде: (отчет 1 строки/столбца)
                }
            }
        }
        return sumArr;
    }
}
