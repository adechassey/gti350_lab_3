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

/**
 * Created by VMabille on 31/10/2016.
 */


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


