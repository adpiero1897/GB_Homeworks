//1. Создать классы Собака и Кот с наследованием от класса Животное.
public class Cat extends Animal{

    //4. * Добавить подсчет созданных котов, собак и животных.
    private static int count_cats = 0;

    public static int getCount_cats() {
        return count_cats;
    }

    //3. У каждого животного есть ограничения на действия
    // (бег: кот 200 м., собака 500 м.; плавание: кот не умеет плавать, собака 10 м.).
    private final double maxRunMeters = 200;
    private final double maxSwimMeters = 0;

    public Cat(String name) {
        super(name);
        //4. * Добавить подсчет созданных котов, собак и животных.
        count_cats++;
    }

    //2. Все животные могут бежать и плыть. В качестве параметра каждому методу передается длина препятствия
    public void swim(double letLength) {
        if (Math.abs(letLength) <= maxSwimMeters) {
            System.out.println(Name + " успешно проплыл "+ letLength + " метров");
        } else {
            System.out.println(Name + " утонул: не смог проплыть "+ letLength + " метров");
        }
    }

    public void run(double letLength) {
        if (Math.abs(letLength) <= maxRunMeters) {
            System.out.println(Name + " успешно пробежал " + letLength + " метров");
        } else {
            System.out.println(Name + " не смог пробежать " + letLength + " метров");
        }
    }
}
