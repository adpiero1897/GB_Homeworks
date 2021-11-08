import Competitors.Cat;
import Competitors.Competitor;
import Competitors.Human;
import Competitors.Robot;
import Lets.Let;
import Lets.RunTrack;
import Lets.Wall;


public class Main_Class {
    //Создали пустой main класс
    public static void main(String[] args) {

        Competitor[] competitors = new Competitor[7];   //Создали массив из 7 участников
        competitors[0] = new Cat("Геральт", 200, 1.5);
        competitors[1] = new Human("Павел", 1500, 1.2);
        competitors[2] = new Robot("R2D2", 1000, 0.3);
        competitors[3] = new Cat("Шустрик", 1700, 2);
        competitors[4] = new Robot("T800", 2000, 3);
        competitors[5] = new Robot("Jumper-2000", 250, 9.8);
        competitors[6] = new Human("Саша", 12000, 1.6);
        //Заполнили массив участников (их 7) "полосы препятствий"
        Let[] lets = new Let[6];    //Создали массив из 6 препятсвий
        lets[0] = new Wall(0.1);
        lets[1] = new RunTrack(100);
        lets[2] = new Wall(0.5);
        lets[3] = new RunTrack(500);
        lets[4] = new Wall(1);
        lets[5] = new RunTrack(1600);
        //Заполнили массив препятствий (их 6)
        for (int i = 0; i < competitors.length; i++) {
            int j = 0;  //Вводим переменную индекса внутреннего цикла препятствий ЗАРАНЕЕ, чтобы потом проверить success()
            for (; j < lets.length; j++) {
                if (lets[j] instanceof Wall) {
                    if(!competitors[i].isJumpable(lets[j].getLetValue())) { //Если стена, то нужно прыгать
                        break;
                    }
                } else {
                    if(!competitors[i].isRunable(lets[j].getLetValue())) { //Если дорожка, то нужно бежать
                        break;
                    }
                }
            }
            if(j == lets.length) {
                competitors[i].success();   //Празднуем прохождение полосы препятствий
            }
        }
    }
}
