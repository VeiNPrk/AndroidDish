package com.example.vnprk.android1final.Presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;

import com.example.vnprk.android1final.ActivityMain;
import com.example.vnprk.android1final.R;
import com.example.vnprk.android1final.model.DataClass;
import com.example.vnprk.android1final.model.Dish;
import com.example.vnprk.android1final.model.Order;

/**
 * Created by VNPrk on 04.12.2016.
 */
//ПРОБНАЯ РЕАЛИЗАЦИЯ
public class DataPresenter {

    Context context;
    Order order;
    DataClass dataClass;

    public DataPresenter(Context _context) {
        context = _context;
        dataClass = new DataClass(context);
        order = (Order)dataClass.deserealizeObject(context.getString(R.string.file_cash_order));
    }

    public void addPositionToOrder(Dish dish){
        order.addOrderPosition(dish);
        dataClass.serializeObject(order, context.getString(R.string.file_cash_order));
    }

    public void showDialogAbout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.dialog_tittle_about));
        builder.setMessage(context.getString(R.string.dialog_text_about));
        builder.create();
        builder.show();
    }
}
