package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * Created by Shurik on 28.03.2017.
 */
public class Cook implements Runnable{
    private String name;
    private boolean busy;
    private LinkedBlockingQueue<Order> queue = new LinkedBlockingQueue<>();


    public Cook(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void startCookingOrder(Order order){
        busy = true;
        StatisticManager.getInstance().register(new CookedOrderEventDataRow(order.tablet.toString(), this.name, order.getTotalCookingTime() * 60, order.getDishes()));

        ConsoleHelper.writeMessage("Start cooking - " + order.toString()+ ", cooking time " + ((Order)(order)).getTotalCookingTime() + "min");

        try{
            Thread.sleep(order.getTotalCookingTime() * 10);
        }catch (InterruptedException e){}

        busy = false;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    while(true) {
                        Thread.sleep(10);
                        if (!busy && !queue.isEmpty()) {
                            Order order = queue.take();
                            if (order != null) {
                                startCookingOrder(order);
                            }
                        }
                    }
                }catch (InterruptedException ignored){

                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
