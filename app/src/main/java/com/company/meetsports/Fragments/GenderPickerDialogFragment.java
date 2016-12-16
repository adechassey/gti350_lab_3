package com.company.meetsports.Fragments;

/**
 * Created by VMabille on 04/11/2016.
 */

import android.content.Context;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import com.company.meetsports.Activities.MainActivity;
import com.company.meetsports.Activities.SignUpActivity;
import com.company.meetsports.R;


public class GenderPickerDialogFragment{

    public static final int SIGN_UP_ACTIVITY = 1;
    public static final int EDIT_PROFILE_ACTIVITY = 2;
    public static String GENDER_PICKED = "";

    public static void showAlertDialog(final Context context, final int parent_activity) {

        final String[] list = new String[]{"Male","Female"};

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title("Select gender");


        builder.items(list);

        builder.itemsCallback(new MaterialDialog.ListCallback() {
            @Override
            public void onSelection(MaterialDialog dialog, View itemView, int item, CharSequence text){
                GENDER_PICKED = list[item];
                if (parent_activity == SIGN_UP_ACTIVITY ){
                    SignUpActivity.Update_gender(GENDER_PICKED);
                } else if (parent_activity == EDIT_PROFILE_ACTIVITY ){
                    ProfileFragment.update_user_infos();
                }
            }
        });

        builder.negativeText("Cancel");

        builder.titleColor(-1);
        builder.positiveColor(-1);
        builder.negativeColor(-1);
        builder.backgroundColorRes(R.color.colorAppBackground);

        builder.show();
    }


}


