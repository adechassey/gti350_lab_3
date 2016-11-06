package gti350.lab.meetsports;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import gti350.lab.meetsports.EditProfileDialogFragment;
import gti350.lab.meetsports.GenderPickerDialogFragment;


/**
 * Created by Antoine on 03/11/2016.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView edit_name;
    private TextView edit_surname;
    private TextView edit_gender;
    private TextView edit_age;
    private TextView edit_email;
    private TextView edit_password;

    public static TextView profile_name;
    public static TextView profile_surname;
    public static TextView profile_gender;
    public static TextView profile_age;
    public static TextView profile_email;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile,container,false);

        edit_name = (TextView) v.findViewById(R.id.profile_edit_name);
        edit_surname = (TextView) v.findViewById(R.id.profile_edit_surname);
        edit_gender = (TextView) v.findViewById(R.id.profile_edit_gender);
        edit_age = (TextView) v.findViewById(R.id.profile_edit_age);
        edit_email = (TextView) v.findViewById(R.id.profile_edit_email);
        edit_password = (TextView) v.findViewById(R.id.profile_edit_pw);


        profile_name = (TextView)  v.findViewById(R.id.profile_value_name);
        profile_surname = (TextView)  v.findViewById(R.id.profile_value_surname);
        profile_age = (TextView)  v.findViewById(R.id.profile_value_age);
        profile_gender = (TextView)  v.findViewById(R.id.profile_value_gender);
        profile_email = (TextView)  v.findViewById(R.id.profile_value_email);


        edit_name.setOnClickListener(this);
        edit_surname.setOnClickListener(this);
        edit_gender.setOnClickListener(this);
        edit_age.setOnClickListener(this);
        edit_email.setOnClickListener(this);
        edit_password.setOnClickListener(this);

        display_user_infos();

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_edit_name:
                // do your code
                EditProfileDialogFragment.showAlertDialog(getActivity(),"Edit Name", "New name");
                break;

            case R.id.profile_edit_surname:
                // do your code
                EditProfileDialogFragment.showAlertDialog(getActivity(),"Edit Surname", "New surname");
                break;

            case R.id.profile_edit_gender:
                // do your code
                GenderPickerDialogFragment.showAlertDialog(getActivity(), 2);
                break;

            case R.id.profile_edit_age:
                // do your code
                EditProfileDialogFragment.showAlertDialog(getActivity(),"Edit Age", "New age");
                break;

            case R.id.profile_edit_email:
                // do your code
                EditProfileDialogFragment.showAlertDialog(getActivity(),"Edit E-mail", "New e-mail");
                break;

            case R.id.profile_edit_pw:
                // do your code
                EditProfileDialogFragment.showAlertDialog(getActivity(),"Edit Password", "New password");
                break;

            default:
                break;

        }
    }

    public static void display_user_infos(){

        profile_name.setText(MainActivity.user_Name);
        profile_surname.setText(MainActivity.user_Surname);
        profile_age.setText(MainActivity.user_Age);
        profile_email.setText(MainActivity.user_Email);

    }
}