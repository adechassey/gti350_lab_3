package com.company.meetsports.Fragments;

/**
 * Created by VMabille on 04/11/2016.
 * <p>
 * Created by VMabille on 27/09/2016.
 */
/**
 * Created by VMabille on 27/09/2016.
 */

import android.content.Context;
import android.text.InputType;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.company.meetsports.R;


/**
 * Created by VMabille on 27/09/2016.
 */

public class EditProfileDialogFragment {

    private static String newPassord;

    public static void showAlertDialog(final Context context, String Title, String Hint) {
        // Build the dialog and set up the button click handlers

        final String title = Title;
        final String hint = Hint;

/*        final EditText confirm_pw_input = new EditText(context);
        confirm_pw_input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
        confirm_pw_input.setHint("Confirm password");
        confirm_pw_input.setPadding(0,100,0,30);
*/


        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title(title);
        builder.input(hint, "", new MaterialDialog.InputCallback() {
            @Override
            public void onInput(MaterialDialog dialog, CharSequence input) {
                if (title == "Edit Name") {
                    String name = input.toString();
                } else if (title == "Edit Surname") {
                    String surname = input.toString();
                } else if (title == "Edit Age") {
                    String age = input.toString();
                } else if (title == "Edit E-mail") {
                    String email = input.toString();
                } else if (title == "Edit Password") {

                    String password = input.toString();

                    if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                        Toast.makeText(context, "Passwords need to be between 4 and 10 alphanumeric characters", Toast.LENGTH_LONG).show();
                    } else {
                        newPassord = password;
                        EditProfileDialogFragment confirm_dialog = new EditProfileDialogFragment();
                        confirm_dialog.showAlertDialog(context, "Confirm Password", "confirm password");
                    }

                } else if (title == "Confirm Password") {

                    String password_confirm = input.toString();

                    if (!password_confirm.equals(newPassord)) {
                        Toast.makeText(context, "Passwords don't match", Toast.LENGTH_LONG).show();
                    } else {
                        newPassord = password_confirm;
                        Toast.makeText(context, "Password changed succesfully", Toast.LENGTH_LONG).show();
                    }

                }

                if (title != "Enter number of participants") {
                    ProfileFragment.display_user_infos();

                }
            }
        });

        if (title == "Edit E-mail") {
            builder.inputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        } else if (title == "Edit Password" || title == "Confirm Password") {
            builder.inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
        } else if (title == "Edit Age" || title == "Enter number of participants") {
            builder.inputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            builder.inputType(InputType.TYPE_CLASS_TEXT);
        }


        builder.negativeText("Cancel");
        builder.titleColor(-1);
        builder.positiveColor(-1);
        builder.negativeColor(-1);
        builder.backgroundColorRes(R.color.colorAppBackground);


        builder.show();


    }


}
