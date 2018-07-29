package com.example.vnprk.android1final;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vnprk.android1final.Presenter.DataPresenter;
import com.example.vnprk.android1final.model.DataClass;
import com.example.vnprk.android1final.model.Order;
import com.example.vnprk.android1final.model.OrderTable;
import com.example.vnprk.android1final.view.ActivityKategories;
import com.example.vnprk.android1final.view.ActivityOrder;
import com.example.vnprk.android1final.view.ActivityOrderTable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

public class ActivityMain extends AppCompatActivity {

    private static final String KEY_STATE_STR_ORDER_TABLE = "state_str_order_table";
    private static final String KEY_STATE_ORDER_TABLE = "state_order_table";
    Button btnOrderMenu;
    Button btnOrderTable;
    Button btnMyOrder;
    TextView tvOrderTable;
    DataClass dataClass;
    Order myOrder;
    OrderTable myOrderTable;
    DataPresenter presenter;
    int requestCodeOrder = 1;
    int requestCodeOrderTable = 2;

   /* public static void openActivity(Context context, Order order){
        Intent intent = new Intent(context, ActivityOrder.class);
        intent.putExtra(KEY_ORDER_INTENT, order);
        if (!(context instanceof Activity))
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initViews();
        setClickListener();
        restoreActivityState(savedInstanceState);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.logo_orange);*/
    }

    private void initViews(){
        btnOrderMenu = (Button)findViewById(R.id.btn_order_menu);
        btnOrderTable = (Button)findViewById(R.id.btn_order_table);
        btnMyOrder = (Button)findViewById(R.id.btn_my_order);
        tvOrderTable = (TextView)findViewById(R.id.tv_order_table_text);
        android.support.v7.app.ActionBar menu = getSupportActionBar();
    }

    private void initData(){
        dataClass = new DataClass(this);
        presenter = new DataPresenter(this);
        dataClass.initStartFiles();
        myOrder = (Order)dataClass.deserealizeObject(getString(R.string.file_order));
    }

    private void setClickListener(){
        tvOrderTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });
        tvOrderTable.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                createPopupMenu(v);
                return false;
            }
        });
        btnOrderMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOrder(true);
            }
        });
        btnOrderTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOrderTable();
            }
        });
        btnMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOrder(false);
            }
        });
    }

    private void startOrder(boolean newOrder){
        if(newOrder) {
            myOrder = new Order();
            dataClass.serializeObject(myOrder, getString(R.string.file_cash_order));
        }
        else
            myOrder = (Order)dataClass.deserealizeObject(getString(R.string.file_order));
        if(myOrder!=null) {
            Intent intent = new Intent(ActivityMain.this, ActivityOrder.class);
            intent.putExtra(ActivityOrder.KEY_NEW_ORDER_INTENT, newOrder);
            startActivityForResult(intent, requestCodeOrder);
        }
        else{
            Toast.makeText(this,getString(R.string.tst_null_my_order), Toast.LENGTH_LONG).show();
        }
    }

    private void startOrderTable() {
        Intent intent = new Intent(ActivityMain.this, ActivityOrderTable.class);
        startActivityForResult(intent, requestCodeOrderTable);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==requestCodeOrder && resultCode==RESULT_OK && data!=null)
            myOrder= (Order)dataClass.deserealizeObject(getString(R.string.file_order));
        if(requestCode==requestCodeOrderTable && resultCode==RESULT_OK && data!=null) {
            myOrderTable = (OrderTable) dataClass.deserealizeObject(getString(R.string.file_order_table));
            if(myOrderTable!=null) {
                tvOrderTable.setText("У вас заказан столик на \n"+myOrderTable.getStrDateOrder()+" "+myOrderTable.getStrTimeOrder());
                tvOrderTable.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_STATE_STR_ORDER_TABLE, tvOrderTable.getText().toString());
        outState.putSerializable(KEY_STATE_ORDER_TABLE, myOrderTable);
        super.onSaveInstanceState(outState);
    }

    private void restoreActivityState(Bundle state) {
        if(state!=null) {
            String strOrTb = state.getString(KEY_STATE_STR_ORDER_TABLE);
            tvOrderTable.setText(strOrTb);
            myOrderTable = (OrderTable)state.getSerializable(KEY_STATE_ORDER_TABLE);
            if (myOrderTable != null) {
                tvOrderTable.setVisibility(View.VISIBLE);
            }
        }
    }
    private void createPopupMenu(View v){
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        popup.inflate(R.menu.popup_menu_table);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_del:
                        dataClass.deleteFile(getApplicationContext().getString(R.string.file_order_table));
                        myOrderTable = null;
                        tvOrderTable.setText("");
                        tvOrderTable.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.tst_del_order), Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });
        popup.show();
    }

    public void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMain.this);
        // Get the layout inflater
        LayoutInflater inflater = ActivityMain.this.getLayoutInflater();
        builder.setTitle(getString(R.string.dialog_tittle_order));
        View dialogView = inflater.inflate(R.layout.dialog_order_table, null);
        builder.setView(dialogView);
        TextView textDate = (TextView) dialogView.findViewById(R.id.tv_dialog_date);
        TextView textTime = (TextView) dialogView.findViewById(R.id.tv_dialog_time);
        TextView textHoll = (TextView) dialogView.findViewById(R.id.tv_dialog_holl);
        TextView textCount = (TextView) dialogView.findViewById(R.id.tv_dialog_count);
        TextView textComm = (TextView) dialogView.findViewById(R.id.tv_dialog_comment);
        textDate.setText(myOrderTable.getStrDateOrder());
        textTime.setText(myOrderTable.getStrTimeOrder());
        textHoll.setText(getResources().getStringArray(R.array.arr_type_holl)[myOrderTable.getKHoll()]);
        textCount.setText(Integer.toString(myOrderTable.getCountPeople()));
        textComm.setText(myOrderTable.getDescription());
        builder.create();
        builder.show();
    }

    /*private void showDialogAbout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMain.this);
        builder.setTitle(getString(R.string.dialog_tittle_about));
        builder.setMessage(getString(R.string.dialog_text_about));
        builder.create();
        builder.show();
    }*/

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
