import Models.Cat;
import Models.Plate;
import Services.CatsFeedingAll;

import java.util.Random;

public class Main_Class {

    //Создали пустой main класс
    public static void main(String[] args) {
        Random rand = new Random();
        /*5. Создать массив котов и тарелку с едой, попросить всех котов покушать из этой тарелки и потом
        вывести информацию о сытости котов в консоль.*/
        Plate plate = new Plate(100);    //На тарелке 100 корма
        Cat[] catsArr = new Cat[10]; //создаем массив из 10 котов, допустим

        for(int i = 0; i < catsArr.length; i++) {
            catsArr[i] = new Cat("Кот_" + (i + 1), rand.nextInt(20)); //Инициализируем массив котов
        }
        CatsFeedingAll CatsFeeding = new CatsFeedingAll();
        CatsFeeding.feeding(catsArr,plate,10); //Вызовем метод массовой кормежки котов с добавкой по 10 ед. корма

    }

}
