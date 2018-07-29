package com.example.vnprk.android1final.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vnprk.android1final.Presenter.DataPresenter;
import com.example.vnprk.android1final.R;
import com.example.vnprk.android1final.adapter.DishRecyclerAdapter;
import com.example.vnprk.android1final.model.DataClass;
import com.example.vnprk.android1final.model.Dish;
import com.example.vnprk.android1final.model.KategoriaDish;
import com.example.vnprk.android1final.model.Order;

import java.util.ArrayList;

/**
 * Created by VNPrk on 01.12.2016.
 */
public class ActivityMenuDish extends AppCompatActivity {
    public static final String KEY_DISH_TYPE = "key_kategoria";
    public static final String KEY_KATEGORIA_NAME="key_kat_name";
    DataPresenter presenter;
    RecyclerView rvMenuDish;
    TextView tvNameKategoria;
    DishRecyclerAdapter adapter;
    ArrayList<Dish> dishes, dishFromKategoria;
    ArrayList<KategoriaDish> kategories;
    int kategoria=-1;
    String nameKategoria;

    public static void openActivity(Context context, int type, String nameKat){
        Intent intent = new Intent(context, ActivityMenuDish.class);
        intent.putExtra(KEY_DISH_TYPE, type);
        intent.putExtra(KEY_KATEGORIA_NAME, nameKat);
        if (!(context instanceof Activity))
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dish);
        kategoria = getIntent().getIntExtra(KEY_DISH_TYPE, -1);
        nameKategoria = getIntent().getStringExtra(KEY_KATEGORIA_NAME);
        presenter = new DataPresenter(this);
        initViews();
        initData();
        setRecyclerView();
    }
    private void initViews(){
        rvMenuDish = (RecyclerView)findViewById(R.id.rv_dishes);
        tvNameKategoria = (TextView)findViewById(R.id.tv_name_kategoria);
    }

    private void initData(){
        DataClass dataClass = new DataClass(this);
        tvNameKategoria.setText(nameKategoria);
        dishes = (ArrayList<Dish>)dataClass.deserealizeObject(getString(R.string.file_dishes));
        kategories = (ArrayList<KategoriaDish>) dataClass.deserealizeObject(getString(R.string.file_kategories));
        dishFromKategoria = new ArrayList<Dish>();
        if(kategoria>-1){
            for (Dish dish : dishes) {
                if (dish.getKategoria() == kategoria) {
                    dishFromKategoria.add(dish);
                }
            }
        }
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new DishRecyclerAdapter(this, dishFromKategoria, kategories, true);
        rvMenuDish.setLayoutManager(layoutManager);
        rvMenuDish.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_about: {
                presenter.showDialogAbout();
                return true;
            }
            default:
                return false;
        }
    }
}
