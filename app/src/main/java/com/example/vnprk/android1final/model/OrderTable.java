package com.example.vnprk.android1final.model;

import android.content.Context;

import com.example.vnprk.android1final.R;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Администратор on 03.12.2016.
 */
public class OrderTable implements Serializable {

    private Calendar dateOrder;
    private int countPeople;
    private int kHoll;
    private String description;

    public OrderTable(/*Date _date, int _count, int _k, String _descr*/) {
       /* dateOrder=_date;
        countPeople=_count;
        kHoll=_k;
        description=_descr;*/
    }

    public void setDateOrder(Calendar _cal) {
        dateOrder=_cal;
    }

    public void setCountPeople(int _count) {
        countPeople=_count;
    }

    public void setkHoll(int _k) {
        kHoll=_k;
    }

    public void setDescription(String _descr) {
        description = _descr;
    }

    public Calendar getDateOrder() {
        return dateOrder;
    }

    public String getStrDateOrder() {
        int year=dateOrder.get(Calendar.YEAR);
        int month=dateOrder.get(Calendar.MONTH);
        int day =dateOrder.get(Calendar.DAY_OF_MONTH);
        return String.format("%02d.%02d.%02d", day, month + 1, year);
    }
    public String getStrTimeOrder() {
        int hour=dateOrder.get(Calendar.HOUR_OF_DAY);
        int minute=dateOrder.get(Calendar.MINUTE);
        return String.format("%02d:%02d", hour, minute);
    }

    public int getCountPeople() {
        return countPeople;
    }

    public int getKHoll() {
        return kHoll;
    }

    public String getDescription() {
        return description;
    }
}
