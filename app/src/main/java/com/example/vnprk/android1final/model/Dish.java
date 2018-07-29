package com.example.vnprk.android1final.model;

import java.io.Serializable;

/**
 * Created by VNPrk on 29.11.2016.
 */
public class Dish implements Serializable {

    private int price;
    private int count;
    private int weight;
    private int kategoria;
    /*private double grProteins;
    private double grFats;
    private double grCarbohydrates;*/
    private String name;
    private String description;
    private String photo;

    public Dish(String _name, String _descr, int _kat, int _price, int _weight, String _photo){
        name=_name;
        description=_descr;
        kategoria=_kat;
        price=_price;
        weight=_weight;
        photo=_photo;
    }

    public int getPrice(){
        return price;
    }

    public int getCount(){
        return count;
    }

    public int getWeight(){
        return weight;
    }

    public int getKategoria(){
        return kategoria;
    }

    public String getPhoto(){
        return photo;
    }

    /*public double getGrProteins(){
        return grProteins;
    }

    public double getGrFats(){
        return  grFats;
    }

    public double getGrCarbohydrates(){
        return grCarbohydrates;
    }*/

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

}
