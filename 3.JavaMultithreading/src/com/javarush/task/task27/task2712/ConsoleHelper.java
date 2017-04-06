package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Shurik on 27.03.2017.
 */
public class ConsoleHelper {
    private static BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString() throws IOException {
       return console.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> list = new ArrayList<>();

        writeMessage("Выберите блюдо:");
        writeMessage(Dish.allDishesToString());

        String dish = readString();

        while (!"exit".equals(dish))
        {
            try
            {
                list.add(Dish.valueOf(dish));
            }
            catch (IllegalArgumentException e)
            {
                ConsoleHelper.writeMessage(dish + " отсутствует в меню");
            }
            dish = readString();
        }

        return list;
    }
}
