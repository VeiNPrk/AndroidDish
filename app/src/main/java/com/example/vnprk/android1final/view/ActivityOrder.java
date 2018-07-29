package com.example.vnprk.android1final.view;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vnprk.android1final.Presenter.DataPresenter;
import com.example.vnprk.android1final.R;
import com.example.vnprk.android1final.adapter.DishRecyclerAdapter;
import com.example.vnprk.android1final.adapter.KategoriaRecyclerAdapter;
import com.example.vnprk.android1final.model.DataClass;
import com.example.vnprk.android1final.model.Dish;
import com.example.vnprk.android1final.model.KategoriaDish;
import com.example.vnprk.android1final.model.Order;

import java.util.ArrayList;

/**
 * Created by VNPrk on 01.12.2016.
 */
public class ActivityOrder extends AppCompatActivity implements MyNoticeDialogFragment.NoticeDialogListener{

    public static final String KEY_ORDER_INTENT = "key_order";
    public static final String KEY_NEW_ORDER_INTENT = "key_new_order";
    DishRecyclerAdapter adapter;
    DataClass dataClass;
    DataPresenter presenter;
    Order order;
    RecyclerView rvOrder;
    TextView tvSumOrder;
    TextView tvCountOrder;
    EditText etComment;
    LinearLayout lin_add;
    LinearLayout lin_cancel;
    Button btnAddPosition;
    Button btnFinishOrder;
    Button btnDelOrder;
    ArrayList<Dish> orderDishes;
    ArrayList<KategoriaDish> kategories;

    boolean newOrder;

    public static void openActivity(Context context, boolean newOrder){
        Intent intent = new Intent(context, ActivityOrder.class);
        intent.putExtra(KEY_NEW_ORDER_INTENT, newOrder);
        if (!(context instanceof Activity))
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(newOrder && orderDishes.size()>0) {
            DialogFragment dialog = new MyNoticeDialogFragment(this);
            dialog.show(getFragmentManager(), "NoticeDialogFragment");
        }
        else
            super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        newOrder = getIntent().getBooleanExtra(KEY_NEW_ORDER_INTENT,true);
        presenter = new DataPresenter(this);
        //order = (Order)getIntent().getSerializableExtra(KEY_ORDER_INTENT);
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
        setRecyclerView();
        setClickListener();
        if(newOrder)
            setRecyclerViewItemTouchListener();
    }

    private void initViews(){
        rvOrder = (RecyclerView)findViewById(R.id.rv_order);
        tvSumOrder = (TextView)findViewById(R.id.tv_sum_order);
        tvCountOrder = (TextView)findViewById(R.id.tv_count_order);
        btnAddPosition = (Button)findViewById(R.id.btn_add_position);
        btnFinishOrder = (Button)findViewById(R.id.btn_set_order);
        btnDelOrder = (Button)findViewById(R.id.btn_del_order);
        etComment=(EditText)findViewById(R.id.et_comment);
        lin_add=(LinearLayout)findViewById(R.id.linl_add);
        lin_cancel=(LinearLayout)findViewById(R.id.linl_cancel);
        if(!newOrder) {
            lin_add.setVisibility(View.GONE);
            lin_cancel.setVisibility(View.VISIBLE);
            /*btnAddPosition.setVisibility(View.GONE);
            btnFinishOrder.setVisibility(View.GONE);
            btnDelOrder.setVisibility(View.VISIBLE);*/
            etComment.setEnabled(false);
        }
    }

    private void initData(){
        dataClass = new DataClass(this);
        if(newOrder)
            order = (Order)dataClass.deserealizeObject(getString(R.string.file_cash_order));
        else
            order = (Order)dataClass.deserealizeObject(getString(R.string.file_order));
        orderDishes = order.getOrderDishes();
        kategories = (ArrayList<KategoriaDish>) dataClass.deserealizeObject(getString(R.string.file_kategories));
        tvCountOrder.setText(Integer.toString(order.getOrderDishes().size()));
        tvSumOrder.setText(Integer.toString(order.getTotalPrice()));
        etComment.setText(order.getComment());
    }

    private void setClickListener(){
        btnAddPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openKategories();
            }
        });
        btnFinishOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishOrder();
            }
        });
        btnDelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delOrder();
            }
        });
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new DishRecyclerAdapter(this, orderDishes, kategories, false);
        rvOrder.setLayoutManager(layoutManager);
        rvOrder.setAdapter(adapter);
    }

    private void openKategories(){
        ActivityKategories.openActivity(getApplicationContext());
    }

    private void finishOrder() {
        order.setOrderDishes(orderDishes);
        order.setComment(etComment.getText().toString());
        Intent intent = new Intent();
        //intent.putExtra(KEY_ORDER_INTENT, order);
        dataClass.serializeObject(order, getString(R.string.file_order));
        dataClass.deleteFile(getString(R.string.file_cash_order));
        setResult(RESULT_OK, intent);
        finish();
    }

    private void delOrder(){
        order=null;
        Intent intent = new Intent();
        if(dataClass.deleteFile(getString(R.string.file_order))){
            Toast.makeText(this, getString(R.string.tst_del_order), Toast.LENGTH_SHORT).show();
        }
        setResult(RESULT_OK, intent);
        finish();
    }
    private void setRecyclerViewItemTouchListener() {
        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                deleteOrderPosition(position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvOrder);
    }

    private void deleteOrderPosition(int position) {
        orderDishes.remove(position);
        order.setOrderDishes(orderDishes);
        rvOrder.getAdapter().notifyItemRemoved(position);
        tvCountOrder.setText(Integer.toString(order.getOrderDishes().size()));
        tvSumOrder.setText(Integer.toString(order.getTotalPrice()));
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

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        finishOrder();
        super.onBackPressed();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        super.onBackPressed();
    }

    @Override
    public void onDialogNeutralClick(DialogFragment dialog) {

    }
}
