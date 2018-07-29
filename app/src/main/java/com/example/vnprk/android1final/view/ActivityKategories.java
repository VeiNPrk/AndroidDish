package com.example.vnprk.android1final.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vnprk.android1final.Presenter.DataPresenter;
import com.example.vnprk.android1final.R;
import com.example.vnprk.android1final.adapter.KategoriaRecyclerAdapter;
import com.example.vnprk.android1final.model.DataClass;
import com.example.vnprk.android1final.model.Dish;
import com.example.vnprk.android1final.model.KategoriaDish;

import java.util.ArrayList;

/**
 * Created by VNPrk on 30.11.2016.
 */
public class ActivityKategories extends AppCompatActivity {

    DataPresenter presenter;
    RecyclerView rvCategories;
    KategoriaRecyclerAdapter adapter;
    ArrayList<KategoriaDish> dishes;

    public static void openActivity(Context context){
        Intent intent = new Intent(context, ActivityKategories.class);
        if (!(context instanceof Activity))
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategories);
        presenter = new DataPresenter(this);
        initViews();
        initData();
        setRecyclerView();
    }

    private void initViews(){
        rvCategories = (RecyclerView)findViewById(R.id.rv_categories);
    }

    private void initData() {
        DataClass dataClass = new DataClass(this);
        dishes = (ArrayList<KategoriaDish>) dataClass.deserealizeObject(getString(R.string.file_kategories));
    }

    private void setRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        adapter = new KategoriaRecyclerAdapter(this, dishes);
        rvCategories.setLayoutManager(layoutManager);
        rvCategories.setAdapter(adapter);
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
