package gti350.lab.meetsports;

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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by VMabille on 31/10/2016.
 */

/*
public class SignInActivity extends FragmentActivity {

    private Button Btn_sign_in, Btn_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


    }

    public void SignIn(View view) {
        // TODO : check for user infos
        finish();
        // -> returns to main activity
    }

    public void SignUp(View view) {
        Intent intent_sign_up = new Intent(this, SignUpActivity.class);
        startActivity(intent_sign_up);
        finish(); // -> returns to main activity
    }



}


*/



public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;


    @InjectView(R.id.sign_in_email) EditText input_Email;
    @InjectView(R.id.sign_in_pw) EditText input_Password;
    @InjectView(R.id.btn_login) Button Btn_signIn;
    @InjectView(R.id.sign_up_link) TextView Btn_signUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.inject(this);

        Btn_signIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        Btn_signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate_input()) {
            LoginFailed();
            return;
        }

        Btn_signIn.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignInActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();




        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        String email = input_Email.getText().toString();
                        String password = input_Password.getText().toString();

                        if ( email.toLowerCase().contains(MainActivity.user_Email.toLowerCase()) && password.toLowerCase().contains(MainActivity.user_Password.toLowerCase())
                                ||  email.toLowerCase().contains("user@ms.com".toLowerCase()) && password.toLowerCase().contains("user".toLowerCase())){
                            LoginSuccess();
                        } else {
                            LoginFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void LoginSuccess() {
        Btn_signIn.setEnabled(true);
        finish();
    }

    public void LoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        Btn_signIn.setEnabled(true);
    }

    public boolean validate_input() {
        boolean valid_input = true;
        String email = input_Email.getText().toString();
        String password = input_Password.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_Email.setError("enter a valid email address");
            valid_input = false;
        } else {
            input_Email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            input_Password.setError("between 4 and 10 alphanumeric characters");
            valid_input = false;
        } else {
            input_Password.setError(null);
        }

        return valid_input;
    }
}