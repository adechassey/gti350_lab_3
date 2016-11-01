package gti350.lab.meetsports;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);

        setContentView(R.layout.activity_main);

    }

    // Called when the user clicks the create event button
    public void createEvent(View view) {
        Intent intent = new Intent(this, CreateEventActivity.class);
        startActivity(intent);
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
