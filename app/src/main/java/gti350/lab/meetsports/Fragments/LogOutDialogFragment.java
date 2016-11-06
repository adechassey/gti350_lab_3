package gti350.lab.meetsports.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import gti350.lab.meetsports.R;
import gti350.lab.meetsports.Activities.SignInActivity;

/**
 * Created by VMabille on 05/11/2016.
 */

public class LogOutDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
        builder.title("Do you really want to leave ?");


        builder.positiveText("Yes");
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                Intent logOut = new Intent(getActivity(), SignInActivity.class);
                startActivity(logOut);
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
