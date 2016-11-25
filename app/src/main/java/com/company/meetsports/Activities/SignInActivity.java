package com.company.meetsports.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.company.meetsports.DataProvider.ApiClient;
import com.company.meetsports.DataProvider.ApiInterface;
import com.company.meetsports.Entities.User;
import com.company.meetsports.Manager.SessionManager;
import com.company.meetsports.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.company.meetsports.Activities.MainActivity.REQUEST_SIGN_UP;

/**
 * Created by VMabille on 31/10/2016.
 */


public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private SessionManager session;

    private String email;
    private String password;

    @InjectView(R.id.sign_in_email)
    EditText input_Email;
    @InjectView(R.id.sign_in_pw)
    EditText input_Password;
    @InjectView(R.id.btn_login)
    Button Btn_signIn;
    @InjectView(R.id.sign_up_link)
    TextView sign_up_link;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate...");
        setContentView(R.layout.activity_sign_in);
        ButterKnife.inject(this);

        // Session class instance
        session = new SessionManager(getApplicationContext());

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
                startActivityForResult(intent, REQUEST_SIGN_UP);
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

                        email = input_Email.getText().toString();
                        password = input_Password.getText().toString();

                        if (password.equals("user")) {
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
        if (requestCode == REQUEST_SIGN_UP) {
            if (resultCode == SignUpActivity.RESULT_OK) {
                    // automatically loggs in the new user
                    // this.finish();
                    recreate();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void LoginSuccess() {
        // Creating user login session
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<User> call = apiService.getUserByUsername(email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int statusCode = response.code();
                Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                if (statusCode == 200) {
                    // Remove the item on remove/button click
                    User user = response.body();

                    //session = new SessionManager(getApplicationContext());
                    // set id_user
                    //id_user = user.getId_user();

                    // Session Manager
                    session.createLoginSession(user);

                    Btn_signIn.setEnabled(true);

                    setResult(SignInActivity.RESULT_OK);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
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