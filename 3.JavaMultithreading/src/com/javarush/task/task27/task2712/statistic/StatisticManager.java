package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.*;


/**
 *
 * Created by Shurik on 29.03.2017.
 */
public class StatisticManager {
    private static StatisticManager ourInstance = new StatisticManager();
    private StatisticStorage statisticStorage = new StatisticStorage();

    public static StatisticManager getInstance() {
        return ourInstance;
    }

    private StatisticManager() {
    }

    public void register(EventDataRow data){
        statisticStorage.put(data);
    }

    public Map<Date, Long> getAdvertisementProfit() {
        TreeMap<Date, Long> map = new TreeMap<>((Comparator) (o1, o2) -> ((Date)o2).compareTo((Date)o1));

        List<EventDataRow> list = statisticStorage.get(EventType.SELECTED_VIDEOS);

        for (EventDataRow e : list) {
            VideoSelectedEventDataRow ee = (VideoSelectedEventDataRow) e;

            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(ee.getDate());
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date newDate = calendar.getTime();

            long sum = ee.getAmount();

            if (map.containsKey(newDate)) {
                sum += map.get(newDate);
            }

            map.put(newDate, sum);
        }

        return map;
    }

    public TreeMap<Date, TreeMap<String, Long>> getCookWorkloading() {
        List<EventDataRow> list = statisticStorage.get(EventType.COOKED_ORDER);
        Date date;
        TreeMap<Date, TreeMap<String, Long>> tree = new TreeMap<>(Collections.<Date>reverseOrder());
        for (EventDataRow row: list) {
            CookedOrderEventDataRow item = (CookedOrderEventDataRow)row;

            date = getDate(row.getDate());

            if (!tree.containsKey(date)) {
                tree.put(date, new TreeMap<>());
                tree.get(date).put(item.getCookName(), (long)item.getTime());
            } else {
                String name = item.getCookName();
                TreeMap<String, Long> cc = tree.get(date);

                long times = item.getTime();

                if (cc.containsKey(name)){
                    times += cc.get(name);
                }

                cc.put(name, times);
            }
        }
        return tree;

    }

    private Date getDate(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private class StatisticStorage{
        private Map<EventType, List<EventDataRow>> storage;

        public StatisticStorage(){
            storage = new HashMap<>();

            for (EventType e : EventType.values()){
                storage.put(e, new ArrayList<>());
            }
        }

        private void put(EventDataRow data){
            if (data != null)
                storage.get(data.getType()).add(data);
        }

        private  List<EventDataRow> get(EventType event){
            return storage.get(event);
        }
    }
}
