package com.company.meetsports.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.appyvet.rangebar.IRangeBarFormatter;
import com.appyvet.rangebar.RangeBar;
import com.company.meetsports.Activities.MainActivity;
import com.company.meetsports.DataProvider.ApiClient;
import com.company.meetsports.DataProvider.ApiInterface;
import com.company.meetsports.Entities.Event;
import com.company.meetsports.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by VMabille on 28/11/2016.
 */

public class CreateEventFragment extends Fragment {

    private static final String TAG = "CreateEventFragment";
    private static final int REQUEST_PLACE_PICKER = 1;

    public static String category = "Running";
    public static String type = "For fun";
    public static Integer year;
    public static Integer month;
    public static Integer day;
    public static Integer hour;
    public static Integer minute;
    public static Double minDuration = 0.2;
    public static Double maxDuration = 1.0;
    public static Integer minParticipants = 1;
    public static Integer maxParticipants = 4;
    public static Integer minAge = 18;
    public static Integer maxAge = 25;
    public static Integer minContribution = 0;
    public static Integer maxContribution = 0;
    public static String level;
    public static String place;
    public static String address;

    public static TextView date_result, time_result, duration_result, participants_result, age_result, contribution_result, level_result, place_result, address_result, textView_category, textView_type, textView_level;
    public static Spinner spinner_category, spinner_type, spinner_level;
    public static RangeBar rangebar_duration, rangebar_participants, rangebar_age, rangebar_contribution;

    public static Button btn_pick_place;
    public static Button btn_create_event;

    public static LinearLayout cardView_placePicker;
    public static TextView textview_place;
    public static TextView textview_address;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_create_event, container, false);

        textview_place = new TextView(getActivity());
        textview_address = new TextView(getActivity());

        Calendar cal = Calendar.getInstance();

        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        cardView_placePicker = (LinearLayout) view.findViewById(R.id.cardview_placePicker);


        spinner_category = (Spinner) view.findViewById(R.id.spinner_category);
        ArrayAdapter<CharSequence> adapter_category = ArrayAdapter.createFromResource(getActivity(),
                R.array.category_array, R.layout.spinner_item);
        adapter_category.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_category.setAdapter(adapter_category);
        spinner_category.setSelection(3, true);
        spinner_category.setOnItemSelectedListener(new CustomOnItemSelectedListener());


        spinner_type = (Spinner) view.findViewById(R.id.spinner_type);
        ArrayAdapter<CharSequence> adapter_type = ArrayAdapter.createFromResource(getActivity(),
                R.array.type_array, R.layout.spinner_item);
        adapter_type.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_type.setAdapter(adapter_type);
        spinner_type.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                type = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });


        date_result = (TextView) view.findViewById(R.id.textView_date_result);
        date_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");

            }
        });
        date_result.setText(day + "/" + month + "/" + year);

        rangebar_duration = (RangeBar) view.findViewById(R.id.rangebar_duration);
        rangebar_duration.setRangePinsByValue(Float.parseFloat(minDuration.toString()), Float.parseFloat(maxDuration.toString()));
        duration_result = (TextView) view.findViewById(R.id.textView_duration_result);
        duration_result.setText(minDuration + "h - " + maxDuration + "h");
        rangebar_duration.setOnRangeBarChangeListener(new CustomOnRangeBarChangeListener());
        rangebar_duration.setFormatter(new IRangeBarFormatter() {
            @Override
            public String format(String s) {
                // Transform the String s here then return s
                if (s.equals("5")) {
                    duration_result.setText(minDuration + "h - more than 4h");
                    return "4+";
                } else {
                    duration_result.setText(minDuration + "h - " + maxDuration + "h");
                    return s;
                }
            }
        });


        time_result = (TextView) view.findViewById(R.id.textView_time_result);
        time_result.setText("At 17h 30min");
        time_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");

            }
        });


        rangebar_participants = (RangeBar) view.findViewById(R.id.rangebar_participants);
        rangebar_participants.setRangePinsByValue(minParticipants, maxParticipants);
        rangebar_participants.setOnRangeBarChangeListener(new CustomOnRangeBarChangeListener());
        participants_result = (TextView) view.findViewById(R.id.textView_participants_result);
        participants_result.setText(minParticipants + " to " + maxParticipants + " persons");

        rangebar_participants.setFormatter(new IRangeBarFormatter() {
            @Override
            public String format(String s) {
                // Transform the String s here then return s
                if (s.equals("11")) {
                    participants_result.setText("More than 10 persons.");
                    return "10+";
                }

                if (minParticipants == maxParticipants)
                    participants_result.setText(minParticipants + " persons");
                /*
                else if (minParticipants == 11 && maxParticipants == 11) {
                    participants_result.setText("More than 10 persons.");
                    participants_result.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditProfileDialogFragment.showAlertDialog(getActivity(), "Enter number of participants", "Number of participants");
                        }
                    });
                } else if (minParticipants != 11 && maxParticipants == 11) {
                    //  participants_result.setText(minParticipants + " to more than 10 persons");
                    participants_result.setText("More than 10 persons.");
                    participants_result.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditProfileDialogFragment.showAlertDialog(getActivity(), "Enter number of participants", "Number of participants");
                        }
                    });
                    */
                else {
                    participants_result.setText(minParticipants + " to " + maxParticipants + " persons");

                }
                return s;
            }
        });

        rangebar_age = (RangeBar) view.findViewById(R.id.rangebar_age);
        rangebar_age.setRangePinsByValue(minAge, maxAge);
        rangebar_age.setOnRangeBarChangeListener(new CustomOnRangeBarChangeListener());
        age_result = (TextView) view.findViewById(R.id.textView_age_result);
        age_result.setText(minAge + " to " + maxAge + " years old");
        rangebar_age.setFormatter(new IRangeBarFormatter() {
            @Override
            public String format(String s) {
                // Transform the String s here then return s
                if (s.equals("71")) {
                    age_result.setText("More than 70 years old");
                    return "70+";
                }
                if (minAge == maxAge)
                    age_result.setText(minAge + " years old");
                else
                    age_result.setText(minAge + " to " + maxAge + " years old");
                return s;
            }
        });

        rangebar_contribution = (RangeBar) view.findViewById(R.id.rangebar_contribution);
        rangebar_contribution.setRangePinsByValue(minContribution, maxContribution);
        rangebar_contribution.setOnRangeBarChangeListener(new CustomOnRangeBarChangeListener());
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
        ArrayAdapter<CharSequence> adapter_level = ArrayAdapter.createFromResource(getActivity(),
                R.array.level_array, R.layout.spinner_item);
        adapter_level.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_level.setAdapter(adapter_level);
        spinner_level.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        btn_pick_place = (Button) view.findViewById(R.id.pickPlace);
        btn_pick_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(getActivity());
                    startActivityForResult(intent, REQUEST_PLACE_PICKER);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        //place_result = (TextView) view.findViewById(R.id.textView_place_result);
        //address_result = (TextView) view.findViewById(R.id.textView_address_result);


        btn_create_event = (Button) view.findViewById(R.id.btn_create_event);
        btn_create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                create_event();

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PLACE_PICKER && resultCode == Activity.RESULT_OK) {


            Place placePicked = PlacePicker.getPlace(getActivity(), data);
            place = placePicked.getName().toString();
            address = placePicked.getAddress().toString();


            ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textview_place.setLayoutParams(lparams);

            textview_place.setPadding(0, 10, 0, 5);
            textview_place.setGravity(Gravity.CENTER);
            textview_place.setTextColor(0xff183c69);
            textview_place.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textview_place.setText(place);

            textview_address.setPadding(5, 5, 5, 15);
            textview_address.setGravity(Gravity.CENTER);
            textview_address.setTextColor(0xff183c69);
            textview_address.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textview_address.setTypeface(null, Typeface.BOLD);

            textview_address.setText(address);


            cardView_placePicker.setBackgroundColor(0x00fffcf2);
            cardView_placePicker.addView(textview_place);
            cardView_placePicker.addView(textview_address);

            //place_result.setText(place);
            //address_result.setText(address);
        }
    }

    // Spinners
    public static class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (parent.getId() == R.id.spinner_category) {
                category = parent.getItemAtPosition(pos).toString();
            } else if (parent.getId() == R.id.spinner_type) {
                type = parent.getItemAtPosition(pos).toString();
            } else if (parent.getId() == R.id.spinner_level) {
                level = parent.getItemAtPosition(pos).toString();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
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


    public void create_event() {

        if (address == null) {
            Snackbar.make(getView(), "Please pick a place first", Snackbar.LENGTH_SHORT).show();
        } else {
            Event newEvent = new Event(null, MainActivity.id_user, category, type, null, minDuration, maxDuration, minParticipants, maxParticipants, minAge, maxAge, minContribution, maxContribution, level, place, address);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            final Call<ResponseBody> callEvent = apiService.addEvent(newEvent);
            callEvent.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    int statusCode = response.code();
                    Log.d(TAG, "Status code: " + String.valueOf(statusCode));

                    MainActivity.Button_My_Events.performClick();

                    Snackbar snackbar = Snackbar
                            .make(getView(), "Event created successfully", Snackbar.LENGTH_LONG)
                            .setAction("CANCEL", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                                    Call<List<Event>> call = apiService.getEventsCreatedByUserId(MainActivity.id_user);
                                    call.enqueue(new Callback<List<Event>>() {
                                        @Override
                                        public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                                            int statusCode = response.code();
                                            Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                                            List<Event> myEvents = response.body();
                                            // Get the id of the last created event
                                            Integer id_event = myEvents.get(0).getId_event();
                                            Call<ResponseBody> callDelete = apiService.deleteAttendance(id_event, MainActivity.id_user);
                                            callDelete.enqueue(new Callback<ResponseBody>() {
                                                @Override
                                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                    int statusCode = response.code();
                                                    Log.d(TAG, "Status code: " + String.valueOf(statusCode));

                                                    MainActivity.Button_Create_Event.performClick();

                                                    Snackbar snackbar = Snackbar.make(getView(), "Event has been deleted!", Snackbar.LENGTH_SHORT);
                                                    snackbar.show();
                                                }

                                                @Override
                                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                    // Log error here since request failed
                                                    Log.e(TAG, t.toString());
                                                    Snackbar snackbar = Snackbar.make(getView(), "Please check your internet connexion!", Snackbar.LENGTH_SHORT);
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
    }

}

