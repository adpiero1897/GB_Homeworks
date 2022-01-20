package FruitBoxesQuest;

import java.util.ArrayList;

public class Box<FT extends Fruit> {

    private ArrayList<FT> fruitList = new ArrayList<>();

    public ArrayList<FT> getFruitList() {
        return fruitList;
    }

    public void addFruit(FT fruit) {
        fruitList.add(fruit);
    }

    //Перегрузка метода для пакетного добавления фруктов в коробку
    public void addFruit(FT fruit,int count) {
        for(int i = 0; i < count; i++) {
            fruitList.add(fruit);
        }
    }

    public void removeFruit(FT fruit) {
        fruitList.remove(fruit);
    }

    //Полное высыпание фруктов пригодится при реализации метода пересыпания из коробки в коробку
    public void removeAllFruits() {
        fruitList.clear();
    }

    public void showFruitsInThisBox() {
        System.out.println("Список фруктов в коробке:");
        for (FT fruit : fruitList) {
            System.out.println(fruit.getFruitName());
        }
    }

    public float getSumWeight() {
      /*  float sumWeight = 0.0f;
        for (FT fruit : fruitList) {
            sumWeight += fruit.getFruitWeight();
        }
        return sumWeight;*/
        //или вариант попроще:
        if (fruitList.isEmpty()) {
            return 0;
        }
        return fruitList.get(0).getFruitWeight() * fruitList.size();
    }

    public boolean compare(Box<? extends Fruit> box) {
        return box.getSumWeight() == this.getSumWeight();
    }

    public void fruitTransfer(Box<FT> box) {
        ArrayList<? extends Fruit> fruitsList = box.getFruitList();
        //Если коробка из которой пересыпают фрукты пустая, то останавливаем метод-трансфер фруктов
        this.fruitList.addAll(box.getFruitList());
        box.removeAllFruits();
    }
}
