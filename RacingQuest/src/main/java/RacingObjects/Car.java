package RacingObjects;

import java.util.concurrent.BrokenBarrierException;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private long fullRaceTime;

    public long getFullRaceTime() {
        return this.fullRaceTime;
    }

    static {
        CARS_COUNT = 0;
    }

    public Race getRace() {
        return this.race;
    }

    private Race race;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }


    @Override
    public void run() {
        fullRaceTime = 0L;    //Соревновательное время участника в гонке (в сумме на всех этапах) начинаем с 0 мс
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            race.getCyclicBarrier().await();    //ждем, пока подготовятся остальные участники
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            fullRaceTime += race.getStages().get(i).go(this);
            //добавили время прохождения этого этапа к общему времени участника
            try {
                race.getCyclicBarrier().await();    //Как только болид проехал этап, нужно дождаться остальных машин
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
        race.getCdlFinish().countDown();    //Одна машина финишировала - счетчик финишировавших на один уменьшили
    }
}