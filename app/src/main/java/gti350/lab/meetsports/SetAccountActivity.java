package gti350.lab.meetsports;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.TextView;

import gti350.lab.meetsports.GenderPickerDialogFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by VMabille on 31/10/2016.
 */


public class SetAccountActivity extends AppCompatActivity {

    @InjectView(R.id.profile_edit_name) TextView edit_name;
    @InjectView(R.id.profile_edit_surname) TextView edit_surname;
    @InjectView(R.id.profile_edit_age) TextView edit_age;
    @InjectView(R.id.profile_edit_gender) TextView edit_gender;
    @InjectView(R.id.profile_edit_email) TextView edit_email;
    @InjectView(R.id.profile_edit_pw) TextView edit_password;

    public static TextView profile_name ;
    public static TextView profile_surname;
    public static TextView profile_age;
    public static TextView profile_gender;
    public static TextView profile_email;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        ButterKnife.inject(this);

        profile_name = (TextView)  findViewById(R.id.profile_value_name);
        profile_surname = (TextView)  findViewById(R.id.profile_value_surname);
        profile_age = (TextView)  findViewById(R.id.profile_value_age);
        profile_gender = (TextView)  findViewById(R.id.profile_value_gender);
        profile_email = (TextView)  findViewById(R.id.profile_value_email);

        display_user_infos();


        edit_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // End session and return to the sign in activity
                Bundle bundle = new Bundle();
                bundle.putString("edit_title", "Edit Name");
                bundle.putString("edit_hint", "New name");

                DialogFragment dialog_editProfile = new EditProfileDialogFragment();
                dialog_editProfile.setArguments(bundle);
                dialog_editProfile.show(getSupportFragmentManager(), "EditProfileDialogFragment");
            }
        });

        edit_surname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // End session and return to the sign in activity
                Bundle bundle = new Bundle();
                bundle.putString("edit_title", "Edit Surname");
                bundle.putString("edit_hint", "New surname");

                DialogFragment dialog_editProfile = new EditProfileDialogFragment();
                dialog_editProfile.setArguments(bundle);
                dialog_editProfile.show(getSupportFragmentManager(), "EditProfileDialogFragment");
            }
        });

        edit_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // End session and return to the sign in activity
                Bundle bundle = new Bundle();
                bundle.putString("edit_title", "Edit Age");
                bundle.putString("edit_hint", "New age");

                DialogFragment dialog_editProfile = new EditProfileDialogFragment();
                dialog_editProfile.setArguments(bundle);
                dialog_editProfile.show(getSupportFragmentManager(), "EditProfileDialogFragment");
            }
        });

        edit_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenderPickerDialogFragment.showAlertDialog(SetAccountActivity.this, 2);
            }
        });


        edit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // End session and return to the sign in activity
                Bundle bundle = new Bundle();
                bundle.putString("edit_title", "Edit E-mail");
                bundle.putString("edit_hint", "New e-mail");

                DialogFragment dialog_editProfile = new EditProfileDialogFragment();
                dialog_editProfile.setArguments(bundle);
                dialog_editProfile.show(getSupportFragmentManager(), "EditProfileDialogFragment");
            }
        });


        edit_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // End session and return to the sign in activity
                Bundle bundle = new Bundle();
                bundle.putString("edit_title", "Edit Password");
                bundle.putString("edit_hint", "New password");

                DialogFragment dialog_editProfile = new EditProfileDialogFragment();
                dialog_editProfile.setArguments(bundle);
                dialog_editProfile.show(getSupportFragmentManager(), "EditProfileDialogFragment");
            }
        });

    }


    public static void display_user_infos(){

        profile_name.setText(MainActivity.user_Name);
        profile_surname.setText(MainActivity.user_Surname);
        //profile_gender.setText(MainActivity.user_Gender);
        profile_age.setText(MainActivity.user_Age);
        profile_email.setText(MainActivity.user_Email);

    }

}
