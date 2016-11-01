package gti350.lab.meetsports;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.appyvet.rangebar.IRangeBarFormatter;
import com.appyvet.rangebar.RangeBar;
import com.github.paolorotolo.appintro.AppIntro;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;

public class CreateEventActivity extends AppIntro {
    private static final String TAG = "CreateEventActivity";

    private static String category;
    private static String type;
    private static Integer year;
    private static Integer month;
    private static Integer day;
    private static Integer hour;
    private static Integer minute;
    private static Double duration;
    private static Integer minParticipants;
    private static Integer maxParticipants;
    private static Integer minAge;
    private static Integer maxAge;
    private static Integer minContribution;
    private static Integer maxContribution;
    private static String level;

    private static TextView date_result, time_result, duration_result, participants_result, age_result, contribution_result, level_result;
    private static Spinner spinner_category, spinner_type, spinner_level;
    private static RangeBar rangebar_duration, rangebar_participants, rangebar_age, rangebar_contribution;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(new FirstFragment());
        addSlide(new SecondFragment());
        addSlide(new ThirdFragment());

        //addSlide(AppIntroFragment.newInstance(title, description, image, backgroundColor));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);
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
            duration = 1.0;
            duration_result.setText(duration + "h");
            rangebar_duration.setOnRangeBarChangeListener(new CreateEventActivity.CustomOnRangeBarChangeListener());
            rangebar_duration.setFormatter(new IRangeBarFormatter() {
                @Override
                public String format(String s) {
                    // Transform the String s here then return s
                    if (s.equals("5"))
                        return "4+";
                    duration_result.setText(duration + "h");
                    return s;
                }
            });

            date_result = (TextView) view.findViewById(R.id.textView_date_result);
            final Calendar c = Calendar.getInstance();
            day = c.get(Calendar.DAY_OF_MONTH);
            month = c.get(Calendar.MONTH);
            year = c.get(Calendar.YEAR);
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
            rangebar_participants.setRangePinsByValue(0, 4);
            rangebar_participants.setOnRangeBarChangeListener(new CreateEventActivity.CustomOnRangeBarChangeListener());
            participants_result = (TextView) view.findViewById(R.id.textView_participants_result);
            minParticipants = 0;
            maxParticipants = 4;
            participants_result.setText(minParticipants + " to " + maxParticipants + " persons");
            rangebar_participants.setFormatter(new IRangeBarFormatter() {
                @Override
                public String format(String s) {
                    // Transform the String s here then return s
                    if (s.equals("11"))
                        return "10+";
                    if (minParticipants == maxParticipants)
                        participants_result.setText(minParticipants + " persons");
                    else
                        participants_result.setText(minParticipants + " to " + maxParticipants + " persons");
                    return s;
                }
            });

            rangebar_age = (RangeBar) view.findViewById(R.id.rangebar_age);
            rangebar_age.setRangePinsByValue(18, 25);
            rangebar_age.setOnRangeBarChangeListener(new CreateEventActivity.CustomOnRangeBarChangeListener());
            age_result = (TextView) view.findViewById(R.id.textView_age_result);
            minAge = 18;
            maxAge = 25;
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
            minContribution = 0;
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
        MapView mMapView;
        private GoogleMap googleMap;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.activity_create_event_fragment_3, container, false);

            mMapView = (MapView) view.findViewById(R.id.mapView);
            mMapView.onCreate(savedInstanceState);

            mMapView.onResume(); // needed to get the map to display immediately

            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }

            mMapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap mMap) {
                    googleMap = mMap;

                    try {
                        // For showing a move to my location button
                        googleMap.setMyLocationEnabled(true);
                    } catch (SecurityException e) {
                        Log.d(TAG, "Could not set location");
                    }

                    // For dropping a marker at a point on the Map
                    LatLng montreal = new LatLng(45.5087, -73.554);
                    googleMap.addMarker(new MarkerOptions().position(montreal).title("Marker Title").snippet("Marker Description"));

                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(montreal).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            });
            // Inflate the layout for this fragment
            return view;
        }

        @Override
        public void onResume() {
            super.onResume();
            mMapView.onResume();
        }

        @Override
        public void onPause() {
            super.onPause();
            mMapView.onPause();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mMapView.onDestroy();
        }

        @Override
        public void onLowMemory() {
            super.onLowMemory();
            mMapView.onLowMemory();
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
        Intent intent = new Intent();
        intent.putExtra("category", category);
        intent.putExtra("type", type);
        intent.putExtra("year", year);
        intent.putExtra("month", month);
        intent.putExtra("day", day);
        intent.putExtra("hour", hour);
        intent.putExtra("minute", minute);
        intent.putExtra("duration", duration);
        intent.putExtra("minParticipants", minParticipants);
        intent.putExtra("maxParticipants", minParticipants);
        intent.putExtra("minAge", minAge);
        intent.putExtra("maxAge", maxAge);
        intent.putExtra("minContribution", minContribution);
        intent.putExtra("maxContribution", maxContribution);
        intent.putExtra("level", level);
        setResult(MainActivity.RESULT_OK, intent);
        finish();
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
                duration = Double.parseDouble(rightPinValue);
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