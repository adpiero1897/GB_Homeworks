package Lets;

public class Wall implements Let{

    private double height;

    @Override
    public double getLetValue() {
        return height;
    }

    public Wall(double height) {
        this.height = height;
    }
}
