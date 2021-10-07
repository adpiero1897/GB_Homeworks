package Services;

import Models.Cat;
import Models.Plate;

public class CatsFeedingAll {


    public void feeding(Cat[] catsArr, Plate plate, int added)
    {
        for(int i = 0; i < catsArr.length; i++) {
            catsArr[i].eat(plate);                             //попутно кормим их из тарелки с кормом
            //5...выведем информацию о сытости котов
            System.out.println(catsArr[i].getisFull() ? "Кот_" + (i + 1) + " наелся" : "Кот_" + (i + 1) + " не наелся");
            plate.info();               //смотрим, сколько осталось корма на тарелке

            if (!catsArr[i].getisFull()) {    //если котик не наелся, нужно добавить 10 еды в тарелку
                plate.addFood(added);
                plate.info();
                i--;
            }
        }
    }
}
