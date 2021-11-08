package Competitors;

public class Robot implements Competitor{

    private String name;
    private double battery_capacity;    //У робота максимальная преодолеваемая дистанция определяется емкостью батареи
    private double maxJumpHeight;

    public Robot(String name, double battery_capacity, double maxJumpHeight) {
        this.name = name;
        this.battery_capacity = battery_capacity;//Каждый мА*Ч даёт роботу продивнуться на 10м (для упрощения расчета бега)
        this.maxJumpHeight = maxJumpHeight; //Но вот максимальная высота прыжка у машины установлена производителем
    }

    @Override
    public boolean isRunable(double distance) {
        if (battery_capacity*10 >= distance) {//Каждый мА*Ч даёт роботу продивнуться на 10м (для упрощения расчета бега)
            System.out.println("Робот " + name + " успешно пробежал " + distance + " метров!");
            battery_capacity -= battery_capacity/10;    //На бег робот потратил энергию своей батареи
            return true;
        } else {
            System.out.println("Робот " + name + " не смог пробежать " + distance + " метров! Робот " + name +
                    " выбывает.");
            return false;
        }
    }

    @Override
    public boolean isJumpable(double height) {
        if (maxJumpHeight >= height && battery_capacity > 50) {//будем считать, что на любой прыжок робот расходует 50мА*ч
            System.out.println("Робот " + name + " успешно перепрыгнул стену высотой " + height + " метров!");
            battery_capacity -= 50; //На прыжок потратили робот 50мА*ч емкости своей батареи
            return true;
        } else {
            System.out.println("Робот " + name + " не смог перепрыгнуть стену в " + height + " метров! Робот "
                    + name + " выбывает.");
            return false;
        }
    }

    @Override
    public void success() {
        System.out.println("Робот " + name + " успешно прошел полосу препятсвий. Гордость советских инженеров");
    }
}
