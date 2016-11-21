package com.company.meetsports.Fragments;


import android.app.Fragment;
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
import android.widget.TextView;

import android.util.TypedValue;
import android.graphics.Typeface;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.company.meetsports.Activities.MainActivity;
import com.company.meetsports.R;


public class EventFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "EventFragment";

    private Integer cardSoftBackground = 0xfffffcf2;
    public final Integer colorDark = 0xff1e5aa6;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (MainActivity.Events.size() == 0){
            View v = inflater.inflate(R.layout.fragment_event, container, false);
            Log.d(TAG, "Bundle arguments are null");
            return v;
        } else {



            View v = inflater.inflate(R.layout.fragment_event_updated, container, false);
            LinearLayout event_layout = (LinearLayout) v.findViewById(R.id.layout_events);

            for(int i=0; i < MainActivity.Events.size(); i++){
                View card_event = create_card_view(i) ;
                card_event.setOnClickListener(this); // calling onClick() method
                event_layout.addView(card_event);
            }

            return v;
        }
    }

    @Override
    public void onClick(View v) {

        final int Id = v.getId();

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
        builder.title("Event details")
                .positiveText("Close")
                .negativeText("Delete")
                .customView(R.layout.event_details, true)
                .titleColor(-1)
                .positiveColor(-1)
                .negativeColor(-1)
                .backgroundColorRes(R.color.colorAppBackground)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                ViewGroup layout = (LinearLayout) getActivity().findViewById(R.id.layout_events);
                View card = (CardView) getActivity().findViewById(Id);
                layout.removeView(card);
                MainActivity.Events.remove(Id-1000);
                dialog.dismiss();
            }});
        builder.show();


    }


    public View create_card_view(int id){

        // 2dp margin
        int margin = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                2.0f,
                getActivity().getResources().getDisplayMetrics()
        );

        CardView card_event = new CardView(getActivity());
        LinearLayout layout_evt_details = new LinearLayout(getActivity());

        LinearLayout details_title = new LinearLayout(getActivity());
        LinearLayout details_container = new LinearLayout(getActivity());

        LinearLayout details_col_2 = new LinearLayout(getActivity());
        LinearLayout details_col_3 = new LinearLayout(getActivity());
        LinearLayout details_col_1 = new LinearLayout(getActivity());

        TextView text_event_category = new TextView(getActivity());
        TextView text_event_type = new TextView(getActivity());
        TextView text_event_date = new TextView(getActivity());
        TextView text_event_duration = new TextView(getActivity());
        TextView text_event_distance = new TextView(getActivity());
        TextView text_event_place = new TextView(getActivity());
        TextView text_event_address = new TextView(getActivity());

        // layout settings for the CardView
        LinearLayout.LayoutParams params_card = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                2.0f);
        params_card.setMargins(0, margin*3, 0, margin*3);
        card_event.setLayoutParams(params_card);
        card_event.setId(1000+id);
        card_event.setBackgroundColor(cardSoftBackground);

        layout_evt_details.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                2.0f));
        layout_evt_details.setOrientation(LinearLayout.VERTICAL);
        layout_evt_details.setGravity(Gravity.CENTER_HORIZONTAL);



        //  Title :  Category
        LinearLayout.LayoutParams params_details_title = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                2.0f);
        params_details_title.setMargins(0, margin, 0, margin*5);
        details_title.setLayoutParams(params_details_title);
        details_title.setOrientation(LinearLayout.HORIZONTAL);

        text_event_category.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,
                2.0f));
        text_event_category.setPadding(3,3,3,3);
        text_event_category.setText(MainActivity.Events.get(id).getCategory());
        text_event_category.setTextColor(colorDark);
        text_event_category.setTextSize(18);
        text_event_category.setTypeface(null, Typeface.BOLD);

        details_title.addView(text_event_category);


        // layout containing the details
        LinearLayout.LayoutParams params_details_container=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                2.0f);
        params_details_title.setMargins(0, margin, 0, margin*5);
        details_container.setLayoutParams(params_details_container);
        details_container.setOrientation(LinearLayout.HORIZONTAL);

        // parameters for textviews
        LinearLayout.LayoutParams params_textView= new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params_textView.setMargins(0, margin, 0, margin);


        // Column 1 : Date / Duration
        details_col_1.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT,
                0.5f));
        details_col_1.setOrientation(LinearLayout.VERTICAL);
        details_col_1.setGravity(Gravity.CENTER_VERTICAL);

        text_event_date.setGravity(Gravity.CENTER);
        text_event_date.setLayoutParams(params_textView);
        text_event_date.setText(MainActivity.Events.get(id).getDate());
        text_event_date.setTextColor(colorDark);
        text_event_date.setTextSize(14);

        text_event_duration.setGravity(Gravity.CENTER);
        text_event_duration.setLayoutParams(params_textView);
        text_event_duration.setText(MainActivity.Events.get(id).getDuration());
        text_event_duration.setTextColor(colorDark);
        text_event_duration.setTextSize(14);

        details_col_1.addView(text_event_date);
        details_col_1.addView(text_event_duration);

        // Column 2 : Type / Distance
        details_col_2.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT,
                0.5f));
        details_col_2.setOrientation(LinearLayout.VERTICAL);
        details_col_2.setGravity(Gravity.CENTER_VERTICAL);

        text_event_type.setGravity(Gravity.CENTER);
        text_event_type.setLayoutParams(params_textView);
        text_event_type.setText(MainActivity.Events.get(id).getType());
        text_event_type.setTextColor(colorDark);
        text_event_type.setTextSize(14);

        text_event_distance.setGravity(Gravity.CENTER);
        text_event_distance.setLayoutParams(params_textView);
        text_event_distance.setText(MainActivity.Events.get(id).getDistance());
        text_event_distance.setTextColor(colorDark);
        text_event_distance.setTextSize(14);

        details_col_2.addView(text_event_type);
        details_col_2.addView(text_event_distance);

        // Column 3 : Place / Address
        details_col_3.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f));
        details_col_3.setOrientation(LinearLayout.VERTICAL);
        details_col_3.setGravity(Gravity.CENTER_VERTICAL);

        text_event_place.setGravity(Gravity.CENTER);
        text_event_place.setLayoutParams(params_textView);
        text_event_place.setText(MainActivity.Events.get(id).getPlace());
        text_event_place.setTextColor(colorDark);
        text_event_place.setTextSize(14);

        text_event_address.setGravity(Gravity.CENTER);
        text_event_address.setLayoutParams(params_textView);
        text_event_address.setText(MainActivity.Events.get(id).getAddress());
        text_event_address.setTextColor(colorDark);
        text_event_address.setTextSize(14);

        details_col_3.addView(text_event_place);
        details_col_3.addView(text_event_address);



        layout_evt_details.addView(details_title);
        details_container.addView(details_col_1);
        details_container.addView(details_col_2);
        details_container.addView(details_col_3);
        layout_evt_details.addView(details_container);


        card_event.addView(layout_evt_details);

        return card_event;

    }

}
