package RacingObjects;


import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {

    //Через этот Семафор переменная пропускной способности тоннеля ограничивается
    Semaphore smp;

    public Tunnel(int length, int throughput) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        smp = new Semaphore(throughput);
    }

    @Override
    public long go(Car c) {
        long startTime = 0;
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                c.getRace().getCyclicBarrier().await(); //Синхронизирующий барьер на начало каждого этапа(и tunnel и road)
                smp.acquire(); //Заезжаем в тоннель - занимаем место в семафоре
                System.out.println(c.getName() + " начал этап: " + description);
                startTime = System.currentTimeMillis();  //засекли секундомер участнику
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                smp.release();  //выезжаем из тоннеля - освобождаем место в семафоре
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis() - startTime;    //Время конца минус время начала прохождения этапа
    }
}
