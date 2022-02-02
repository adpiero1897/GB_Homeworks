import RacingObjects.Car;
import RacingObjects.Race;
import RacingObjects.Road;
import RacingObjects.Tunnel;

/*Организуем гонки:
        Все участники должны стартовать одновременно, несмотря на то, что на подготовку у каждого из них уходит разное
        время.
        В туннель не может заехать одновременно больше половины участников (условность).
        Попробуйте всё это синхронизировать.
        Только после того как все завершат гонку, нужно выдать объявление об окончании.
        Можете корректировать классы (в т.ч. конструктор машин) и добавлять объекты классов из пакета util.concurrent.
        Пример выполнения кода до корректировки:*/

public class RacingMainClass {
    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        //Сделаем 2 тоннеля в 80 м и 120м и + 3 участка дороги для гонки
        Race race = new Race(CARS_COUNT, new Road(60), new Tunnel(80, CARS_COUNT / 2), new Road(40),
                new Tunnel(120, CARS_COUNT / 2), new Road(100));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        try {
            race.getCdlFinish().await();    //ждем, пока все финишируют, опустив счетчик до нуля
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!! Результаты участников:");
        String championName = "";    //будем искать номер победителя
        Long monTime = null;  //победитель будет искаться по минимальному времени здесь
        for (Car car : cars) {
            System.out.println("Время участника " + car.getName() + ": " + car.getFullRaceTime() + " мс.");
            if (monTime == null || car.getFullRaceTime() < monTime) {
                monTime = car.getFullRaceTime();
                championName = car.getName();
            }
        }
        System.out.println("Победил " + championName);
    }
}


