public class Test_ABC_Class {

//1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок – ABСABСABС).
// Используйте wait/notify/notifyAll.
private static Object monitor = new Object();
private static char nextLet = 'A';
private static String resultString="";

    public static void main(String[] args) {

        Thread threadPrintA = new Thread(() -> {
            for (int i = 0; i < 5;i++) { //печатаем по 5 каждых символов
                try {
                    writeA();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadPrintB = new Thread(() -> {
            for (int i = 0; i < 5;i++) { //печатаем по 5 каждых символов
                try {
                    writeB();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadPrintC = new Thread(() -> {
            for (int i = 0; i < 5;i++) { //печатаем по 5 каждых символов
                try {
                    writeC();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadPrintA.start();
        threadPrintB.start();
        threadPrintC.start();

        //Подсоединяемся к потоку только последнего символа
        try {
            threadPrintC.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Итоговая строка получилась: " + resultString);

    }

    private static void writeA() throws InterruptedException {
        synchronized (monitor){
            while(nextLet != 'A'){
                monitor.wait();
            }
            resultString+='A';
            nextLet = 'B';
            monitor.notifyAll();
        }
    }

    private static void writeB() throws InterruptedException {
        synchronized (monitor){
            while(nextLet != 'B'){
                monitor.wait();
            }
            resultString+='B';
            nextLet = 'C';
            monitor.notifyAll();
        }
    }

    private static void writeC() throws InterruptedException {
        synchronized (monitor){
            while(nextLet != 'C'){
                monitor.wait();
            }
            resultString+='C';
            nextLet = 'A';
            monitor.notifyAll();
        }
    }
}
