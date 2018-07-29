package com.example.vnprk.android1final.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vnprk.android1final.Presenter.DataPresenter;
import com.example.vnprk.android1final.R;
import com.example.vnprk.android1final.adapter.DishRecyclerAdapter;
import com.example.vnprk.android1final.model.DataClass;
import com.example.vnprk.android1final.model.Dish;
import com.example.vnprk.android1final.model.Order;

import java.util.ArrayList;

/**
 * Created by VNPrk on 02.12.2016.
 */
public class ActivityDish extends AppCompatActivity {
    public static final String KEY_DISH = "key_dish";
    public static final String KEY_FROM_MENU = "key_from_menu_tf";
    public static final String KEY_SAVE_STATE_ORDER = "key_save_state_order";
    Order order;
    Dish dish;
    DataClass dataClass;
    ImageView imvPhoto;
    TextView tvName;
    TextView tvWeight;
    TextView tvPrice;
    TextView tvDescription;
    Button btnAddPosition;
    DataPresenter presenter;
    DishRecyclerAdapter adapter;
    ArrayList<Dish> dishFromKategoria;
    boolean fromMenu;

    public static void openActivity(Context context,/*Order order,*/ Dish dish, boolean fromMenu){
        Intent intent = new Intent(context, ActivityDish.class);
        //intent.putExtra(ActivityOrder.KEY_ORDER_INTENT, order);
        intent.putExtra(KEY_FROM_MENU, fromMenu);
        intent.putExtra(KEY_DISH, dish);
        if (!(context instanceof Activity))
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        dish = (Dish)getIntent().getSerializableExtra(KEY_DISH);
        fromMenu = getIntent().getBooleanExtra(KEY_FROM_MENU, true);
        presenter = new DataPresenter(this);
        initViews();
        initData();
        setClickListener();
    }

    private void initViews(){
        imvPhoto = (ImageView)findViewById(R.id.imv_photo_dish);
        tvWeight = (TextView)findViewById(R.id.tv_dish_weight);
        tvPrice = (TextView)findViewById(R.id.tv_dish_price);
        tvDescription = (TextView)findViewById(R.id.tv_dish_description);
        tvName = (TextView)findViewById(R.id.tv_dish_name);
        btnAddPosition = (Button)findViewById(R.id.btn_add_position);
        if(fromMenu)
            btnAddPosition.setVisibility(View.VISIBLE);
    }

    private void initData(){
        dataClass = new DataClass(this);
        order = (Order)dataClass.deserealizeObject(getString(R.string.file_cash_order));
        if(dish!=null){
            //imvPhoto.setImageResource(getResources().getIdentifier("chizkeyk", "drawable", getPackageName()));
            imvPhoto.setImageResource(getResources().getIdentifier(dish.getPhoto(), "drawable", getPackageName()));

            tvName.setText(dish.getName());
            tvDescription.setText("\t\t"+dish.getDescription());
            tvPrice.setText(Integer.toString(dish.getPrice()));
            tvWeight.setText(Integer.toString(dish.getWeight())+" г.");
        }
    }

    private void setClickListener(){
        btnAddPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPosition();
            }
        });
    }

    private void addPosition(){
        /*order.addOrderPosition(dish);
        dataClass.serializeObject(order, getString(R.string.file_cash_order));*/
        presenter.addPositionToOrder(dish);
        Toast.makeText(this, getString(R.string.tst_add_position),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putSerializable(KEY_SAVE_STATE_ORDER, order);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void restoreActivityState(Bundle state){
        if(state!=null){
            order = (Order)state.getSerializable(KEY_SAVE_STATE_ORDER);
        }
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
