package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * Created by Shurik on 28.03.2017.
 */
public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

	public void processVideos(){

        if(storage.list().size()==0)throw new NoVideoAvailableException();

        ArrayList<Advertisement> show = new ArrayList<Advertisement>();
        ArrayList<Advertisement> show1 = new ArrayList<Advertisement>();
        Result r = new Result(timeSeconds,0,0,0L, show);
        Result r1 = new Result(timeSeconds,Integer.MAX_VALUE,0, 0L, show1);
        ResultRecord record = new ResultRecord(r1);
        recursion(r,record);
        List<Advertisement> itog = record.record.seq;

        if(itog.size()==0)throw new NoVideoAvailableException();
        Collections.sort(itog,new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                int result = Long.compare(o2.getAmountPerOneDisplaying(), o2.getAmountPerOneDisplaying());
                if(result != 0)return result;

                long x =  (long)(o1.getAmountPerOneDisplaying() * 1000.0d / o1.getDuration());
                long y =  (long)(o2.getAmountPerOneDisplaying() * 1000.0d / o2.getDuration());

                return Long.compare(x, y);
            }
        });

        StatisticManager.getInstance().register(new VideoSelectedEventDataRow(itog, record.record.profit, timeSeconds - record.record.durationTo));

        for (Advertisement advertisement: itog) {
            ConsoleHelper.writeMessage(""+advertisement);
            advertisement.revalidate();
        }
    }	
    private static class Result implements Cloneable{
        public int durationTo; //min>0
        public int quantity;
        public int pos;
        public long profit;
        public List<Advertisement> seq;

        public Result(int durationTo, int quantity, int pos, long profit,List<Advertisement> seq) {
            this.durationTo = durationTo;
            this.quantity = quantity;
            this.pos = pos;
            this.profit = profit;
            this.seq = seq;
        }
        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
        public boolean breakRecord(Result record){
            if(this.profit > record.profit)return true;
            if(this.profit == record.profit) {
                if (this.durationTo < record.durationTo) return true;
                if(this.durationTo == record.durationTo){
                    if(this.quantity < record.quantity)return true;
                }
            }
            return false;
        }
    }
    private static class ResultRecord{
        public Result record;

        public ResultRecord(Result record) {
            this.record = record;
        }
    }
    private void recursion(Result r, ResultRecord record){
        //l - откуда мы выбираем, выводим общее время.
        //timeSeconds
        Result tmp;
        List<Advertisement> l = r.seq;
        List<Advertisement> allList = storage.list();
        for (int i = r.pos; i<allList.size(); i++) {
            Advertisement adv = allList.get(i);
            if(adv.getHits()>0 && !l.contains(adv)){
                if(r.durationTo - adv.getDuration()>=0) {
                    try {
                        tmp = (Result)r.clone();
                        tmp.seq = new  ArrayList<Advertisement>();
                        tmp.seq.addAll(l.subList(0, l.size()));
                        tmp.seq.add(adv);
                        tmp.durationTo = r.durationTo - adv.getDuration();
                        tmp.profit += adv.getAmountPerOneDisplaying();
                        tmp.quantity++;
                        tmp.pos = i+1;
                        //ставим рекорд
                        if(tmp.breakRecord(record.record))record.record=tmp;
                        recursion(tmp,record);

                    } catch (CloneNotSupportedException e) {
                        //e.printStackTrace();
                    }
                }
            }
        }
    }
}
