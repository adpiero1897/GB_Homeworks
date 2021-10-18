package Models;

public class Cat {


    private String name;
    private int appetite;

    /*3. Каждому коту нужно добавить поле сытость (когда создаем котов, они голодны).
    Если коту удалось покушать (хватило еды), сытость = true.*/
    private boolean isFull;

    public Cat(String name, int appetite) {
        this.name = name;
        this.appetite = appetite;
        this.isFull = appetite <=0; //Если у кота нет аппетита, то он сыт
    }
    public void eat(Plate p) {
        /*4. Считаем, что если коту мало еды в тарелке, то он её просто не трогает, то есть не может быть наполовину сыт
        (это сделано для упрощения логики программы).*/
        if(appetite <= p.getFood()) {
            p.decreaseFood(appetite);
            this.isFull = true;         //Кошка наелась
        }else{
            /*2. Сделать так, чтобы в тарелке с едой не могло получиться отрицательного количества еды (например,
                    в миске 10 еды, а кот пытается покушать 15-20).*/
            System.out.println("Кошке мало еды на тарелке - " + this.name + " уходит");
        }
    }

    public String getName() {
        return name;
    }

    public boolean getisFull() {
        return isFull;
    }
}
