public class MethodsRacing {

    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;


    public static long TestMonoStreamMethod(){
        float[] arr = new float[SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        long startTime = System.currentTimeMillis(); //Засекаем время
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + (float) i / 5) * Math.cos(0.2f + (float) i / 5) *
                    Math.cos(0.4f + (float) i / 2));
        }
        return System.currentTimeMillis() - startTime;  //Стопаем и выдаем время выполнения метода: одним потоком
    }

    public static long Test2StreamsMethod() throws InterruptedException {
        //Заполним массив ещё до старта замера времени работы метода, чтобы гонка с монометодом была честной всё же
        float[] arr = new float[SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        long startTime = System.currentTimeMillis(); //Засекаем время
        //теперь нужно разбить на 2 части массив
        float[] arrLeft = new float[HALF];  //Выделяем в памяти место под левую часть массива при разделении
        float[] arrRight = new float[SIZE-HALF]; //...под правую (SIZE-HALF: формула страхует от случаев нечетного size)
        System.arraycopy(arr, 0, arrLeft, 0, HALF); //Записываем левую на половину (HALF)
        System.arraycopy(arr, HALF, arrRight, 0, SIZE-HALF);   //Записываем в правый полумассив оставшееся

        //Через краткую запись лямбды организуем реализацию первого потока с преобразованием ячеек левого полумассива
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < arrLeft.length; i++) {
                arrLeft[i] = (float) (arrLeft[i] * Math.sin(0.2f + (float) i / 5) * Math.cos(0.2f + (float) i / 5)
                        * Math.cos(0.4f + (float) i / 2));
            }
        });
        //Через краткую запись лямбды организуем реализацию второго потока с преобразованием ячеек правого полумассива
        Thread thread2 = new Thread(() -> {

            for (int i = 0; i < arrRight.length; i++) {
                arrRight[i] = (float) (arrRight[i] * Math.sin(0.2f + (float) (i + arrLeft.length) / 5) * Math.cos(0.2f +
                        //i + arrLeft.length в цикле вместо i, чтобы совпадали выходы "соревнующихся" методов
                        (float) (i + arrLeft.length)/ 5) *
                        Math.cos(0.4f + (float) (i + arrLeft.length) / 2));
            }
        });

        //запустим эти потоки
        thread1.start();
        thread2.start();

        //подождем, пока выполнятся оба
        thread1.join();
        thread2.join();

        arr = new float[SIZE];  // Очистим итоговый массив, чтобы слить туда обе обратботанные части
        //"склеиваем"
        System.arraycopy(arrLeft, 0, arr, 0, arrLeft.length);
        System.arraycopy(arrRight, 0, arr, arrLeft.length, arrRight.length);
        return System.currentTimeMillis() - startTime;  //Стопаем и выдаем время выполнения метода: одним потоком
    }
}
