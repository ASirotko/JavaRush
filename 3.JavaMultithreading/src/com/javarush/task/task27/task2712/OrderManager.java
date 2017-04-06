package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Order;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * Created by Shurik on 05.04.2017.
 */
public class OrderManager implements Observer{
    private LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();;

    @Override
    public void update(Observable o, Object arg) {
        Order order = (Order) arg;
        try {
            orderQueue.put(order);
        } catch (InterruptedException ignored) {
        }
    }
}
