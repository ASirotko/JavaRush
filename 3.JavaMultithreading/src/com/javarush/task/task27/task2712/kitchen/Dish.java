package com.javarush.task.task27.task2712.kitchen;

/**
 *
 * Created by Shurik on 27.03.2017.
 */
public enum Dish {
    Fish(25),
    Steak(30),
    Soup(15),
    Juice(5),
    Water(3);

    private int duration;

    Dish(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public static String allDishesToString(){
        StringBuilder res = new StringBuilder();
        Dish[] dishes = Dish.values();

        res.append("[");
        for (int i = 0; i < dishes.length - 1; i++) {
            res.append(dishes[i]).append(", ");
        }

        if (dishes.length > 0)
            res.append((dishes[dishes.length-1]));

        res.append("]");

        return res.toString();
    }

}
