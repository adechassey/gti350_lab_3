package com.company.meetsports.Activities;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.MenuItem;
import android.view.MenuInflater;
import 	android.view.Menu;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.PopupMenu;
import android.widget.AdapterView;

import com.appyvet.rangebar.RangeBar;
import com.company.meetsports.DataProvider.ApiClient;
import com.company.meetsports.DataProvider.ApiInterface;
import com.company.meetsports.Entities.Event;
import com.company.meetsports.Fragments.CreateEventFragment;
import com.company.meetsports.Fragments.EventFragment;
import com.company.meetsports.Fragments.InfoFragment;
import com.company.meetsports.Fragments.LogOutDialogFragment;
import com.company.meetsports.Fragments.ProfileFragment;
import com.company.meetsports.Fragments.FindEventFragment;

import com.company.meetsports.Manager.SessionManager;
import com.company.meetsports.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.lang.reflect.Field;
import java.util.Calendar;
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
    public static final int REQUEST_PLACE_PICKER = 6;

    // Permissions
    public static final int PERMISSIONS_REQUEST_CAMERA = 10;
    public static final int PERMISSIONS_REQUEST_GALLERY = 11;

    private SessionManager session;
    public static HashMap<String, String> user = new HashMap<>();
    public static Integer id_user;

    public static TextView header_name;
    public static TextView header_email;

    // Defining Layout variables
    private Toolbar toolbar;
    private TextView Button_My_Events;
    private TextView Button_Find_Events;
    private TextView Button_Create_Event;
    private View view_MyEvents;
    private View view_FindEvents;
    private View view_CreateEvent;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate...");

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */

        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn() == false) {

            Intent signInIntent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivityForResult(signInIntent, REQUEST_SIGN_IN);
        }

        // get user data from session
        user = session.getUserDetails();
        // id_user
        id_user = Integer.valueOf(user.get(SessionManager.KEY_ID));

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        setContentView(R.layout.activity_main);

        // drawer layout for the main activity
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);


        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        view_MyEvents = (View) findViewById(R.id.view_my_events);
        view_FindEvents = (View) findViewById(R.id.view_find_events);
        view_CreateEvent = (View) findViewById(R.id.view_create_event);


        final ImageButton toolbar_menu = (ImageButton) toolbar.findViewById(R.id.toolbar_menu);
        toolbar_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu

                PopupMenu popup = new PopupMenu(MainActivity.this, toolbar_menu);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.toolbar_menu, popup.getMenu());
                Object menuHelper;
                Class[] argTypes;
                try {
                    Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
                    fMenuHelper.setAccessible(true);
                    menuHelper = fMenuHelper.get(popup);
                    argTypes = new Class[] { boolean.class };
                    menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
                } catch (Exception e) {
                    // Possible exceptions are NoSuchMethodError and NoSuchFieldError
                    //
                    // In either case, an exception indicates something is wrong with the reflection code, or the
                    // structure of the PopupMenu class or its dependencies has changed.
                    //
                    // These exceptions should never happen since we're shipping the AppCompat library in our own apk,
                    // but in the case that they do, we simply can't force icons to display, so log the error and
                    // show the menu normally.

                    Log.w(TAG, "error forcing menu icons to show", e);
                    popup.show();
                    return;
                }

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch(item.getItemId()){

                            case R.id.toolbar_menu_profile:
                                ProfileFragment fragment_profile = new ProfileFragment();
                                FragmentTransaction fragmentTransaction_profile = getFragmentManager().beginTransaction();
                                fragmentTransaction_profile.replace(R.id.frame, fragment_profile);
                                fragmentTransaction_profile.commit();
                                return true;

                            case R.id.toolbar_menu_logout:
                                LogOutDialogFragment log_out_dialog = new LogOutDialogFragment();
                                log_out_dialog.show(getFragmentManager(), "LogOutFragmentDialog");
                                return true;

                            case R.id.toolbar_menu_aboutus:
                                InfoFragment fragment_info = new InfoFragment();
                                FragmentTransaction fragmentTransaction_info = getFragmentManager().beginTransaction();
                                fragmentTransaction_info.replace(R.id.frame, fragment_info);
                                fragmentTransaction_info.commit();
                                return true;

                        }
                        return true;
                    }


                });


                popup.show(); //showing popup menu

            }
        }); //closing the setOnClickListener method


        Button_My_Events = (TextView) findViewById(R.id.header_menu_my_events);
        Button_My_Events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the MyEvents fragment
                reload_header_menu_display();
                Update_menu_view(Button_My_Events, view_MyEvents);
                EventFragment fragment_my_events = new EventFragment();
                FragmentTransaction fragmentTransaction_my_events = getFragmentManager().beginTransaction();
                fragmentTransaction_my_events.replace(R.id.frame, fragment_my_events);
                fragmentTransaction_my_events.commit();
            }
        });

        Button_Find_Events = (TextView) findViewById(R.id.header_menu_find_events);
        Button_Find_Events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the FindEvent fragment
                reload_header_menu_display();
                Update_menu_view(Button_Find_Events, view_FindEvents);
                FindEventFragment fragment_find_events = new FindEventFragment();
                FragmentTransaction fragmentTransaction_find_events = getFragmentManager().beginTransaction();
                fragmentTransaction_find_events.replace(R.id.frame, fragment_find_events);
                fragmentTransaction_find_events.commit();
            }
        });

        Button_Create_Event = (TextView) findViewById(R.id.header_menu_create_events);
        Button_Create_Event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   // Start the Create Event fragment
                reload_header_menu_display();
                Update_menu_view(Button_Create_Event, view_CreateEvent);
                CreateEventFragment fragment_create_event = new CreateEventFragment();
                FragmentTransaction fragmentTransaction_create_event = getFragmentManager().beginTransaction();
                fragmentTransaction_create_event.replace(R.id.frame, fragment_create_event);
                fragmentTransaction_create_event.commit();
            }
        });



        // Setting the Fragment to event
        EventFragment fragment_event = new EventFragment();
        FragmentTransaction fragmentTransaction_event = getFragmentManager().beginTransaction();
        fragmentTransaction_event.replace(R.id.frame, fragment_event);
        fragmentTransaction_event.commit();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // From SignInActivity
            case (REQUEST_SIGN_IN):
                if (resultCode == SignInActivity.RESULT_OK) {
                    Log.d(TAG, "onActivityResult SignIn RESULT_OK");

                    // get user data from session
                    user = session.getUserDetails();
                    // id_user
                    id_user = Integer.valueOf(user.get(SessionManager.KEY_ID));

                    header_name.setText(user.get(SessionManager.KEY_SURNAME) + " " + user.get(SessionManager.KEY_NAME));
                    header_email.setText(user.get(SessionManager.KEY_EMAIL));
                    Toast.makeText(getApplicationContext(), "Welcome " + user.get(SessionManager.KEY_SURNAME) + " " + user.get(SessionManager.KEY_NAME) + ", you are now logged in", Toast.LENGTH_LONG).show();

                    EventFragment fragment_event = new EventFragment();
                    FragmentTransaction fragmentTransaction_event = getFragmentManager().beginTransaction();
                    fragmentTransaction_event.replace(R.id.frame, fragment_event);
                    fragmentTransaction_event.commit();

                }
                break;

            // From SignUpActivity
            case (REQUEST_SIGN_UP):
                if (resultCode == SignUpActivity.RESULT_OK) {
                    Log.d(TAG, "onActivityResult SignUp RESULT_OK");
                }
                break;


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

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        Log.d(TAG, "onResume after signing in!");
    }



    public void Update_menu_view(TextView button_event, View underline) {

        button_event.setTypeface(null, Typeface.BOLD);
        button_event.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        ViewGroup.LayoutParams params = underline.getLayoutParams();
        params.height = 9;
        underline.setLayoutParams(params);

    }

    public void reload_header_menu_display() {


        Button_My_Events.setTypeface(null, Typeface.NORMAL);
        Button_My_Events.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

        Button_Find_Events.setTypeface(null, Typeface.NORMAL);
        Button_Find_Events.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

        Button_Create_Event.setTypeface(null, Typeface.NORMAL);
        Button_Create_Event.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);


        ViewGroup.LayoutParams params_my = view_MyEvents.getLayoutParams();
        ViewGroup.LayoutParams params_find = view_FindEvents.getLayoutParams();
        ViewGroup.LayoutParams params_create = view_CreateEvent.getLayoutParams();

        params_my.height = 6;
        params_find.height = 6;
        params_create.height = 6;

        view_MyEvents.setLayoutParams(params_my);
        view_FindEvents.setLayoutParams(params_find);
        view_CreateEvent.setLayoutParams(params_create);

    }


}