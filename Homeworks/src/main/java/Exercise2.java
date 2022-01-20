import java.util.ArrayList;

public final class Exercise2<T> {


    public ArrayList<T> arrToArrayList(T[] arr) {
        ArrayList<T> arrayList = new ArrayList<>(arr.length);
        for (T e:arr) {
            arrayList.add(e);
        }
        return arrayList;
    }
}
