package entity;

public class Car extends Thread {
    private int size;
    private int weight;

    @Override
    public void run() {
        Ferry ferry = Ferry.getInstance();
        ferry.loadCar(this);
        ferry.ferryDeparture(this);
        ferry.ferryDischarging(this);
    }

    public Car(int size, int weight) {
        this.size = size;
        this.weight = weight;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}