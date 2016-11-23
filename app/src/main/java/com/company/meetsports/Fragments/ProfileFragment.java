package com.company.meetsports.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.meetsports.Activities.MainActivity;
import com.company.meetsports.Manager.SessionManager;
import com.company.meetsports.R;

import static android.app.Activity.RESULT_OK;
import static com.company.meetsports.Activities.MainActivity.PERMISSIONS_REQUEST_CAMERA;
import static com.company.meetsports.Activities.MainActivity.PERMISSIONS_REQUEST_GALLERY;
import static com.company.meetsports.Activities.MainActivity.REQUEST_CAMERA;
import static com.company.meetsports.Activities.MainActivity.REQUEST_GALLERY;
import static com.company.meetsports.Activities.MainActivity.session;
import static com.company.meetsports.Activities.MainActivity.user;


/**
 * Created by Antoine on 03/11/2016.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ProfileFragment";

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

    private ImageView profile_picture;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        profile_picture = (ImageView) v.findViewById(R.id.profile_picture);
        edit_name = (TextView) v.findViewById(R.id.profile_edit_name);
        edit_surname = (TextView) v.findViewById(R.id.profile_edit_surname);
        edit_gender = (TextView) v.findViewById(R.id.profile_edit_gender);
        edit_age = (TextView) v.findViewById(R.id.profile_edit_age);
        edit_email = (TextView) v.findViewById(R.id.profile_edit_email);
        edit_password = (TextView) v.findViewById(R.id.profile_edit_pw);

        profile_name = (TextView) v.findViewById(R.id.profile_value_name);
        profile_surname = (TextView) v.findViewById(R.id.profile_value_surname);
        profile_age = (TextView) v.findViewById(R.id.profile_value_age);
        profile_gender = (TextView) v.findViewById(R.id.profile_value_gender);
        profile_email = (TextView) v.findViewById(R.id.profile_value_email);

        edit_name.setOnClickListener(this);
        edit_surname.setOnClickListener(this);
        edit_gender.setOnClickListener(this);
        edit_age.setOnClickListener(this);
        edit_email.setOnClickListener(this);
        edit_password.setOnClickListener(this);

        profile_picture.setOnClickListener(this);

        display_user_infos();

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_picture:
                showProfilePictureDialog();
                break;
            case R.id.profile_edit_name:
                EditProfileDialogFragment.showAlertDialog(getActivity(), "Edit Name", "New name");
                break;

            case R.id.profile_edit_surname:
                EditProfileDialogFragment.showAlertDialog(getActivity(), "Edit Surname", "New surname");
                break;

            case R.id.profile_edit_gender:
                GenderPickerDialogFragment.showAlertDialog(getActivity(), 2);
                break;

            case R.id.profile_edit_age:
                EditProfileDialogFragment.showAlertDialog(getActivity(), "Edit Age", "New age");
                break;

            case R.id.profile_edit_email:
                EditProfileDialogFragment.showAlertDialog(getActivity(), "Edit E-mail", "New e-mail");
                break;

            case R.id.profile_edit_pw:
                EditProfileDialogFragment.showAlertDialog(getActivity(), "Edit Password", "New password");
                break;

            default:
                break;

        }
    }

    private void showProfilePictureDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Pick Image from")
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            // Check Permissions Now
                            // Callback onRequestPermissionsResult interceptado na Activity MainActivity
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);
                        } else {
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, REQUEST_CAMERA);
                        }
                    }
                })
                .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_GALLERY);
                        } else {
                            Intent loadIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(loadIntent, REQUEST_GALLERY);
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void display_user_infos() {

        //Update user infos
        profile_name.setText(user.get(SessionManager.KEY_NAME));
        profile_surname.setText(user.get(SessionManager.KEY_SURNAME));
        profile_gender.setText(user.get(SessionManager.KEY_GENDER));
        profile_age.setText(user.get(SessionManager.KEY_AGE));
        profile_email.setText(user.get(SessionManager.KEY_EMAIL));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (4): {
                if (resultCode == RESULT_OK) {
                    Log.d(TAG, "From camera");
                    Bitmap mphoto = (Bitmap) data.getExtras().get("data");
                    profile_picture.setImageBitmap(mphoto);
                }
                break;
            }
            case (5): {
                if (resultCode == RESULT_OK) {
                    Log.d(TAG, "From gallery");
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();

                        profile_picture.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    }
                }
                break;
            }
        }
    }
}