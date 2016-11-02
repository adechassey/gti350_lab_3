package gti350.lab.meetsports;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    public static final int REQUEST_SIGN_IN = 0;
    public static final int REQUEST_SIGN_UP = 1;
    public static final int REQUEST_CREATE_EVENT = 2;

    public static String user_Email = new String();
    public static String user_Password = new String();
    public static String user_Name = new String();
    public static String user_Surname = new String();
    public static String user_Age = new String();



    @InjectView(R.id.btn_create_event) Button Btn_Create_event;
    @InjectView(R.id.btn_find_event) Button Btn_Find_event;
    @InjectView(R.id.btn_profile) Button Btn_Profile;
    @InjectView(R.id.log_off_link) TextView Link_LogOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, SignInActivity.class);
        startActivityForResult(intent, REQUEST_SIGN_IN);

        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        Btn_Create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEvent(v);
            }
        });

        Btn_Find_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findEvent(v);
            }
        });

        Btn_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile(v);
            }
        });

        Link_LogOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // End session and return to the sign in activity
                logOff(v);
            }
        });
    }

    // Called when the user clicks the create event button
    public void createEvent(View view) {
        Intent createEvent = new Intent(this, CreateEventActivity.class);
        startActivityForResult(createEvent, REQUEST_CREATE_EVENT);
    }


    public void findEvent(View view) {
        Intent intent = new Intent(this, FindEventActivity.class);
        startActivity(intent);
    }

    public void Profile(View view) {
        Intent intent = new Intent(this, SetAccountActivity.class);
        startActivity(intent);
    }

    public void logOff(View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case(0): {
                String welcome_phrase = "Welcome " + MainActivity.user_Name + " " + MainActivity.user_Surname + ", you are now logged in";
                Toast.makeText(getBaseContext(), welcome_phrase , Toast.LENGTH_LONG).show();
                break;
            }

            case (2): {
                if (resultCode == CreateEventActivity.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity.
                    data.getStringExtra("category");
                    data.getStringExtra("type");
                    data.getIntExtra("year", 0);
                    data.getIntExtra("month", 0);
                    data.getIntExtra("day", 0);
                    data.getIntExtra("hour", 0);
                    data.getIntExtra("minute", 0);
                    data.getDoubleExtra("duration", 0);
                    data.getIntExtra("minParticipants", 0);
                    data.getIntExtra("maxParticipants", 0);
                    data.getIntExtra("minAge", 0);
                    data.getIntExtra("maxAge", 0);
                    data.getIntExtra("minContribution", 0);
                    data.getIntExtra("maxContribution", 0);
                    data.getStringExtra("level");

                }
                break;
            }

        }
    }

}
