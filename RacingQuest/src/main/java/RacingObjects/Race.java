package RacingObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Race {
    private ArrayList<Stage> stages;

    public CyclicBarrier getCyclicBarrier() {
        return this.cyclicBarrier;
    }

    private CyclicBarrier cyclicBarrier;

    public CountDownLatch getCdlFinish() {
        return this.cdlFinish;
    }

    private CountDownLatch cdlFinish;

    public ArrayList<Stage> getStages() {
        return stages;
    }

    public Race(int carsCount,Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
        cyclicBarrier = new CyclicBarrier(carsCount);   //Синхронизирует старты на каждом этапе
        cdlFinish = new CountDownLatch(carsCount);  //Счетчик для финиширующих участников, чтобы объявить о завершении гонки
    }
}
