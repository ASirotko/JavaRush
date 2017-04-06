package com.javarush.task.task27.task2712.statistic.event;

import java.util.Date;

/**
 *
 * Created by Shurik on 29.03.2017.
 */
public interface EventDataRow {
    EventType getType();

    /**
     * Дата создания записи
     * @return
     */
    Date getDate();

    /**
     * время — продолжительность
     * @return
     */
    int getTime();
}
