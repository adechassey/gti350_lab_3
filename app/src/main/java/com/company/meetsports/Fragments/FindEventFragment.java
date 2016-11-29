package com.company.meetsports.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.company.meetsports.Activities.MainActivity;
import com.company.meetsports.Adapters.FindEventsAdapter;
import com.company.meetsports.Adapters.MyEventsAdapter;
import com.company.meetsports.DataProvider.ApiClient;
import com.company.meetsports.DataProvider.ApiInterface;
import com.company.meetsports.Entities.Event;
import com.company.meetsports.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.company.meetsports.Activities.MainActivity.id_user;

/**
 * Created by VMabille on 28/11/2016.
 */

public class FindEventFragment extends Fragment  {
    private static final String TAG = "FindEventFragment";

    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerViewForAsync;
    private static List<Event> findEvents;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_find_events, container, false);
        Log.d(TAG, "onCreate...");
        Toast.makeText(getActivity(), "User id: " + id_user.toString(), Toast.LENGTH_LONG).show();
        Log.d(TAG, "User id: " + id_user.toString());
        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.find_events_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewForAsync = recyclerView;

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Event>> call = apiService.getAllEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                int statusCode = response.code();
                Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                findEvents = response.body();
                if (findEvents.size() != 0) {
                    recyclerView.setAdapter(new FindEventsAdapter(findEvents, R.layout.list_item_event_find, getActivity().getApplicationContext()));
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
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer_find_events);
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
        Call<List<Event>> call = apiService.getAllEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                int statusCode = response.code();
                Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                findEvents = response.body();
                recyclerViewForAsync.setAdapter(new FindEventsAdapter(findEvents, R.layout.list_item_event_find, getActivity().getApplicationContext()));
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












    /*

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_find_events, container, false);
        Log.d(TAG, "onCreate...");

        // Retrieve the PlaceAutocompleteFragment.
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment_find_events);

        // Register a listener to receive callbacks when a place has been selected or an error has
        // occurred.
        autocompleteFragment.setOnPlaceSelectedListener(this);

        // Refresh scrolling
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer_find_events);
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

        // Getting findEvents
        recyclerView = (RecyclerView) v.findViewById(R.id.find_events_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Event>> call = apiService.getAllEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                int statusCode = response.code();
                Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                findEvents = response.body();
                recyclerView.setAdapter(new FindEventsAdapter(findEvents, R.layout.list_item_event_find, getActivity().getApplicationContext()));
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());

            }
        });

        return v;
    }





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
        getActivity().setResult(MainActivity.RESULT_OK, intent);
    }

    public void onError(Status status) {
        Log.e(TAG, "onError: Status = " + status.toString());

        Toast.makeText(getActivity(), "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }


    public void fetchTimelineAsync(int page) {
        Toast.makeText(getActivity(), "Updating...", Toast.LENGTH_SHORT).show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Event>> call = apiService.getAllEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                int statusCode = response.code();
                Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                findEvents = response.body();
                recyclerView.setAdapter(new FindEventsAdapter(findEvents, R.layout.list_item_event_find, getActivity()));
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
*/