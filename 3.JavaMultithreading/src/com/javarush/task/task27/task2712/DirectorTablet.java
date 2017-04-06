package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.Advertisement;
import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * Created by Shurik on 29.03.2017.
 */
public class DirectorTablet {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

    public void printAdvertisementProfit(){
        Map<Date, Long> videoSelected = StatisticManager.getInstance().getAdvertisementProfit();
        long total = 0;

        for(Map.Entry<Date,Long> entry : videoSelected.entrySet()){
            long l = entry.getValue();
            ConsoleHelper.writeMessage(String.format("%s - %d.%d", sdf.format(entry.getKey()), l / 100, l % 100));

            total += l;
        }

        ConsoleHelper.writeMessage(String.format("Total - %d.%d", total/100, total %100));
        ConsoleHelper.writeMessage("");
    }



    public void  printCookWorkloading(){
        TreeMap<Date, TreeMap<String, Long>> tree = StatisticManager.getInstance().getCookWorkloading();

        for (Map.Entry<Date, TreeMap<String, Long>> entry: tree.entrySet()) {

            ConsoleHelper.writeMessage(sdf.format(entry.getKey()));

            for (Map.Entry<String, Long> ee: entry.getValue().entrySet()) {
                if (ee.getValue() != 0) {
                    ConsoleHelper.writeMessage(ee.getKey() + " - " + Math.round(Math.ceil(ee.getValue() / 60.0f)) + " min");
                }
            }
            ConsoleHelper.writeMessage("");
        }
    }

    public void  printActiveVideoSet(){
        //ConsoleHelper.writeMessage("printActiveVideoSet");
        StatisticAdvertisementManager manager = StatisticAdvertisementManager.getInstance();

        for(Advertisement a : manager.getVideoList(StatisticAdvertisementManager.VideoType.Active)){
            ConsoleHelper.writeMessage(a.getName() + " - " + a.getHits());
        }
    }

    public void  printArchivedVideoSet(){
        //ConsoleHelper.writeMessage("printArchivedVideoSet");
        StatisticAdvertisementManager manager = StatisticAdvertisementManager.getInstance();

        for(Advertisement a : manager.getVideoList(StatisticAdvertisementManager.VideoType.NotActive)){
            ConsoleHelper.writeMessage(a.getName());
        }
    }
}
