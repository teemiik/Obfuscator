package com.example.webapp.webapp;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by bolgov.artem on 11.09.17.
 */

public class DialogFragmentMes extends DialogFragment {
    public TextView t_alert;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progress_bar, null));

        return builder.create();
    }
}
