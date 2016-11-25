package com.company.meetsports.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.appyvet.rangebar.IRangeBarFormatter;
import com.appyvet.rangebar.RangeBar;
import com.company.meetsports.Fragments.EditProfileDialogFragment;
import com.company.meetsports.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.Calendar;


public class CreateEventActivity extends AppIntro {
    private static final String TAG = "CreateEventActivity";

    private static int PLACE_PICKER_REQUEST = 1;

    private static String category;
    private static String type;
    private static Integer year;
    private static Integer month;
    private static Integer day;
    private static Integer hour;
    private static Integer minute;
    private static Double minDuration;
    private static Double maxDuration;
    private static Integer minParticipants;
    private static Integer maxParticipants;
    private static Integer minAge;
    private static Integer maxAge;
    private static Integer minContribution;
    private static Integer maxContribution;
    private static String level;
    private static String place;
    private static String address;

    private static TextView date_result, time_result, duration_result, participants_result, age_result, contribution_result, level_result, place_result, address_result;
    private static Spinner spinner_category, spinner_type, spinner_level;
    private static RangeBar rangebar_duration, rangebar_participants, rangebar_age, rangebar_contribution;

    private static RelativeLayout relativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate...");

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(new FirstFragment());
        addSlide(new SecondFragment());
        addSlide(new ThirdFragment());

        //addSlide(AppIntroFragment.newInstance(title, description, image, backgroundColor));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(ContextCompat.getColor(this, R.color.colorDark));
        setSeparatorColor(ContextCompat.getColor(this, R.color.colorPrimary));

        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);

        // Initialize inputs
        minDuration = 1.0;
        maxDuration = 2.0;
        final Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        minParticipants = 1;
        maxParticipants = 4;
        minAge = 18;
        maxAge = 25;
        minContribution = 0;
        maxContribution = 0;
        level = "Any";
        place = null;
        address = null;
    }

    public static class FirstFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.activity_create_event_fragment_1, container, false);
            spinner_category = (Spinner) view.findViewById(R.id.spinner_category);
            spinner_category.setOnItemSelectedListener(new CreateEventActivity.CustomOnItemSelectedListener());

            spinner_type = (Spinner) view.findViewById(R.id.spinner_type);
            spinner_type.setOnItemSelectedListener(new CreateEventActivity.CustomOnItemSelectedListener());

            rangebar_duration = (RangeBar) view.findViewById(R.id.rangebar_duration);
            rangebar_duration.setRangePinsByValue(0, 1);
            duration_result = (TextView) view.findViewById(R.id.textView_duration_result);
            duration_result.setText(minDuration + "h - " + maxDuration + "h");
            rangebar_duration.setOnRangeBarChangeListener(new CreateEventActivity.CustomOnRangeBarChangeListener());
            rangebar_duration.setFormatter(new IRangeBarFormatter() {
                @Override
                public String format(String s) {
                    // Transform the String s here then return s
                    if (s.equals("5"))
                        return "4+";
                    duration_result.setText(minDuration + "h - " + maxDuration + "h");
                    return s;
                }
            });

            date_result = (TextView) view.findViewById(R.id.textView_date_result);

            date_result.setText(day + "/" + month + "/" + year);
            time_result = (TextView) view.findViewById(R.id.textView_time_result);
            time_result.setText("At 17h 30min");
            // Inflate the layout for this fragment
            return view;
        }
    }

    public static class SecondFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.activity_create_event_fragment_2, container, false);
            rangebar_participants = (RangeBar) view.findViewById(R.id.rangebar_participants);
            rangebar_participants.setRangePinsByValue(1, 4);
            rangebar_participants.setOnRangeBarChangeListener(new CreateEventActivity.CustomOnRangeBarChangeListener());
            participants_result = (TextView) view.findViewById(R.id.textView_participants_result);
            participants_result.setText(minParticipants + " to " + maxParticipants + " persons");
            rangebar_participants.setFormatter(new IRangeBarFormatter() {
                @Override
                public String format(String s) {
                    // Transform the String s here then return s
                    if (s.equals("11"))
                        return "10+";

                    if (minParticipants == maxParticipants)
                        participants_result.setText(minParticipants + " persons");
                    else if (minParticipants == 11 && maxParticipants == 11) {
                        participants_result.setText("More than 10 persons. Clic to enter exact number");
                        participants_result.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EditProfileDialogFragment.showAlertDialog(getActivity(), "Enter number of participants", "Number of participants");
                            }
                        });
                    } else if (minParticipants != 11 && maxParticipants == 11) {
                        //  participants_result.setText(minParticipants + " to more than 10 persons");
                        participants_result.setText("More than 10 persons. Clic to enter exact number");
                        participants_result.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EditProfileDialogFragment.showAlertDialog(getActivity(), "Enter number of participants", "Number of participants");
                            }
                        });
                    } else {
                        participants_result.setText(minParticipants + " to " + maxParticipants + " persons");

                    }
                    return s;
                }
            });

            rangebar_age = (RangeBar) view.findViewById(R.id.rangebar_age);
            rangebar_age.setRangePinsByValue(18, 25);
            rangebar_age.setOnRangeBarChangeListener(new CreateEventActivity.CustomOnRangeBarChangeListener());
            age_result = (TextView) view.findViewById(R.id.textView_age_result);
            age_result.setText(minAge + " to " + maxAge + " years old");
            rangebar_age.setFormatter(new IRangeBarFormatter() {
                @Override
                public String format(String s) {
                    // Transform the String s here then return s
                    if (s.equals("36"))
                        return "35+";
                    if (minAge == maxAge)
                        age_result.setText(minAge + " years old");
                    else
                        age_result.setText(minAge + " to " + maxAge + " years old");
                    return s;
                }
            });

            rangebar_contribution = (RangeBar) view.findViewById(R.id.rangebar_contribution);
            rangebar_contribution.setRangePinsByValue(0, 0);
            rangebar_contribution.setOnRangeBarChangeListener(new CreateEventActivity.CustomOnRangeBarChangeListener());
            contribution_result = (TextView) view.findViewById(R.id.textView_contribution_result);
            contribution_result.setText(minContribution + " $");
            rangebar_contribution.setFormatter(new IRangeBarFormatter() {
                @Override
                public String format(String s) {
                    // Transform the String s here then return s
                    if (s.equals("21"))
                        return "20+";
                    if (minContribution == maxContribution)
                        contribution_result.setText(minContribution + " $");
                    else
                        contribution_result.setText(minContribution + " to " + maxContribution + " $");
                    return s;
                }
            });

            spinner_level = (Spinner) view.findViewById(R.id.spinner_level);
            spinner_level.setOnItemSelectedListener(new CreateEventActivity.CustomOnItemSelectedListener());
            // Inflate the layout for this fragment
            return view;
        }
    }

    public static class ThirdFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.activity_create_event_fragment_3, container, false);

            // For snackbar
            relativeLayout = (RelativeLayout) view.findViewById(R.id.activity_create_event);

            place_result = (TextView) view.findViewById(R.id.textView_place_result);
            place_result.setText(place);
            address_result = (TextView) view.findViewById(R.id.textView_address_result);
            address_result.setText(address);

            // Inflate the layout for this fragment
            return view;
        }
    }


    public void onPickPlace(View view) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            Intent intent = builder.build(this);
            startActivityForResult(intent, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
            Place placePicked = PlacePicker.getPlace(this, data);
            place = placePicked.getName().toString();
            address = placePicked.getAddress().toString();
            place_result.setText(place);
            address_result.setText(address);
        }
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        if (address != null) {
            Intent intent = new Intent();
            intent.putExtra("category", category);
            intent.putExtra("type", type);
            intent.putExtra("year", year);
            intent.putExtra("month", month);
            intent.putExtra("day", day);
            intent.putExtra("hour", hour);
            intent.putExtra("minute", minute);
            intent.putExtra("minDuration", minDuration);
            intent.putExtra("maxDuration", maxDuration);
            intent.putExtra("minParticipants", minParticipants);
            intent.putExtra("maxParticipants", minParticipants);
            intent.putExtra("minAge", minAge);
            intent.putExtra("maxAge", maxAge);
            intent.putExtra("minContribution", minContribution);
            intent.putExtra("maxContribution", maxContribution);
            intent.putExtra("level", level);
            intent.putExtra("place", place);
            intent.putExtra("address", address);
            setResult(CreateEventActivity.RESULT_OK, intent);
            finish();
        } else {
            // Show message on CreateEventActivity finished
            Snackbar.make(relativeLayout, "Please pick a place first", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new CreateEventActivity.DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new CreateEventActivity.TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(getApplicationContext(), TAG + " paused", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(getApplicationContext(), TAG + " resumed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(getApplicationContext(), TAG + " stopped", Toast.LENGTH_SHORT).show();
    }

    // Spinners
    public static class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            //Toast.makeText(parent.getContext(), "OnItemSelectedListener : " + parent.getId().toString(), Toast.LENGTH_SHORT).show();
            if (parent.getId() == R.id.spinner_category)
                category = parent.getItemAtPosition(pos).toString();
            if (parent.getId() == R.id.spinner_type)
                type = parent.getItemAtPosition(pos).toString();
            if (parent.getId() == R.id.spinner_level)
                level = parent.getItemAtPosition(pos).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

    // Rangebars
    public static class CustomOnRangeBarChangeListener implements RangeBar.OnRangeBarChangeListener {
        @Override
        public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
            if (rangeBar.equals(rangebar_duration)) {
                minDuration = Double.parseDouble(leftPinValue);
                maxDuration = Double.parseDouble(rightPinValue);
            }
            if (rangeBar.equals(rangebar_participants)) {
                minParticipants = Integer.parseInt(leftPinValue);
                maxParticipants = Integer.parseInt(rightPinValue);
            }
            if (rangeBar.equals(rangebar_age)) {
                minAge = Integer.parseInt(leftPinValue);
                maxAge = Integer.parseInt(rightPinValue);
            }
            if (rangeBar.equals(rangebar_contribution)) {
                minContribution = Integer.parseInt(leftPinValue);
                maxContribution = Integer.parseInt(rightPinValue);
            }
        }
    }

    // Pickers
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int yearPicker, int monthPicker, int dayPicker) {
            // Do something with the date chosen by the user
            year = yearPicker;
            month = monthPicker;
            day = dayPicker;
            updateDateTextView();
        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDayPicker, int minutePicker) {
            // Do something with the time chosen by the user
            hour = hourOfDayPicker;
            minute = minutePicker;
            updateTimeTextView();
        }
    }

    private static void updateDateTextView() {
        date_result.setText(day + "/" + month + "/" + year);
    }

    private static void updateTimeTextView() {
        date_result.setText(day + "/" + month + "/" + year);
        time_result.setText("At " + hour + "h " + minute + "min");
    }
}