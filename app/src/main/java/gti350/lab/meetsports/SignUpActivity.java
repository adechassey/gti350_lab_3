package gti350.lab.meetsports;

import android.os.Bundle;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by VMabille on 31/10/2016.
 */


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    public static int SIGN_UP_SUCCESS = 0;

    @InjectView(R.id.sign_up_name) EditText input_Name;
    @InjectView(R.id.sign_up_surname) EditText input_Surname;
    @InjectView(R.id.sign_up_age) EditText input_Age;
    @InjectView(R.id.sign_up_email) EditText input_Email;
    @InjectView(R.id.sign_up_pw) EditText input_Password;
    @InjectView(R.id.sign_up_pw_confirm) EditText input_Password_confirm;
    @InjectView(R.id.btn_confirm_sign_up) Button Btn_sign_up;
    @InjectView(R.id.sign_up_cancel_link) TextView cancel_sign_up;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);

        Btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sign_up();
            }
        });

        cancel_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // End registration and return to the sign in activity
                finish();
            }
        });
    }

    public void Sign_up() {
        Log.d(TAG, "Signup");

        if (!validate_input_sign_up()) {
            SignupFailed();
            return;
        }

        Btn_sign_up.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();



        // TODO: Implement  signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        Create_Account();

                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void Create_Account() {
        String name = input_Name.getText().toString();
        String surname = input_Name.getText().toString();
        String age = input_Age.getText().toString();
        String email = input_Email.getText().toString();
        String password = input_Password.getText().toString();
        String password_confirm = input_Password_confirm.getText().toString();

        Btn_sign_up.setEnabled(true);
        setResult(RESULT_OK, null);

        MainActivity.user_Name = name;
        MainActivity.user_Surname = surname;
        MainActivity.user_Age = age;
        MainActivity.user_Email = email;
        MainActivity.user_Password = password;

        SIGN_UP_SUCCESS = 1;

        finish();
    }

    public void SignupFailed() {
        Toast.makeText(getBaseContext(), "Sign up failed", Toast.LENGTH_LONG).show();
        Btn_sign_up.setEnabled(true);
    }

    public boolean validate_input_sign_up() {
        boolean valid = true;

        String name = input_Name.getText().toString();
        String surname = input_Surname.getText().toString();
        String age = input_Age.getText().toString();
        String email = input_Email.getText().toString();
        String password = input_Password.getText().toString();
        String password_confirm = input_Password_confirm.getText().toString();

        if (name.isEmpty() || name.length() < 2) {
            input_Name.setError("at least 2 characters");
            valid = false;
        } else {
            input_Name.setError(null);
        }

        if (surname.isEmpty() || surname.length() < 2) {
            input_Surname.setError("at least 2 characters");
            valid = false;
        } else {
            input_Surname.setError(null);
        }

        if (age.isEmpty()) {
            input_Age.setError("please set your age");
            valid = false;
        } else {
            input_Age.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_Email.setError("please enter a valid email address");
            valid = false;
        } else {
            input_Email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            input_Password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            input_Password.setError(null);
        }

        if (!password_confirm.equals(password) ) {
            input_Password_confirm.setError("passwords don't match");
            valid = false;
        } else {
            input_Password_confirm.setError(null);
        }

        return valid;
    }
}
/*
public class SignUpActivity extends FragmentActivity{


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void createAccount(View view) {
        // TODO: save user infos
        finish();
    }

}
*/

