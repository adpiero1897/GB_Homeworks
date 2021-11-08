package Lets;

public class RunTrack implements Let{

    private double distance;

    @Override
    public double getLetValue() {
        return distance;
    }

    public RunTrack(double distance) {
        this.distance = distance;
    }
}
