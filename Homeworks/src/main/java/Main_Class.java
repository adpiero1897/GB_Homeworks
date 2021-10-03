public class Main_Class {

    public static void main(String[] args) {

        Dog dogBobik = new Dog("Бобик");
        Cat catGeralt = new Cat("Геральт");

        dogBobik.run(-200); // Бобик глупый: побежал не в ту сторону, поэтому со знаком "-"
        dogBobik.swim(9.21);

        catGeralt.run(143.33);
        catGeralt.swim(-1.1);

        //4. * Добавить подсчет созданных котов, собак и животных.
        System.out.println("Всего было создано " + Animal.getCount_animals() + " животных");
        System.out.println("Всего было создано " + Dog.getCount_dogs() + " собак");
        System.out.println("Всего было создано " + Cat.getCount_cats() + " котов");
    }
}
