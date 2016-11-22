package com.company.meetsports.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import com.company.meetsports.Entities.Event_OLD;
import com.company.meetsports.R;

/**
 * Created by Antoine on 02/10/2016.
 * <p>
 * A big thanks to this tutorial:
 * https://androidhub.intel.com/en/posts/nglauber/Android_Search.html
 */

public class FindEventActivity extends AppCompatActivity implements PlaceSelectionListener, View.OnClickListener {
    private static final String TAG = "FindEventActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_event);

        // Retrieve the PlaceAutocompleteFragment.
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Register a listener to receive callbacks when a place has been selected or an error has
        // occurred.
        autocompleteFragment.setOnPlaceSelectedListener(this);

        CardView card_event1 = (CardView) findViewById(R.id.event1);
        card_event1.setOnClickListener(this); // calling onClick() method
        CardView card_event2 = (CardView) findViewById(R.id.event2);
        card_event2.setOnClickListener(this);
        CardView card_event3 = (CardView) findViewById(R.id.event3);
        card_event3.setOnClickListener(this);
    }

    /**
     * Callback invoked when a place has been selected from the PlaceAutocompleteFragment.
     */
    @Override
    public void onPlaceSelected(Place place) {
        Log.i(TAG, "Place Selected: " + place.getName());

        // Saving results
        Intent intent = new Intent();
        intent.putExtra("place", place.getName());
        intent.putExtra("address", place.getAddress());
        intent.putExtra("id", place.getId());
        if (place.getPhoneNumber() != null)
            intent.putExtra("phone", place.getPhoneNumber());
        if (place.getWebsiteUri() != null)
            intent.putExtra("website", place.getWebsiteUri().toString());
        setResult(MainActivity.RESULT_OK, intent);
    }

    /**
     * Callback invoked when PlaceAutocompleteFragment encounters an error.
     */
    @Override
    public void onError(Status status) {
        Log.e(TAG, "onError: Status = " + status.toString());

        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.event1:
                // do your code
                new MaterialDialog.Builder(this)
                        .title("Event_OLD details")
                        .customView(R.layout.event_details, true)
                        .positiveText("I am going")
                        .negativeText("Close")
                        .titleColor(-1)
                        .positiveColor(-1)
                        .negativeColor(-1)
                        .backgroundColorRes(R.color.colorAppBackground)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                storeEvent(1);
                            }
                        }).show();
                break;

            case R.id.event2:
                // do your code
                new MaterialDialog.Builder(this)
                        .title("Event_OLD details")
                        .customView(R.layout.event_details, true)
                        .positiveText("I am going")
                        .negativeText("Close")
                        .titleColor(-1)
                        .positiveColor(-1)
                        .negativeColor(-1)
                        .backgroundColorRes(R.color.colorAppBackground)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                storeEvent(2);
                            }
                        }).show();
                break;

            case R.id.event3:
                // do your code
                new MaterialDialog.Builder(this)
                        .title("Event_OLD details")
                        .customView(R.layout.event_details, true)
                        .positiveText("I am going")
                        .negativeText("Close")
                        .titleColor(-1)
                        .positiveColor(-1)
                        .negativeColor(-1)
                        .backgroundColorRes(R.color.colorAppBackground)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                storeEvent(3);
                            }
                        }).show();
                break;

            default:
                break;
        }
    }

    private void storeEvent(int id) {

        boolean existing_event = false;
        TextView category = (TextView) findViewById(getResources().getIdentifier("event_category_" + id, "id", getPackageName()));
        TextView type = (TextView) findViewById(getResources().getIdentifier("event_type_" + id, "id", getPackageName()));
        TextView date = (TextView) findViewById(getResources().getIdentifier("event_date_" + id, "id", getPackageName()));
        TextView duration = (TextView) findViewById(getResources().getIdentifier("event_duration_" + id, "id", getPackageName()));
        TextView distance = (TextView) findViewById(getResources().getIdentifier("event_distance_" + id, "id", getPackageName()));

        /*TextView place = (TextView) findViewById(getResources().getIdentifier("event_place_" + id, "id", getPackageName()));
        TextView address = (TextView) findViewById(getResources().getIdentifier("event_address_" + id, "id", getPackageName()));*/

        Intent intent = new Intent();

        // Check if event is already selected
        for(Event_OLD eventOLD : MainActivity.eventOLDs){
            if (eventOLD.getId() == id){
                existing_event = true;
            }
        }

        if(!existing_event) {
            Event_OLD eventOLD = new Event_OLD(id,
                    category.getText().toString(),
                    type.getText().toString(),
                    date.getText().toString(),
                    duration.getText().toString(),
                    distance.getText().toString(),
                    "Chalet du Mont-Royal",
                    "1196 Camillien-Houde Road, Montreal, Québec H3H 1A1");
            MainActivity.eventOLDs.add(eventOLD);
            setResult(MainActivity.RESULT_OK, intent);
            finish();
        } else {
            setResult(MainActivity.RESULT_CANCELED, intent);
            finish();
        }
    }
}
