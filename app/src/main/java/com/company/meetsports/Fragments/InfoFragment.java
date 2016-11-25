package com.company.meetsports.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.company.meetsports.R;

/**
 * Created by VMabille on 08/11/2016.
 */

public class InfoFragment extends Fragment {
    private static final String TAG = "InfoFragment";

    private RatingBar ratingBar;
    private Button rateBtn;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);
        Log.d(TAG, "onCreate...");

        ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        rateBtn = (Button) v.findViewById(R.id.ratingSubmit);
        //Performing action on Button Click
        rateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Getting the rating and displaying it on the toast
                String rating = String.valueOf(ratingBar.getRating());
                Toast.makeText(getActivity(), "Thank you for the feedback !", Toast.LENGTH_SHORT).show();
            }

        });
        return v;
    }
}
