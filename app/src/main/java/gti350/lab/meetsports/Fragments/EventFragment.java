package gti350.lab.meetsports.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.TextView;



import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.w3c.dom.Text;

import gti350.lab.meetsports.Activities.MainActivity;
import gti350.lab.meetsports.Activities.SignInActivity;
import gti350.lab.meetsports.R;

/**
 * Created by Antoine on 04/11/2016.
 */

public class EventFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "EventFragment";

    private Integer cardSoftBackground = 0xfffcf2;

    private TextView text_event_category_1;
    private TextView text_event_type_1;
    private TextView text_event_date_1;
    private TextView text_event_duration_1;
    private TextView text_event_distance_1;
    private TextView text_event_place_1;
    private TextView text_event_address_1;
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

    private TextView event_details_category;
    private TextView event_details_type;
    private TextView event_details_date;
    private TextView event_details_duration;
    private TextView event_details_place;
    private TextView event_details_address;

    public static CardView card_event1;
    public static CardView card_event2;
    public static CardView card_event3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (MainActivity.Events.size() == 0){
            View v = inflater.inflate(R.layout.fragment_event, container, false);
            Log.d(TAG, "Bundle arguments are null");
            return v;
        } else {

            View v = inflater.inflate(R.layout.fragment_event_updated, container, false);
            text_event_category_1 = (TextView) v.findViewById(R.id.event_category_1);
            text_event_type_1 = (TextView) v.findViewById(R.id.event_type_1);
            text_event_date_1 = (TextView) v.findViewById(R.id.event_date_1);
            text_event_duration_1 = (TextView) v.findViewById(R.id.event_duration_1);
            text_event_distance_1 = (TextView) v.findViewById(R.id.event_distance_1);
            text_event_place_1 = (TextView) v.findViewById(R.id.event_place_1);
            text_event_address_1 = (TextView) v.findViewById(R.id.event_address_1);

            text_event_category_1.setText(MainActivity.Events.get(0).getCategory());
            text_event_type_1.setText(MainActivity.Events.get(0).getType());
            text_event_date_1.setText(MainActivity.Events.get(0).getDate());
            text_event_duration_1.setText(MainActivity.Events.get(0).getDuration());
            text_event_distance_1.setText(MainActivity.Events.get(0).getDistance());
            text_event_place_1.setText(MainActivity.Events.get(0).getPlace());
            text_event_address_1.setText(MainActivity.Events.get(0).getAddress());

            card_event1 = (CardView) v.findViewById(R.id.event1);
            card_event1.setCardBackgroundColor(0xfffffcf2);
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


                card_event2 = (CardView) v.findViewById(R.id.event2);
                card_event2.setCardBackgroundColor(0xfffffcf2);
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

                card_event3 = (CardView) v.findViewById(R.id.event3);
                card_event3.setCardBackgroundColor(0xfffffcf2);
                card_event3.setClickable(true);
                card_event3.setOnClickListener(this); // calling onClick() method
            }


            return v;
        }
    }

    @Override
    public void onClick(View v) {

        event_details_category = (TextView) getActivity().findViewById(R.id.category);
        event_details_type  = (TextView) getActivity().findViewById(R.id.type);
        event_details_date = (TextView) getActivity().findViewById(R.id.date);
        event_details_duration = (TextView) getActivity().findViewById(R.id.duration);
        event_details_place = (TextView) getActivity().findViewById(R.id.event_place);
        event_details_address = (TextView) getActivity().findViewById(R.id.event_address);

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
        builder.title("Event details")
                .positiveText("Close")
                .negativeText("Delete")
                .customView(R.layout.event_details, true)
                .titleColor(-1)
                .positiveColor(-1)
                .negativeColor(-1)
                .backgroundColorRes(R.color.colorAppBackground)
                .show();

        switch (v.getId()) {


            case R.id.event1:
                /*
                event_details_category.setText(MainActivity.Events.get(0).getCategory());
                event_details_type.setText(MainActivity.Events.get(0).getType());
                event_details_date.setText(MainActivity.Events.get(0).getDate());
                event_details_duration.setText(MainActivity.Events.get(0).getDuration());
                event_details_place.setText(MainActivity.Events.get(0).getPlace());
                event_details_address.setText(MainActivity.Events.get(0).getAddress());
                */
                builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    ViewGroup layout = (LinearLayout) getActivity().findViewById(R.id.layout_events);
                    View card = (CardView) getActivity().findViewById(R.id.event1);
                    layout.removeView(card);
                    MainActivity.Events.remove(0);
                    dialog.dismiss();
                }});

                break;

            case R.id.event2:
                /*
                event_details_category.setText(MainActivity.Events.get(1).getCategory());
                event_details_type.setText(MainActivity.Events.get(1).getType());
                event_details_date.setText(MainActivity.Events.get(1).getDate());
                event_details_duration.setText(MainActivity.Events.get(1).getDuration());
                event_details_place.setText(MainActivity.Events.get(1).getPlace());
                event_details_address.setText(MainActivity.Events.get(1).getAddress());
                */
                builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ViewGroup layout = (LinearLayout) getActivity().findViewById(R.id.layout_events);
                        View card = (CardView) getActivity().findViewById(R.id.event2);
                        layout.removeView(card);
                        MainActivity.Events.remove(1);
                        dialog.dismiss();
                    }});

                break;

            case R.id.event3:
                /*
                event_details_category.setText(MainActivity.Events.get(2).getCategory());
                event_details_type.setText(MainActivity.Events.get(2).getType());
                event_details_date.setText(MainActivity.Events.get(2).getDate());
                event_details_duration.setText(MainActivity.Events.get(2).getDuration());
                event_details_place.setText(MainActivity.Events.get(2).getPlace());
                event_details_address.setText(MainActivity.Events.get(2).getAddress());
                */
                builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ViewGroup layout = (LinearLayout) getActivity().findViewById(R.id.layout_events);
                        View card = (CardView) getActivity().findViewById(R.id.event3);
                        layout.removeView(card);
                        MainActivity.Events.remove(2);

                    }});
                break;

            default:
                break;
        }

        if (MainActivity.Events.size() == 0) {
           // View no_events = LayoutInflater.inflate(R.layout.fragment_event, Container , false);
            //Log.d(TAG, "Bundle arguments are null");
        }
    }
/*
    // TO COMPLETE
    public void create_card_view(int id){



        ViewGroup card_event = new CardView(getActivity());
        card_event.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                2.0f));

        TextView text_event_category = new TextView(getActivity());
        TextView text_event_type = new TextView(getActivity());
        TextView text_event_date = new TextView(getActivity());
        TextView text_event_duration = new TextView(getActivity());
        TextView text_event_distance = new TextView(getActivity());
        TextView text_event_place = new TextView(getActivity());
        TextView text_event_address = new TextView(getActivity());

        text_event_category.setText(MainActivity.Events.get(id).getCategory());
        text_event_category.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                2.0f));
        text_event_category.setGravity(Gravity.CENTER_HORIZONTAL );

        text_event_duration.setText(MainActivity.Events.get(id).getDuration());

        text_event_type.setText(MainActivity.Events.get(id).getType());
        text_event_category.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                2.0f));


        text_event_date.setText(MainActivity.Events.get(id).getDate());

        text_event_distance.setText(MainActivity.Events.get(id).getDistance());
        text_event_place.setText(MainActivity.Events.get(id).getPlace());
        text_event_address.setText(MainActivity.Events.get(id).getAddress());

        card_event.addView(text_event_category);
        card_event.addView(text_event_type);
        card_event.addView(text_event_date);
        card_event.addView(text_event_duration);
        card_event.addView(text_event_distance);
        card_event.addView(text_event_place);
        card_event.addView(text_event_address);

    }
*/
}
