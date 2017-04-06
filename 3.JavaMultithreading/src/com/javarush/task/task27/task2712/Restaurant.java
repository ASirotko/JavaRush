package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Waiter;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Shurik on 27.03.2017.
 */
public class Restaurant {
    private static final int ORDER_CREATING_INTERVAL = 100;

    public static void main(String[] args) {
        Cook cook = new Cook("Amigo");
        Cook cookChuck = new Cook("Chuck");

        StatisticManager.getInstance().register(cook);
        StatisticManager.getInstance().register(cookChuck);

        OrderManager orderManager = new OrderManager();

        Waiter waiter = new Waiter();

        cook.addObserver(waiter);
        cookChuck.addObserver(waiter);

        List<Tablet> tablets = new ArrayList<Tablet>();
        for(int i=0; i<5; i++) {
            Tablet tablet = new Tablet(i+1);
            tablets.add(tablet);
            //tablet.addObserver(cook);
            //tablet.addObserver(cookChuck);
            tablet.addObserver(orderManager);
            tablet.addObserver(orderManager);
        }

        Thread task = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        task.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException nothing) {
            /*NOP*/
        }
        task.interrupt();

        DirectorTablet directorTablet = new DirectorTablet();

        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();
    }
}
