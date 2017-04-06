package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.List;

/**
 *
 * Created by Shurik on 27.03.2017.
 */
public class Order {
    protected List<Dish> dishes;
    protected final Tablet tablet;


    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;

        initDishes();
    }

    @Override
    public String toString() {
        if (dishes == null || dishes.isEmpty()) {
            return "";
        } else {
            return "Your order: " + dishes + " of " + tablet;
        }
    }

    protected void initDishes() throws IOException {
        this.dishes = ConsoleHelper.getAllDishesForOrder();
    }

    public int getTotalCookingTime(){
        int sum = 0;

        for (Dish d : dishes) {
            sum += d.getDuration();
        }

        return sum;
    }
    public boolean isEmpty(){
        return dishes == null || dishes.size() == 0;
    }

    public List<Dish> getDishes() {
        return dishes;
    }
}
