package gti350.lab.meetsports;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.zip.Inflater;

/**
 * Created by Antoine on 04/11/2016.
 */

public class EventFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "EventFragment";

    private TextView text_event_category;
    private TextView text_event_type;
    private TextView text_event_date;
    private TextView text_event_duration;
    private TextView text_event_distance;
    private TextView text_event_place;
    private TextView text_event_address;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args == null) {
            // Default page (no events chosen)
            View v = inflater.inflate(R.layout.fragment_event, container, false);
            Log.d(TAG, "Bundle arguments are null");
            return v;
        } else {
            // After event is selected in FindEventActivity
            View v = inflater.inflate(R.layout.fragment_event_updated, container, false);
            text_event_category = (TextView) v.findViewById(R.id.event_category_1);
            text_event_type = (TextView) v.findViewById(R.id.event_type_1);
            text_event_date = (TextView) v.findViewById(R.id.event_date_1);
            text_event_duration = (TextView) v.findViewById(R.id.event_duration_1);
            text_event_distance = (TextView) v.findViewById(R.id.event_distance_1);
            text_event_place = (TextView) v.findViewById(R.id.event_place_1);
            text_event_address = (TextView) v.findViewById(R.id.event_address_1);

            String category = getArguments().getString("category");
            String type = getArguments().getString("type");
            String date = getArguments().getString("date");
            String duration = getArguments().getString("duration");
            String distance = getArguments().getString("distance");
            String place = getArguments().getString("place");
            String address = getArguments().getString("address");

            text_event_category.setText(category);
            text_event_type.setText(type);
            text_event_date.setText(date);
            text_event_duration.setText(duration);
            text_event_distance.setText(distance);
            text_event_place.setText(place);
            text_event_address.setText(address);

            CardView card_event1 = (CardView) v.findViewById(R.id.event1);
            card_event1.setOnClickListener(this); // calling onClick() method
            return v;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.event1:
                // do your code
                new MaterialDialog.Builder(getActivity())
                        .title("Event details")
                        .customView(R.layout.event_details, true)
                        .positiveText("Close")
                        .negativeText("Delete")
                        .titleColorRes(R.color.colorAppBackground)
                        .backgroundColorRes(R.color.colorWhite)
                        .show();
                break;

            case R.id.event2:
                // do your code
                break;

            case R.id.event3:
                // do your code
                break;

            default:
                break;
        }
    }
}
