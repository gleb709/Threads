package entity;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import state.FerryState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ferry {
    private final static Logger logger = LogManager.getLogger(Ferry.class);
    private static Ferry INSTANCE = new Ferry();
    private FerryState state;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private List loadedCars = new ArrayList();
    private int freeFerryCapacity = 50;
    private int freeFerrySquare = 15;


    private Ferry(){}

    public static Ferry getInstance() {
        return INSTANCE;
    }

    public boolean checkPlaceForCar(Car car){
        boolean result = false;
        if(car.getWeight() <= freeFerryCapacity && car.getSize() <= freeFerrySquare){
            result = true;
        }
        return result;
    }
    public void loadCar(Car car){
        try {
            lock.lock();
            while (state == FerryState.LOADED){
                logger.info("{} : Ferry is full, waiting", Thread.currentThread().getName());
                condition.await();
            }
            if(!checkPlaceForCar(car)){
                state =FerryState.LOADED;
                logger.info("{} : Ferry is ready for transfer", Thread.currentThread().getName());
                condition.await();
            }
            setFreeFerryCapacity(getFreeFerryCapacity()-car.getWeight());
            setFreeFerrySquare(getFreeFerrySquare()-car.getSize());
            loadedCars.add(car);
            logger.info("{} : Car has been loaded", Thread.currentThread().getName());
            condition.await(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public void ferryDeparture(Car car){
        try {
            lock.lock();
            logger.info("{} : Ferry departure car", Thread.currentThread().getName());
            condition.await(3000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void ferryDischarging(Car car){
        try{
            lock.lock();
            setFreeFerrySquare(getFreeFerrySquare() + car.getSize());
            setFreeFerryCapacity(getFreeFerryCapacity() + car.getWeight());
            loadedCars.remove(car);
            logger.info("{} : Car has been unloaded", Thread.currentThread().getName());
            if(loadedCars.isEmpty()){
                state = FerryState.EMPTY;
                logger.info("{} : Ferry is empty", Thread.currentThread().getName());
                condition.signalAll();
            }
            condition.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public int getFreeFerryCapacity() {
        return freeFerryCapacity;
    }

    public void setFreeFerryCapacity(int freeFerryCapacity) {
        this.freeFerryCapacity = freeFerryCapacity;
    }

    public int getFreeFerrySquare() {
        return freeFerrySquare;
    }

    public void setFreeFerrySquare(int freeFerrySquare) {
        this.freeFerrySquare = freeFerrySquare;
    }

}
