package com.company.meetsports.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.company.meetsports.Adapters.MyEventsAdapter;
import com.company.meetsports.DataProvider.ApiClient;
import com.company.meetsports.DataProvider.ApiInterface;
import com.company.meetsports.Entities.Event;
import com.company.meetsports.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.company.meetsports.Activities.MainActivity.id_user;


public class EventFragment extends Fragment {
    private static final String TAG = "EventFragment";

    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerViewForAsync;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event, container, false);
        Log.d(TAG, "onCreate...");
        Toast.makeText(getActivity(), "User id: " + id_user.toString(), Toast.LENGTH_LONG).show();
        Log.d(TAG, "User id: " + id_user.toString());
        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.events_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewForAsync = recyclerView;

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Event>> call = apiService.getEventsAttendingByUserId(id_user);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                int statusCode = response.code();
                Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                List<Event> myEvents = response.body();
                if (myEvents.size() != 0) {
                    recyclerView.setAdapter(new MyEventsAdapter(myEvents, R.layout.list_item_event_my, getActivity().getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());

            }
        });


        // Refresh scrolling
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return v;
    }

    public void fetchTimelineAsync(int page) {
        Toast.makeText(getActivity(), "Updating...", Toast.LENGTH_SHORT).show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Event>> call = apiService.getEventsAttendingByUserId(id_user);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                int statusCode = response.code();
                Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                List<Event> myEvents = response.body();
                recyclerViewForAsync.setAdapter(new MyEventsAdapter(myEvents, R.layout.list_item_event_my, getActivity().getApplicationContext()));
                if (response.isSuccessful())
                    swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());

            }
        });
    }


}
