package com.company.meetsports.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import com.company.meetsports.Manager.SessionManager;
import com.company.meetsports.R;

/**
 * Created by VMabille on 05/11/2016.
 */

public class LogOutDialogFragment extends DialogFragment {

    // Session Manager Class
    private SessionManager session;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Session class instance
        session = new SessionManager(getActivity());

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
        builder.title("Do you really want to leave ?");


        builder.positiveText("Yes");
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                session.logoutUser();
                Toast.makeText(getActivity(), "Come back soon !", Toast.LENGTH_SHORT).show();
            }
        });
        builder.negativeText("Cancel");

        builder.titleColor(-1);
        builder.positiveColor(-1);
        builder.negativeColor(-1);
        builder.backgroundColorRes(R.color.colorAppBackground);

        return builder.show();
    }

}
