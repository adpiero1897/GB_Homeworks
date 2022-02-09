package RacingObjects;

import java.util.concurrent.BrokenBarrierException;

public class Road extends Stage {
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }

    @Override
    public long go(Car c) {
        long startTime = 0;
        try {
            c.getRace().getCyclicBarrier().await(); //Синхронизирующий барьер на начало каждого этапа(и tunnel и road)
            System.out.println(c.getName() + " начал этап: " + description);
            startTime = System.currentTimeMillis(); //засекли секундомер участнику
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis() - startTime;    //Время конца минус время начала прохождения этапа
    }
}
