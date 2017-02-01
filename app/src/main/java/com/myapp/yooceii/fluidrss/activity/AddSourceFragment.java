package com.myapp.yooceii.fluidrss.activity;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.myapp.yooceii.fluidrss.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSourceFragment extends DialogFragment {
    private EditText mEditText;
    private String source_link;
    private Callback callback;
    public static final String SOURCE_LINK="com.myapp.yooceii.fluidrss.activity.AddSourceFragment.source_link";
    public AddSourceFragment() {
        // Required empty public constructor
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 设置主题的构造方法
        // AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_source, null);
        mEditText=(EditText)view.findViewById(R.id.add_rss_source_edittext);
        builder.setView(view);
        builder.setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        callback.getString(mEditText.getText().toString());
                        //dismiss();
                    }
                })

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        return builder.create();
    }
    public void getString(Callback callback) {
        String msg = mEditText.getText().toString();
        callback.getString(msg);
    }

    public interface Callback {
        public void getString(String msg);
    }
}
