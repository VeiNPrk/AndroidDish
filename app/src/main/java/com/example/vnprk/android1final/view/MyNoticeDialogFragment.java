package com.example.vnprk.android1final.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.vnprk.android1final.R;

/**
 * Created by VNPrk on 05.12.2016.
 */
public class MyNoticeDialogFragment extends DialogFragment {

    Context context;

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);//сохранить
        public void onDialogNegativeClick(DialogFragment dialog);//не сохранять
        public void onDialogNeutralClick(DialogFragment dialog);//отмена
    }

    NoticeDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public MyNoticeDialogFragment(Context _context) {
        context=_context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(context.getString(R.string.dialog_notice_tittle));
                builder.setMessage(context.getString(R.string.dialog_notice_message))
                        .setPositiveButton(context.getString(R.string.dialog_notise_positive), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mListener.onDialogPositiveClick(MyNoticeDialogFragment.this);
                            }
                        })
                        .setNeutralButton(context.getString(R.string.dialog_notise_neutral), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mListener.onDialogNeutralClick(MyNoticeDialogFragment.this);
                            }
                        })
                        .setNegativeButton(context.getString(R.string.dialog_notise_negative), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mListener.onDialogNegativeClick(MyNoticeDialogFragment.this);
                            }
                        });
        // Create the AlertDialog object and return it
        return builder.create();
    }



}
