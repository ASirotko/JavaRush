package com.javarush.task.task27.task2712.ad;

/**
 *
 * Created by Shurik on 28.03.2017.
 */
public class Advertisement {
    private Object content;
    private String name;
    private long initialAmount;
    private int hits;
    private int duration;
    private long amountPerOneDisplaying;

    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;
        if (hits > 0) {
            this.amountPerOneDisplaying = initialAmount / hits;
        }
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public String toString(){
        return String.format("%s is displaying... %d, %d",name, amountPerOneDisplaying, (long)(amountPerOneDisplaying * 1000.0/ duration));
    }

    public void revalidate(){
        if(hits<=0)throw new UnsupportedOperationException();

        hits--;
    }

    public int getHits() {
        return hits;
    }
}
