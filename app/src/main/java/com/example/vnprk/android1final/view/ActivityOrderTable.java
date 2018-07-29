package com.example.vnprk.android1final.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.vnprk.android1final.Presenter.DataPresenter;
import com.example.vnprk.android1final.R;
import com.example.vnprk.android1final.adapter.DatePickerFragment;
import com.example.vnprk.android1final.adapter.KategoriaRecyclerAdapter;
import com.example.vnprk.android1final.adapter.TimePickerFragment;
import com.example.vnprk.android1final.model.DataClass;
import com.example.vnprk.android1final.model.KategoriaDish;
import com.example.vnprk.android1final.model.OrderTable;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Администратор on 03.12.2016.
 */
public class ActivityOrderTable extends AppCompatActivity implements TimePickerFragment.OnTimeCompleteListener, DatePickerFragment.OnDateCompleteListener{

    private static final String KEY_STATE_STR_DATE = "state_date_str";
    private static final String KEY_STATE_STR_TIME = "state_time_str";
    private static final String KEY_STATE_COUNT = "state_count_people";
    private static final String KEY_STATE_STR_COMMENT = "state_comment";

    Spinner spnTypeHoll;
    EditText etComment;
    TextView tvDateOrder;
    TextView tvTimeOrder;
    Button btnDate;
    Button btnTime;
    Button btnOrderTable;
    Calendar orderTime =null;
    DataClass dataClass;
    DataPresenter presenter;
    OrderTable orderTable;
    NumberPicker numpCountPeople;
    KategoriaRecyclerAdapter adapter;
    ArrayList<KategoriaDish> dishes;

    String strDate ="";
    String strTime ="";

    public static void openActivity(Context context){
        Intent intent = new Intent(context, ActivityOrderTable.class);
        if (!(context instanceof Activity))
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_table);
        presenter = new DataPresenter(this);
        initViews();
        initCalendar();
        setClickListeners();
        setSpinner();
        restoreActivityState(savedInstanceState);
    }

    private void initViews() {
        spnTypeHoll = (Spinner)findViewById(R.id.spn_type_holl);
        etComment = (EditText)findViewById(R.id.et_order_t_description);
        tvDateOrder = (TextView)findViewById(R.id.tv_cal_date);
        tvTimeOrder = (TextView)findViewById(R.id.tv_cal_time);
        btnDate = (Button)findViewById(R.id.btn_cal_date);
        btnTime = (Button)findViewById(R.id.btn_cal_time);
        btnOrderTable=(Button)findViewById(R.id.btn_set_order_table);
        numpCountPeople = (NumberPicker) findViewById(R.id.nump_count_people);
        numpCountPeople.setMaxValue(9);
        numpCountPeople.setMinValue(1);
    }

    private void initCalendar() {
        if(orderTime==null)
            orderTime = Calendar.getInstance();
        orderTable = new OrderTable();
    }

    private void setClickListeners() {
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
        btnOrderTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrderTable();
            }
        });
    }

    private void saveOrderTable() {
        orderTable.setDateOrder(orderTime);
        orderTable.setCountPeople(numpCountPeople.getValue());
        orderTable.setkHoll(spnTypeHoll.getSelectedItemPosition());
        orderTable.setDescription(etComment.getText().toString());
        Intent intent = new Intent();
        dataClass = new DataClass(this);
        dataClass.serializeObject(orderTable, getString(R.string.file_order_table));
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setSpinner(){
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.arr_type_holl, android.R.layout.simple_spinner_item);
        spnTypeHoll.setAdapter(adapter);
    }

    @Override
    public void onTimeComplete(Calendar time) {
        int hour=time.get(Calendar.HOUR_OF_DAY);
        int minute=time.get(Calendar.MINUTE);
        orderTime.set(Calendar.HOUR_OF_DAY, hour);
        orderTime.set(Calendar.MINUTE, minute);
        strTime = String.format("%02d:%02d", hour, minute);
        tvTimeOrder.setText(strTime);
    }

    @Override
    public void onDateComplete(Calendar time) {
        int year=time.get(Calendar.YEAR);
        int month=time.get(Calendar.MONTH);
        int day = time.get(Calendar.DAY_OF_MONTH);
        orderTime.set(Calendar.YEAR, year);
        orderTime.set(Calendar.MONTH, month);
        orderTime.set(Calendar.DAY_OF_MONTH, day);
        strDate=String.format("%02d.%02d.%02d", day, month+1, year);
        tvDateOrder.setText(strDate);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_STATE_STR_DATE, strDate);
        outState.putString(KEY_STATE_STR_TIME, strTime);
        outState.putString(KEY_STATE_STR_COMMENT, etComment.getText().toString());
        outState.putInt(KEY_STATE_COUNT, numpCountPeople.getValue());
        super.onSaveInstanceState(outState);
    }

    private void restoreActivityState(Bundle state) {
        if(state!=null){
            String strD=state.getString(KEY_STATE_STR_DATE);
            tvDateOrder.setText(strD);
            String strT=state.getString(KEY_STATE_STR_TIME);
            tvTimeOrder.setText(strT);
            String com = state.getString(KEY_STATE_STR_COMMENT);
            etComment.setText(com);
            int coun = state.getInt(KEY_STATE_COUNT);
            numpCountPeople.setValue(coun);
        }
    }
    public void showTimePickerDialog()
    {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog()
    {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"datePicker");
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
