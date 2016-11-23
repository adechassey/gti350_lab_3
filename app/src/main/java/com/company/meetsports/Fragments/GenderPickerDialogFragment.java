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

    public static void showAlertDialog(final Context context, final int parent_activity) {

        final String[] list = new String[]{"Male","Female"};

        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title("Select gender");


        builder.positiveText("Yes");
        builder.items(list);

        builder.itemsCallback(new MaterialDialog.ListCallback() {
            @Override
            public void onSelection(MaterialDialog dialog, View itemView, int item, CharSequence text){
                /**
                 *
                 *
                 * TODO:
                 * Verifier ci-dessous !
                 *
                 *
                 *
                  */
                /*
                if (parent_activity == SIGN_UP_ACTIVITY ){
                    MainActivity.setGender(list[item]);
                    SignUpActivity.Update_gender(list[item]);
                } else if (parent_activity == EDIT_PROFILE_ACTIVITY){
                    MainActivity.setGender(list[item]);
                    ProfileFragment.display_user_infos();
                }*/
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


