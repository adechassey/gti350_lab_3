package gti350.lab.meetsports;

/**
 * Created by VMabille on 04/11/2016.
 */
/**
 * Created by VMabille on 27/09/2016.
 */

import gti350.lab.meetsports.MainActivity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.text.InputType;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;


/**
 * Created by VMabille on 27/09/2016.
 */

public class EditProfileDialogFragment {

    public static void showAlertDialog(final Context context, String Title, String Hint) {
        // Build the dialog and set up the button click handlers

        final String title = Title ;
        final String hint = Hint ;

        final EditText input = new EditText(context);
        final EditText confirm_pw_input = new EditText(context);
        confirm_pw_input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
        confirm_pw_input.setHint("Confirm password");
        confirm_pw_input.setPadding(0,100,0,30);


        if (title == "Edit E-mail"){
            input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        } else if (title == "Edit Password"){
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
        } else if (title == "Edit Age"){
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            input.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        input.setHint(hint);
        input.setPadding(0,100,0,30);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setView(input);
        if (title == "Edit Password"){
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(input);
            layout.addView(confirm_pw_input);
            builder.setView(layout);
        } else {
            builder.setView(input);
        }

        builder.setPositiveButton("Validate", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        input.getText();

                        if (title == "Edit Name"){
                            MainActivity.user_Name = input.getText().toString();
                        } else if (title == "Edit Surname"){
                            MainActivity.user_Surname = input.getText().toString();
                        } else if (title == "Edit Age"){
                            MainActivity.user_Age = input.getText().toString();
                        } else if (title == "Edit E-mail"){
                            MainActivity.user_Email = input.getText().toString();
                        } else if (title == "Edit Password"){

                            String password = input.getText().toString();
                            String confirm_password = confirm_pw_input.getText().toString();

                            if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                                Toast.makeText(context, "Passwords need to be between 4 and 10 alphanumeric characters" , Toast.LENGTH_LONG).show();
                            }

                            if (!confirm_password.equals(password) ) {
                                Toast.makeText(context, "Passwords don't match" , Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Password changed succesfully" , Toast.LENGTH_LONG).show();
                            }

                        }
                        ProfileFragment.display_user_infos();
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                    }
                });
        builder.show();



    }






}
