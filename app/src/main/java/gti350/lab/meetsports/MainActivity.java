package gti350.lab.meetsports;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static String user_Email = new String();
    public static String user_Password = new String();
    public static String user_Name = new String();
    public static String user_Surname = new String();
    public static String user_Age = new String();

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);

        setContentView(R.layout.activity_main);
    }

    // Called when the user clicks the create event button
    public void createEvent(View view) {
        Intent createEvent = new Intent(this, CreateEventActivity.class);
        startActivityForResult(createEvent, 2); // 2 = requestCode
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
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

    public void findEvent(View view) {
        Intent intent = new Intent(this, FindEventActivity.class);
        startActivity(intent);
    }

    public void setAccount(View view) {
        Intent intent = new Intent(this, SetAccountActivity.class);
        startActivity(intent);
    }

    public void logOff(View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

}
