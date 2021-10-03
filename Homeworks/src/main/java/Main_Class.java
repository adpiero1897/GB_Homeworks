public class Main_Class {
    public static void main(String[] args) {
        /* if(IsSumBetween1020(13,8)) {
            System.out.println("Сумма лежит в промежутке от 10 до 20");
        }
        else {
            System.out.println("Сумма НЕ лежит в промежутке от 10 до 20");
        }  */
        //Расскоментрировать код, чтобы проверить работу метода 1

        // printSignOfNumber(88888);
        //Раскомемнтрировать код, чтобы проверить работу метода 2

        /* if(checkSignOfNumber(-11115)) {
            System.out.println("Число отрицательное");
        }
        else {
            System.out.println("Число неотрицательное (положительное)");
        }  */
        //Расскоментрировать код, чтобы проверить работу метода 3

        //printStringRepeat("Вот она, Java моей мечты, вот она, вот она",3);
        //Расскоментрировать код, чтобы проверить работу метода 4

        /*if (isLeapYear(2000))
            System.out.println("Год високосный");
        else
            System.out.println("Год НЕ високосный");*/
        //Расскоментрировать код, чтобы проверить работу метода 5*
    }

    public static boolean isSumBetween1020(int num1, int num2) {
        return num1 + num2 >= 10 && 20 >= num1 + num2;
    }

    public static void printSignOfNumber(int checkNum) {
        if (checkNum >= 0)
            System.out.println(checkNum + " Это положительное(неотрицательное) число");
        else
            System.out.println(checkNum + " Это отрицательное число");
    }

    public static boolean checkSignOfNumber(int checkNum) {
        return checkNum < 0;
    }

    public static void printStringRepeat(String outString,int repeat) {
        for (;repeat > 0;repeat--)
            System.out.println(outString);
    }

    public static boolean isLeapYear(int year) {
        if (year % 4 == 0 && (year % 100 != 0 || year % 400 ==0)) {
            return true;
        }
        return false;
    }
}
