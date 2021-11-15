import java.util.ArrayList;

/*
1.  массива с набором слов (10-20 слов, должны встречаться повторяющиеся). Найти и вывести список уникальных слов, из
которых состоит массив (дубликаты не считаем). Посчитать сколько раз встречается каждое слово.

2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров. В этот
телефонный справочник с помощью метода add() можно добавлять записи. С помощью метода get() искать номер телефона по
фамилии. Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев), тогда при
запросе такой фамилии должны выводиться все телефоны.
*/

public class Main_Class {
    //Создали пустой main класс
    public static void main(String[] args) {
        //1-е задание
        System.out.println("Задание №1");
        //Инициализируем входной массив
        String[] inputArr = {"Odin", "Thor", "Cupid", "Terra", "Aphrodite", "Vulcan", "Loki", "Terra", "Zeus",
                "Artemis", "Hercules", "Amaterasu", "Zeus", "Thor", "Terra", "Zeus", "Thor", "Cupid", "Odin", "Thor", "Cupid"};

        //метод 1-го задания
        uniqueWordsCounter(inputArr);

        //ко 2-ому заданию
        System.out.println("Задание №2");
        PhoneBook phoneBook = new PhoneBook();
        //Для теста заполним нашу телефоннуб книгу несколькоими номерами, где Иванов не один
        phoneBook.add("Иванов", "+79186179015");
        phoneBook.add("Кортенко", "+79528784541");
        phoneBook.add("Палтусов", "+78612231128");
        phoneBook.add("Иванов", "+7993512719");
        phoneBook.add("Драгунский", "+35781649");
        phoneBook.add("Иванов", "+78591638419");

        //Посмотрим в консоле телефоны всех Ивановых из нашего справочника
        System.out.println(phoneBook.get("Иванов"));
    }


    public static void uniqueWordsCounter(String[] inputWordsArr){
        ArrayList<UniqueWord> uniqueWords = new ArrayList<>(20);
        //Создадим лист уникальных слов (этот объект создан нами и считает вес (количеством повторений))
        for (String s : inputWordsArr) {   //для всех слов из входного массива
            boolean isUniqueWord = true;
            for (UniqueWord u : uniqueWords) {  // для всех уникальных слов-объектов листа
                if (u.getWord().equals(s)) {
                    u.incValue();   //такое слово уже записано: повышаем его счетчик на +1
                    isUniqueWord = false;
                    break;
                }
            }
            if(isUniqueWord) {    //Если слово ранее не повторялось
                uniqueWords.add(new UniqueWord(s)); //Добавляем его в список
            }
        }

        for (UniqueWord u : uniqueWords) {   //для всех уникальных организуем вывод инфо о весе в консоль
            System.out.println(u.getWord() + ": " + u.getValue() + " раз(а) встречалось в исходном массиве");
        }
    }

}





