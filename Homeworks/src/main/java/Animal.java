public class Animal {

    public String Name;
    private double maxRunMeters;
    private double maxSwimMeters;

    //4. * Добавить подсчет созданных котов, собак и животных.
    private static int count_animals = 0;

    public static int getCount_animals() {
        return count_animals;
    }

    public Animal(String name) {
        this.Name = name;
        //4. * Добавить подсчет созданных котов, собак и животных.
        count_animals++;
    }

    //2. Все животные могут бежать и плыть. В качестве параметра каждому методу передается длина препятствия
    public void swim(double letLength){
        System.out.println("Есть животные плавающие далеко и НЕдалеко. Перед тем, как проплыть, нужно определится.");
    }

    public void run(double letLength){
        System.out.println("Есть животные бегающие далеко и НЕдалеко. Перед тем, как пробежать, нужно определится.");
    }

}
