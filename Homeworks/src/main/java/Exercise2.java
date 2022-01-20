import java.util.ArrayList;

public final class Exercise2<T> {

    //1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
    public ArrayList<T> arrToArrayList(T[] arr) {
        ArrayList<T> arrayList = new ArrayList<>(arr.length);
        for (T e:arr) {
            arrayList.add(e);
        }
        return arrayList;
    }
}
