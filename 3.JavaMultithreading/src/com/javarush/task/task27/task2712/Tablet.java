package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.TestOrder;

import java.io.IOException;
import java.util.Observable;
import java.util.logging.Level;
import  java.util.logging.Logger;

/**
 *
 * Created by Shurik on 27.03.2017.
 */
public class Tablet extends Observable {
    public final int number;
    private static Logger logger = Logger.getLogger(Tablet.class.getName());

    public Tablet(int number) {
        this.number = number;
    }

    public Order createOrder(){
        Order order = null;
        try {
            order = new Order(this);

            manageOrder(order);
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }

        return order;
    }

    public void createTestOrder(){
        TestOrder testOrder = null;

            try {
                testOrder = new TestOrder(this);
                manageOrder(testOrder);
            }
                catch (IOException e){
                logger.log(Level.SEVERE, "Console is unavailable.");
            }
                catch (NoVideoAvailableException e){
                Tablet.logger.log(Level.INFO, "No video is available for the order " + testOrder);
            }
    }

    private void manageOrder(Order order) {
        if (!order.isEmpty()) {
            ConsoleHelper.writeMessage(order.toString());

            AdvertisementManager manager = new AdvertisementManager(order.getTotalCookingTime() * 60);

            try {
                manager.processVideos();
            }catch (NoVideoAvailableException e){
                logger.log(Level.INFO,"No video is available for the order " + order);
            }

            setChanged();
            notifyObservers(order);
        }
    }


    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }
    /**
     *     public void setQueue(LinkedBlockingQueue<Order> queue) {
     this.queue = queue;
     }
     */
}
