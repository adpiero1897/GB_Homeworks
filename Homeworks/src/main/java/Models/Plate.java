package Models;

public class Plate {

    private int food;

    public Plate(int food) {
        //2. Сделать так, чтобы в тарелке с едой не могло получиться отрицательного количества еды
        this.food = Math.max(0, food);
    }

    public void decreaseFood(int n) {
        food -= n;
    }

    //6. Добавить в тарелку метод, с помощью которого можно было бы добавлять еду в тарелку.
    public void addFood(int food){
        if(food > 0) {  //Этим методом нельзя изъять еду из тарелки
            this.food += food;
            System.out.println("В тарелку добавили " + food + " еды");
        }
    }

    public int getFood() {
        return food;
    }

    public void info() {
        System.out.println("на тарелке: " + food + " еды");
    }
}
