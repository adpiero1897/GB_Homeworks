import java.util.Random;

public class Main_Class {
    public static void main(String[] args) {
        //1. Задать целочисленный массив, состоящий из элементов 0 и 1.
        //Например: [ 1, 1, 0, 0, 1, 0, 1, 1, 0, 0 ]. С помощью цикла и условия заменить 0 на 1, 1 на 0;
        int[] arr1 = {1, 1, 0, 1, 0, 0};   //Исходный массив на вход
        arr1 = arrayInversion(arr1);
        System.out.println("1. ");  //Номер практического задания
        for (int i = 0; i < arr1.length; i++) {      //Вывод элементов массива в консоль после преобразования
            System.out.printf(arr1[i] + " ");
        }
        //2. Задать пустой целочисленный массив длиной 100. С помощью цикла заполнить его значениями 1 2 3 4 5 6 7 8…100
        int[] arr2 = new int[100];
        arr2 = arrayFilling(arr2);
        System.out.println();
        System.out.println("2. "); //Номер практического задания
        for (int i = 0; i < arr2.length; i++) {      //Вывод элементов массива в консоль после преобразования
            System.out.printf(arr2[i] + " ");
        }

        //3. Задать массив [ 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 ] пройти по нему циклом, и числа меньшие 6 умножить на 2
        int[] arr3 = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        arr3 = arrayLower6x2(arr3);
        System.out.println();
        System.out.println("3. "); //Номер практического задания
        for (int i = 0; i < arr3.length; i++) {      //Вывод элементов массива в консоль после преобразования
            System.out.printf(arr3[i] + " ");
        }

        /*4. Создать квадратный двумерный целочисленный массив (количество строк и столбцов одинаковое), и с помощью
        цикла(-ов) заполнить его диагональные элементы единицами (можно только одну из диагоналей, если обе сложно).
         Определить элементы одной из диагоналей можно по следующему принципу: индексы таких элементов равны, то есть
         [0][0], [1][1], [2][2], …, [n][n]; */
        int[][] multiArr4 = new int[10][10];
        multiArr4 = arrayDiagonal(multiArr4);
        System.out.println();
        System.out.println("4. "); //Номер практического задания
        for (int i = 0; i < multiArr4.length; i++) {  //Вывод элементов массива в консоль после преобразования
            for (int j = 0; j < multiArr4[i].length; j++) {
                System.out.print(multiArr4[i][j] + "  ");
            }
            System.out.println();
        }

        /*5. Написать метод, принимающий на вход два аргумента: len и initialValue, и возвращающий одномерный массив
         типа    int длиной len, каждая ячейка которого равна initialValue;*/

        int[] arr5 = arrayInitialization(10, 125815);
        System.out.println();
        System.out.println("5. "); //Номер практического задания
        for (int i = 0; i < arr5.length; i++) {      //Вывод элементов массива в консоль после преобразования
            System.out.printf(arr5[i] + " ");
        }

        //6. * Задать одномерный массив и найти в нем минимальный и максимальный элементы ;
        arrayMaxMinOutput();

        /*7. ** Написать метод, в который передается не пустой одномерный целочисленный массив, метод должен вернуть
        true, если в массиве есть место, в котором сумма левой и правой части массива равны.*/
        int[] arr7 = {1, 2, -1, 0, -2, 3, 1, 0, 2, -1, -1}; //Пример массива, в котором первые 3-4 левых элемента равны
        // остальным
        System.out.println("7. "); //Номер практического задания
        if (isEqualArrayParts(arr7)) {
            System.out.println("В данном массиве ЕСТЬ ось эквивалентности левой и правой части");
        } else {
            System.out.println("В данном массиве НЕТ оси эквивалентности левой и правой части");
        }

        /*8. *** Написать метод, которому на вход подается одномерный массив и число n (может быть положительным, или
        отрицательным), при этом метод должен сместить все элементы массива на n позиций. Элементы смещаются циклично.
        Для усложнения задачи нельзя пользоваться вспомогательными массивами. Примеры: [ 1, 2, 3 ] при n = 1 (на один
        вправо) -> [ 3, 1, 2 ]; [ 3, 5, 6, 1] при n = -2 (на два влево) -> [ 6, 1, 3, 5 ]. При каком n в какую сторону
        сдвиг можете выбирать сами */
        int[] arr8 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};//Тестовый массив на вход, чтобы легко проверить метод
        arr8 = arrayShifter(arr8, -29);
        System.out.println("8. "); //Номер практического задания
        for (int i = 0; i < arr8.length; i++) {      //Вывод элементов массива в консоль после преобразования
            System.out.printf(arr8[i] + " ");
        }
    }

    public static int[] arrayInversion(int[] arr_inverse) {
        for (int i = 0; i < arr_inverse.length; i++) {
            if (arr_inverse[i] == 0) {
                arr_inverse[i] = 1;
            } else if (arr_inverse[i] == 1) {
                arr_inverse[i] = 0;
            } else {  //Проверка на недопустимые числа в массиве (В задании не было, но всякие входные данные бывают...)
                System.out.println("Недопустимое число " + arr_inverse[i] + " под индексом " + i + " в исходном массиве");
            }
        }
        return arr_inverse;
    }


    public static int[] arrayFilling(int[] arr_fill) {
        for (int i = 0; i < arr_fill.length; i++) {
            arr_fill[i] = i + 1;
        }
        return arr_fill;
    }

    public static int[] arrayLower6x2(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 6) {
                arr[i] *= 2;
            }
        }
        return arr;
    }

    public static int[][] arrayDiagonal(int[][] multiArr) {
        for (int ij = 0; ij < multiArr.length; ij++) {
            multiArr[ij][ij] = 1;
            //Заполнили диагональ сверху вниз, теперь заполним диагональ обратную
            multiArr[multiArr.length - 1 - ij][ij] = 1;
        }
        return multiArr;
    }

    public static int[] arrayInitialization(int len, int initialValue) {
        int[] arr_initial = new int[len];
        for (int i = 0; i < arr_initial.length; i++) {
            arr_initial[i] = initialValue;
        }
        return arr_initial;
    }

    public static void arrayMaxMinOutput() {
        Random rand = new Random();
        int[] arr6 = new int[12]; //Будем находить максимум и минимум из 12 значений, допустим
        for (int i = 0; i < arr6.length; i++) {
            arr6[i] = rand.nextInt(1000);//Сгенерируем натуральные числа до 1000
        }

        int min = arr6[0];      //Переменные в которые будем находить минимум и максимум из элементов массива
        int max = arr6[0];

        for (int i = 1; i < arr6.length; i++) {     //Можно начать с [1] эелемента, т.к. [0] уже записан в min и max
            min = Math.min(arr6[i], min);
            max = Math.max(arr6[i], max);
        }
        System.out.println(); //Номер практического задания
        System.out.println("6. Минимальное значение элемента массива = " + min);
        System.out.println("Максимальное значение элемента массива = " + max);
    }

    public static boolean isEqualArrayParts(int[] arr7) {
        //Считаем сумму всех элементов массива (в начале это будет сумма правой части)
        int right_sum = 0;

        for (int i = 0; i < arr7.length; i++) {
            right_sum += arr7[i];
        }

        int left_sum = 0;
        for (int i = 0; i < arr7.length; i++) {
            left_sum += arr7[i];
            right_sum -= arr7[i];
            //элементы переходят поочередно слева из правой суммы в левую и сравниваются
            if (left_sum == right_sum) {
                return true;
            }
        }
        return false;
    }

    public static int[] arrayShifter(int[] arr8, int shift_input) {
        int checksum = 0; //Число для проверки количества сдвинутых элементов. В конце должно быть = размеру массива
        int shift = shift_input % arr8.length;    //shift - реальный сдвиг
        if (shift < 0) {     //Выразим сдвиг влево через сдвиг вправо (для отрицательных значений сдвига)
            shift = arr8.length + shift;
        }

        if (shift != 0) {        //Если пользователь укажет нулевой сдвиг, то дальнейшее не имеет смысла
            for (int outside_index = 0; checksum != arr8.length; outside_index++) {   //пока все числа не сдвинутся
                int num0_buffer;    //Скидываем сюда значение элемента, который сейчас является точкой отсчета
                int i = outside_index;
                num0_buffer = arr8[i];
                int shift_index = (i + shift) % arr8.length;//Здесь будет хранится последний сдвигаемый индекс из цикла ниже
                do {            //Сдвигаем весь блок чисел, замыкающихся начальным числом(num0) блока
                    arr8[i] = arr8[shift_index]; //Значение переход на новый, сдвинутый, индекс
                    checksum++; //Считаем +1 изменённый (сдвинутый) элемент
                    i = shift_index;    //циклично переходим между элементами блока на следующий индекс
                    shift_index = (i + shift) % arr8.length; /* на следующий индекс */
                }
                while (shift_index != outside_index);   //Блок заканчивает движение по сдвигу, как только зацикливается
                arr8[i] = num0_buffer; //возвращаем из буфера начальное, уже измененное значение num0
                checksum++;
            }
        }
        System.out.println("8. Было сдвинуто элементов массива: " + checksum);
        return arr8;
    }
}
