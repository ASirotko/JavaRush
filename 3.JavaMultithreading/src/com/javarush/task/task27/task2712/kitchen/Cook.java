package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * Created by Shurik on 28.03.2017.
 */
public class Cook extends Observable{
    private String name;

    public Cook(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void startCookingOrder(Order order){
        StatisticManager.getInstance().register(new CookedOrderEventDataRow(order.tablet.toString(), this.name, order.getTotalCookingTime() * 60, order.getDishes()));

        ConsoleHelper.writeMessage("Start cooking - " + order.toString()+ ", cooking time " + ((Order)(order)).getTotalCookingTime() + "min");
        setChanged();
        notifyObservers(order);

    }
}
