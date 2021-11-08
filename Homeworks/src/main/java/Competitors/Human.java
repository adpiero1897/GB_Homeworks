package Competitors;

public class Human implements Competitor{

    private String name;
    private double maxRunDistance;
    private double maxJumpHeight;

    public Human(String name, double maxRunDistance, double maxJumpHeight) {
        this.name = name;
        this.maxRunDistance = maxRunDistance;
        this.maxJumpHeight = maxJumpHeight;
    }

    @Override
    public boolean isRunable(double distance) {
        if (maxRunDistance >= distance) {
            System.out.println("Человек " + name + " успешно пробежал " + distance + " метров!");
            return true;
        } else {
            System.out.println("Человек " + name + " не смог пробежать " + distance + " метров! Человек " + name +
                    " выбывает.");
            return false;
        }
    }

    @Override
    public boolean isJumpable(double height) {
        if (maxJumpHeight >= height) {
            System.out.println("Человек " + name + " успешно перепрыгнул стену высотой " + height + " метров!");
            return true;
        } else {
            System.out.println("Человек " + name + " не смог перепрыгнуть стену в " + height + " метров! Человек "
                    + name + " выбывает.");
            return false;
        }
    }

    @Override
    public void success() {
        System.out.println("Человек " + name + " успешно прошел полосу препятсвий. Во славу олимпийских богов!");
    }
}
