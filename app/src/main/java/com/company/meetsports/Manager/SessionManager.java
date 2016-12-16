package com.company.meetsports.Manager;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.company.meetsports.Activities.MainActivity;
import com.company.meetsports.Activities.SignInActivity;
import com.company.meetsports.Entities.User;
import com.company.meetsports.Fragments.EventFragment;
import com.company.meetsports.R;

import java.util.HashMap;

public class SessionManager {
    private static final String TAG = "SessionManager";

    // Shared Preferences
    private SharedPreferences pref;

    // Editor for Shared preferences
    private Editor editor;

    // Context
    private Context _context;

    // Sharedpref file name
    private static final String PREF_NAME = "MeetSportsPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // Email address (make variable public to access from outside)
    public static final String KEY_ID = "id_user";
    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_AGE = "age";
    public static final String KEY_EMAIL = "email";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(User user) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing ID & email in pref
        editor.putInt(KEY_ID, user.getId_user());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_SURNAME, user.getSurname());
        editor.putString(KEY_GENDER, user.getGender());
        editor.putInt(KEY_AGE, user.getAge());
        editor.putString(KEY_EMAIL, user.getEmail());
        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, SignInActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();

        // user details
        user.put(KEY_ID, String.valueOf(pref.getInt(KEY_ID, 0)));
        user.put(KEY_NAME, pref.getString(KEY_NAME, ""));
        user.put(KEY_SURNAME, pref.getString(KEY_SURNAME, ""));
        user.put(KEY_GENDER, pref.getString(KEY_GENDER, ""));
        user.put(KEY_AGE, String.valueOf(pref.getInt(KEY_AGE, 0)));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, ""));

        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        // After logout redirect user to MainActivity which will redirect to SignInActivity
        Intent i = new Intent(_context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}