package FruitBoxesQuest;

public class Apple extends Fruit{

    private Float weight = 1.0f;

    public Apple(FruitTypes fruit) {
        super(fruit);
    }

    public Float getWeight() {
        return weight;
    }
}
