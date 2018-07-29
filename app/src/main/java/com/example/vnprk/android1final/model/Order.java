package com.example.vnprk.android1final.model;

//import java.util.List;

import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by VNPrk on 29.11.2016.
 */
public class Order implements Serializable {

    private ArrayList<Dish> orderDishes = null;
    int totalPrice = 0;
    private String comment;

    public Order(){
        orderDishes=new ArrayList<Dish>();
    }

    public void addOrderPosition(Dish dish){
        orderDishes.add(dish);
        calcTotalPrice();
    }

    public Dish getDishFromOrder(int i){
        return orderDishes.get(i);
    }

    public int getTotalPrice(){
        return totalPrice;
    }

    public String getComment(){
        return comment;
    }
    public ArrayList<Dish> getOrderDishes(){
         return orderDishes;
     }

    public void setOrderDishes(ArrayList<Dish> _dishes) {
        orderDishes=_dishes;
        calcTotalPrice();
    }

    public void setComment(String _comment){
        comment=_comment;
    }

    private void calcTotalPrice(){
        totalPrice=0;
        for(Dish dish : orderDishes) {
            totalPrice+=dish.getPrice();
        }
    }
}
