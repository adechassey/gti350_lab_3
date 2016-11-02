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


public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";


    @InjectView(R.id.sign_in_email) EditText input_Email;
    @InjectView(R.id.sign_in_pw) EditText input_Password;
    @InjectView(R.id.btn_login) Button Btn_signIn;
    @InjectView(R.id.sign_up_link) TextView sign_up_link;

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

        sign_up_link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, MainActivity.REQUEST_SIGN_UP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate_input_sign_in()) {
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

                        if ( email.equals(MainActivity.user_Email) && password.equals(MainActivity.user_Password)
                                ||  email.equals("user@ms.com") && password.equals("user")){
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
        if (requestCode == MainActivity.REQUEST_SIGN_UP) {
            if (resultCode == RESULT_OK) {

                if(SignUpActivity.SIGN_UP_SUCCESS == 1){
                    // automatically loggs in the new user
                    this.finish();
                }
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

    public boolean validate_input_sign_in() {
        boolean valid = true;
        String email = input_Email.getText().toString();
        String password = input_Password.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_Email.setError("enter a valid email address");
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

        return valid;
    }
}