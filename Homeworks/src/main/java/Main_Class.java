public class Main_Class {

    public static void main(String[] args)
    {
        printThreeWords();
        checkSumSign();
        printColor();
        compareNumbers();
    }

    public static void printThreeWords()
    {
        System.out.print("Orange\nBanana\nApple\n");
    }

    public static void checkSumSign()
    {
        int a = 666;
        int b = -100500;
        if(a+b >= 0)
        {
            System.out.println("Сумма положительна");
        }
        else
        {
            System.out.println("Сумма отрицательна");
        }
    }

    public static void printColor()
    {
        int value = 68;

        if(value <=0)
        {
            System.out.println("Красный");
        }
        else if(value <= 100)
        {
            System.out.println("Желтый");
        }
        else
        {
            System.out.println("Зеленый");
        }
    }

    public static void compareNumbers()
    {
        int a = 263282;
        int b = 93268;

        if(a >= b)
        {
            System.out.println("a>=b");
            System.out.println(a+">="+b); // Добавляю ко всему вывод значений переменных, участвующих в сравнении
        }
        else
        {
            System.out.println("a<b");
            System.out.println(a+"<"+b); // Добавляю ко всему вывод значений переменных, участвующих в сравнении
        }
    }
}
