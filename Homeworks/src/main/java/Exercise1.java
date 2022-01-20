public class Exercise1<T> {

    private T[] arr;

    public Exercise1(T[] arr) {
        this.arr = arr;
    }

    public T[] getArr() {
        return arr;
    }

    public void showArrType() {
        System.out.println("Тип элементов массива: " + arr.getClass().getName());
    }

    //1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
    public void rotationElements(int i1, int i2) {
        if (i1 < 0 || i1 >= arr.length || i2 < 0 || i2 >= arr.length) {
            System.out.println("Аргументы-индексы выходят за пределы массива");
            return;
        }
        T buffer = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = buffer;
    }
}
