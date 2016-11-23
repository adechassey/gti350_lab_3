package com.company.meetsports.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.meetsports.Adapters.MyEventsAdapter;
import com.company.meetsports.DataProvider.ApiClient;
import com.company.meetsports.DataProvider.ApiInterface;
import com.company.meetsports.Entities.Event;
import com.company.meetsports.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Antoine on 09/11/2016.
 */

public class DebugFragment extends Fragment {
    private static final String TAG = "DebugFragment";

    /**
     * http://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
     */

    // TODO - insert API KEY here (https://www.themoviedb.org)
    //private final static String API_KEY = "3dc7a68bed9ff5f3029a3ae47a9f6589";
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_debug, container, false);
/*
        if (API_KEY.isEmpty()) {
            Toast.makeText(getActivity(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
        }
*/
        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.events_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Event>> call = apiService.getAllEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                int statusCode = response.code();
                Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                List<Event> myEvents = response.body();
                recyclerView.setAdapter(new MyEventsAdapter(myEvents, R.layout.list_item_event_my, getActivity().getApplicationContext()));
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

        return v;
    }
}
