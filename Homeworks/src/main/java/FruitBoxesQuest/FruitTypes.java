package FruitBoxesQuest;

public enum FruitTypes {
    ORANGE ("Апельсин",1.5f)
    ,APPLE ("Яблоко",1.0f);

    private String title;
    private Float weight;

    FruitTypes(String name, Float weight) {
        this.title = name;
        this.weight = weight;
    }

    public String getTitle() {
        return title;
    }

    public Float getWeight() {
        return weight;
    }
}
