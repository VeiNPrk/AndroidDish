package com.example.vnprk.android1final.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by VNPrk on 01.12.2016.
 */
public class KategoriaDish implements Serializable{

    private int idKategoria;
    private String nameKategoria;

    public KategoriaDish(int id, String name){
        idKategoria=id;
        nameKategoria=name;
    }

    public int getIdKategoria(){
        return idKategoria;
    }

    public String getNameKategoria(){
        return nameKategoria;
    }

}
