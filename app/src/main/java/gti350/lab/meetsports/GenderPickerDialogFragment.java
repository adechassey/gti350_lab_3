package gti350.lab.meetsports;

/**
 * Created by VMabille on 04/11/2016.
 */

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;



public class GenderPickerDialogFragment{

    public static final int SIGN_UP_ACTIVITY = 1;
    public static final int EDIT_PROFILE_ACTIVITY = 2;

    public static void showAlertDialog(final Context context, final int parent_activity) {

        final String[] list = new String[]{"Male","Female"};


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select gender");
        builder.setItems(list, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                if (parent_activity == SIGN_UP_ACTIVITY ){
                    MainActivity.setGender(list[item]);
                    SignUpActivity.Update_gender(list[item]);
                } else if (parent_activity == EDIT_PROFILE_ACTIVITY){
                    MainActivity.setGender(list[item]);
                    SetAccountActivity.display_user_infos();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create();


        builder.show();
    }


}


