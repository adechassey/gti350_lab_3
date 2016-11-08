package gti350.lab.meetsports.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import gti350.lab.meetsports.Activities.MainActivity;
import gti350.lab.meetsports.R;

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
    private TextView text_event_category_2;
    private TextView text_event_type_2;
    private TextView text_event_date_2;
    private TextView text_event_duration_2;
    private TextView text_event_distance_2;
    private TextView text_event_place_2;
    private TextView text_event_address_2;
    private TextView text_event_category_3;
    private TextView text_event_type_3;
    private TextView text_event_date_3;
    private TextView text_event_duration_3;
    private TextView text_event_distance_3;
    private TextView text_event_place_3;
    private TextView text_event_address_3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle args = getArguments();
        if (MainActivity.Events.size() == 0){
            View v = inflater.inflate(R.layout.fragment_event, container, false);
            Log.d(TAG, "Bundle arguments are null");
            return v;
        } else {

            View v = inflater.inflate(R.layout.fragment_event_updated, container, false);
            text_event_category = (TextView) v.findViewById(R.id.event_category_1);
            text_event_type = (TextView) v.findViewById(R.id.event_type_1);
            text_event_date = (TextView) v.findViewById(R.id.event_date_1);
            text_event_duration = (TextView) v.findViewById(R.id.event_duration_1);
            text_event_distance = (TextView) v.findViewById(R.id.event_distance_1);
            text_event_place = (TextView) v.findViewById(R.id.event_place_1);
            text_event_address = (TextView) v.findViewById(R.id.event_address_1);

            text_event_category.setText(MainActivity.Events.get(0).getCategory());
            text_event_type.setText(MainActivity.Events.get(0).getType());
            text_event_date.setText(MainActivity.Events.get(0).getDate());
            text_event_duration.setText(MainActivity.Events.get(0).getDuration());
            text_event_distance.setText(MainActivity.Events.get(0).getDistance());
            text_event_place.setText(MainActivity.Events.get(0).getPlace());
            text_event_address.setText(MainActivity.Events.get(0).getAddress());

            CardView card_event1 = (CardView) v.findViewById(R.id.event1);
            card_event1.setOnClickListener(this); // calling onClick() method

            if(MainActivity.Events.size() > 1){



                text_event_category_2 = (TextView) v.findViewById(R.id.event_category_2);
                text_event_type_2 = (TextView) v.findViewById(R.id.event_type_2);
                text_event_date_2 = (TextView) v.findViewById(R.id.event_date_2);
                text_event_duration_2 = (TextView) v.findViewById(R.id.event_duration_2);
                text_event_distance_2 = (TextView) v.findViewById(R.id.event_distance_2);
                text_event_place_2 = (TextView) v.findViewById(R.id.event_place_2);
                text_event_address_2 = (TextView) v.findViewById(R.id.event_address_2);

                text_event_category_2.setText(MainActivity.Events.get(1).getCategory());
                text_event_type_2.setText(MainActivity.Events.get(1).getType());
                text_event_date_2.setText(MainActivity.Events.get(1).getDate());
                text_event_duration_2.setText(MainActivity.Events.get(1).getDuration());
                text_event_distance_2.setText(MainActivity.Events.get(1).getDistance());
                text_event_place_2.setText(MainActivity.Events.get(1).getPlace());
                text_event_address_2.setText(MainActivity.Events.get(1).getAddress());

                CardView card_event2 = (CardView) v.findViewById(R.id.event2);
                card_event2.setCardBackgroundColor(getResources().getColor(R.color.colorBackgroundSoft));
                card_event2.setClickable(true);
                card_event2.setOnClickListener(this); // calling onClick() method
            }

            if(MainActivity.Events.size() > 2){


                text_event_category_3 = (TextView) v.findViewById(R.id.event_category_3);
                text_event_type_3 = (TextView) v.findViewById(R.id.event_type_3);
                text_event_date_3 = (TextView) v.findViewById(R.id.event_date_3);
                text_event_duration_3 = (TextView) v.findViewById(R.id.event_duration_3);
                text_event_distance_3 = (TextView) v.findViewById(R.id.event_distance_3);
                text_event_place_3 = (TextView) v.findViewById(R.id.event_place_3);
                text_event_address_3 = (TextView) v.findViewById(R.id.event_address_3);

                text_event_category_3.setText(MainActivity.Events.get(2).getCategory());
                text_event_type_3.setText(MainActivity.Events.get(2).getType());
                text_event_date_3.setText(MainActivity.Events.get(2).getDate());
                text_event_duration_3.setText(MainActivity.Events.get(2).getDuration());
                text_event_distance_3.setText(MainActivity.Events.get(2).getDistance());
                text_event_place_3.setText(MainActivity.Events.get(2).getPlace());
                text_event_address_3.setText(MainActivity.Events.get(2).getAddress());

                CardView card_event3 = (CardView) v.findViewById(R.id.event3);
                card_event3.setCardBackgroundColor(getResources().getColor(R.color.colorBackgroundSoft));
                card_event3.setClickable(true);
                card_event3.setOnClickListener(this); // calling onClick() method
            }


            return v;
        }
        /*
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

        }*/
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
                        .titleColor(-1)
                        .positiveColor(-1)
                        .negativeColor(-1)
                        .backgroundColorRes(R.color.colorAppBackground)
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
