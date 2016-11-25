package com.company.meetsports.Activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.company.meetsports.DataProvider.ApiClient;
import com.company.meetsports.DataProvider.ApiInterface;
import com.company.meetsports.Entities.Event;
import com.company.meetsports.Fragments.DebugFragment;
import com.company.meetsports.Fragments.EventFragment;
import com.company.meetsports.Fragments.InfoFragment;
import com.company.meetsports.Fragments.LogOutDialogFragment;
import com.company.meetsports.Fragments.ProfileFragment;
import com.company.meetsports.Manager.SessionManager;
import com.company.meetsports.R;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    // Activity request
    public static final int REQUEST_SIGN_IN = 0;
    public static final int REQUEST_SIGN_UP = 1;
    public static final int REQUEST_CREATE_EVENT = 2;
    public static final int REQUEST_FIND_EVENT = 3;
    public static final int REQUEST_CAMERA = 4;
    public static final int REQUEST_GALLERY = 5;

    // Permissions
    public static final int PERMISSIONS_REQUEST_CAMERA = 10;
    public static final int PERMISSIONS_REQUEST_GALLERY = 11;

    // Session Manager
    public static SessionManager session;
    public static HashMap<String, String> user = new HashMap<>();
    public static Integer id_user;
    public static TextView header_name;
    public static TextView header_email;

    // Defining Layout variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Session class instance
        session = new SessionManager(getApplicationContext());

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        // get user data from session
        user = session.getUserDetails();
        // id_user
        id_user = Integer.valueOf(user.get(SessionManager.KEY_ID));

        setContentView(R.layout.activity_main);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View header = navigationView.getHeaderView(0);
        header_name = (TextView) header.findViewById(R.id.header_username);
        header_email = (TextView) header.findViewById(R.id.header_email);
        header_name.setText(user.get(SessionManager.KEY_SURNAME) + " " + user.get(SessionManager.KEY_NAME));
        header_email.setText(user.get(SessionManager.KEY_EMAIL));


        // Setting the Fragment to event
        EventFragment fragment_event = new EventFragment();
        FragmentTransaction fragmentTransaction_event = getFragmentManager().beginTransaction();
        fragmentTransaction_event.replace(R.id.frame, fragment_event);
        fragmentTransaction_event.commit();

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
                        startActivityForResult(findEvent, REQUEST_FIND_EVENT);
                        return true;

                    case R.id.create_event:
                        Toast.makeText(getApplicationContext(), "Opening create event", Toast.LENGTH_SHORT).show();
                        Intent createEvent = new Intent(getApplicationContext(), CreateEventActivity.class);
                        startActivityForResult(createEvent, REQUEST_CREATE_EVENT);
                        return true;

                    case R.id.profile:
                        ProfileFragment fragment_profile = new ProfileFragment();
                        FragmentTransaction fragmentTransaction_profile = getFragmentManager().beginTransaction();
                        fragmentTransaction_profile.replace(R.id.frame, fragment_profile);
                        fragmentTransaction_profile.commit();
                        return true;

                    case R.id.log_out:
                        LogOutDialogFragment log_out_dialog = new LogOutDialogFragment();
                        log_out_dialog.show(getFragmentManager(), "LogOutFragmentDialog");
                        return true;

                    case R.id.info:
                        InfoFragment fragment_info = new InfoFragment();
                        FragmentTransaction fragmentTransaction_info = getFragmentManager().beginTransaction();
                        fragmentTransaction_info.replace(R.id.frame, fragment_info);
                        fragmentTransaction_info.commit();
                        return true;

                    case R.id.debug:
                        DebugFragment fragment_debug = new DebugFragment();
                        FragmentTransaction fragmentTransaction_debug = getFragmentManager().beginTransaction();
                        fragmentTransaction_debug.replace(R.id.frame, fragment_debug);
                        fragmentTransaction_debug.commit();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // From CreateEventActivity
            case (2): {
                if (resultCode == CreateEventActivity.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity.
                    String category = data.getStringExtra("category");
                    String type = data.getStringExtra("type");
                    Integer year = data.getIntExtra("year", 0);
                    Integer month = data.getIntExtra("month", 0);
                    Integer day = data.getIntExtra("day", 0);
                    Integer hour = data.getIntExtra("hour", 0);
                    Integer minute = data.getIntExtra("minute", 0);
                    Double minDuration = data.getDoubleExtra("minDuration", 0);
                    Double maxDuration = data.getDoubleExtra("maxDuration", 0);
                    Integer minParticipants = data.getIntExtra("minParticipants", 0);
                    Integer maxParticipants = data.getIntExtra("maxParticipants", 0);
                    Integer minAge = data.getIntExtra("minAge", 0);
                    Integer maxAge = data.getIntExtra("maxAge", 0);
                    Integer minContribution = data.getIntExtra("minContribution", 0);
                    Integer maxContribution = data.getIntExtra("maxContribution", 0);
                    String level = data.getStringExtra("level");
                    String place = data.getStringExtra("place");
                    String address = data.getStringExtra("address");

                    Event newEvent = new Event(null, id_user, category, type, null, minDuration, maxDuration, minParticipants, maxParticipants, minAge, maxAge, minContribution, maxContribution, level, place, address);
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                    final Call<ResponseBody> callEvent = apiService.addEvent(newEvent);
                    callEvent.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            int statusCode = response.code();
                            Log.d(TAG, "Status code: " + String.valueOf(statusCode));

                            EventFragment fragment_event = new EventFragment();
                            FragmentTransaction fragmentTransaction_event = getFragmentManager().beginTransaction();
                            fragmentTransaction_event.replace(R.id.frame, fragment_event);
                            fragmentTransaction_event.commit();

                            // Show message on CreateEventActivity finished
                            Snackbar snackbar = Snackbar
                                    .make(drawerLayout, "Event created successfully", Snackbar.LENGTH_LONG)
                                    .setAction("CANCEL", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                                            Call<List<Event>> call = apiService.getEventsCreatedByUserId(id_user);
                                            call.enqueue(new Callback<List<Event>>() {
                                                @Override
                                                public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                                                    int statusCode = response.code();
                                                    Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                                                    List<Event> myEvents = response.body();
                                                    // Get the id of the last created event
                                                    Integer id_event = myEvents.get(0).getId_event();
                                                    Call<ResponseBody> callDelete = apiService.deleteEvent(id_event);
                                                    callDelete.enqueue(new Callback<ResponseBody>() {
                                                        @Override
                                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                            int statusCode = response.code();
                                                            Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                                                            EventFragment fragment_event = new EventFragment();
                                                            FragmentTransaction fragmentTransaction_event = getFragmentManager().beginTransaction();
                                                            fragmentTransaction_event.replace(R.id.frame, fragment_event);
                                                            fragmentTransaction_event.commit();
                                                            Snackbar snackbar = Snackbar.make(drawerLayout, "Event has been deleted!", Snackbar.LENGTH_SHORT);
                                                            snackbar.show();
                                                        }

                                                        @Override
                                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                            // Log error here since request failed
                                                            Log.e(TAG, t.toString());
                                                            Snackbar snackbar = Snackbar.make(drawerLayout, "Please check your internet connexion!", Snackbar.LENGTH_SHORT);
                                                            snackbar.show();
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onFailure(Call<List<Event>> call, Throwable t) {
                                                    // Log error here since request failed
                                                    Log.e(TAG, t.toString());

                                                }
                                            });


                                        }
                                    });
                            snackbar.show();


                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            // Log error here since request failed
                            Log.e(TAG, t.toString());
                        }
                    });


                }
                break;
            }
            // From FindEventActivity
            case (3): {
                if (resultCode == FindEventActivity.RESULT_OK) {

                    EventFragment fragment_event = new EventFragment();
                    Toast.makeText(getApplicationContext(), "Event selected", Toast.LENGTH_SHORT).show();
                    FragmentTransaction fragmentTransaction_event = getFragmentManager().beginTransaction();
                    fragmentTransaction_event.replace(R.id.frame, fragment_event);
                    fragmentTransaction_event.commit();

                } else if (resultCode == FindEventActivity.RESULT_CANCELED) {

                    Toast.makeText(getApplicationContext(), "You already selected this event", Toast.LENGTH_SHORT).show();
                    Intent findEvent = new Intent(getApplicationContext(), FindEventActivity.class);
                    startActivityForResult(findEvent, REQUEST_FIND_EVENT);
                }
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, REQUEST_CAMERA);
                    Toast.makeText(getApplicationContext(), "Permission granted to access camera", Toast.LENGTH_SHORT).show();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "Permission denied to access camera", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case PERMISSIONS_REQUEST_GALLERY: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent loadIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(loadIntent, REQUEST_GALLERY);
                    Toast.makeText(getApplicationContext(), "Permission granted to access media content", Toast.LENGTH_SHORT).show();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "Permission denied to access media content", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}