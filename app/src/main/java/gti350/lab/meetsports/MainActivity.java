package gti350.lab.meetsports;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    public static final int REQUEST_SIGN_IN = 0;
    public static final int REQUEST_SIGN_UP = 1;
    public static final int REQUEST_CREATE_EVENT = 2;

    public static boolean SESSION_ON = false;

    public static String user_Email = new String();
    public static String user_Password = new String();
    public static String user_Name = new String();
    public static String user_Surname = new String();
    public static String user_Age = new String();

    // Defining Layout variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SESSION_ON) {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivityForResult(intent, REQUEST_SIGN_IN);
        }

        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    //Replacing the main content with fragments
                    case R.id.event:
                        Toast.makeText(getApplicationContext(), "Opening event", Toast.LENGTH_SHORT).show();
                        EventFragment fragment_event = new EventFragment();
                        FragmentTransaction fragmentTransaction_event = getFragmentManager().beginTransaction();
                        fragmentTransaction_event.replace(R.id.frame, fragment_event);
                        fragmentTransaction_event.commit();
                        return true;

                    case R.id.find_event:
                        Toast.makeText(getApplicationContext(), "Opening find event", Toast.LENGTH_SHORT).show();
                        Intent findEvent = new Intent(getApplicationContext(), FindEventActivity.class);
                        startActivity(findEvent);
                        return true;

                    case R.id.create_event:
                        Toast.makeText(getApplicationContext(), "Opening create event", Toast.LENGTH_SHORT).show();
                        Intent createEvent = new Intent(getApplicationContext(), CreateEventActivity.class);
                        startActivityForResult(createEvent, REQUEST_CREATE_EVENT);
                        return true;

                    case R.id.log_out:
                        Toast.makeText(getApplicationContext(), "Logging out", Toast.LENGTH_SHORT).show();
                        Intent logOut = new Intent(getApplicationContext(), SignInActivity.class);
                        startActivity(logOut);
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Opening profile", Toast.LENGTH_SHORT).show();

            ProfileFragment fragment_profile = new ProfileFragment();
            FragmentTransaction fragmentTransaction_profile = getFragmentManager().beginTransaction();
            fragmentTransaction_profile.replace(R.id.frame, fragment_profile);
            fragmentTransaction_profile.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case (0): {
                String welcome_phrase = "Welcome " + MainActivity.user_Name + " " + MainActivity.user_Surname + ", you are now logged in";
                Toast.makeText(getBaseContext(), welcome_phrase, Toast.LENGTH_LONG).show();
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
                    data.getStringExtra("place");
                    data.getStringExtra("address");

                    // Show message on CreateEventActivity finished
                    Snackbar snackbar = Snackbar
                            .make(drawerLayout, "Event created successfully", Snackbar.LENGTH_LONG)
                            .setAction("CANCEL", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Snackbar snackbar1 = Snackbar.make(drawerLayout, "Event has been deleted!", Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                }
                            });
                    snackbar.show();
                }
                break;
            }

        }
    }

}
