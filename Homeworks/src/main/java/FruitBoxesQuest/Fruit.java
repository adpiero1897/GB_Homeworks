package FruitBoxesQuest;

public class Fruit {

    private String fruitName;
    private Float weight;

    public Fruit(FruitTypes fruit) {
        this.fruitName = fruit.getTitle();
        this.weight = fruit.getWeight();
    }

    public String getFruitName() {
        return fruitName;
    }

    public Float getFruitWeight() {
        return weight;
    }
}
