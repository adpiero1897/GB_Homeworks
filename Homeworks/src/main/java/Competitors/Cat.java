package Competitors;

public class Cat implements Competitor {

    private String name;
    private double maxRunDistance;
    private double maxJumpHeight;

    public Cat(String name, double maxRunDistance, double maxJumpHeight) {
        this.name = name;
        this.maxRunDistance = maxRunDistance;
        this.maxJumpHeight = maxJumpHeight;
    }

    @Override
    public boolean isRunable(double distance) {
        if (maxRunDistance >= distance) {
            System.out.println("Кот " + name + " успешно пробежал " + distance + " метров!");
            return true;
        } else {
            System.out.println("Кот " + name + " не смог пробежать " + distance + " метров! Кот " + name + " выбывает.");
            return false;
        }
    }

    @Override
    public boolean isJumpable(double height) {
        if (maxJumpHeight >= height) {
            System.out.println("Кот " + name + " успешно перепрыгнул стену высотой " + height + " метров!");
            return true;
        } else {
            System.out.println("Кот " + name + " не смог перепрыгнуть стену в " + height + " метров! Кот " + name + " выбывает.");
            return false;
        }
    }

    @Override
    public void success() {
        System.out.println("Кот " + name + " успешно прошел полосу препятсвий. Можете дать ему вкусняшки");
    }
}
