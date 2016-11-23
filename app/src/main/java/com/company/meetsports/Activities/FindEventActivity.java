package com.company.meetsports.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.company.meetsports.Adapters.FindEventsAdapter;
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

/**
 * Created by Antoine on 02/10/2016.
 * <p>
 * A big thanks to this tutorial:
 * https://androidhub.intel.com/en/posts/nglauber/Android_Search.html
 */

public class FindEventActivity extends AppCompatActivity implements PlaceSelectionListener {
    private static final String TAG = "FindEventActivity";

    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private static List<Event> findEvents;

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

        // Refresh scrolling
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
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
        recyclerView = (RecyclerView) findViewById(R.id.events_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Event>> call = apiService.getAllEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                int statusCode = response.code();
                Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                findEvents = response.body();
                recyclerView.setAdapter(new FindEventsAdapter(findEvents, R.layout.list_item_event_find, getApplicationContext().getApplicationContext()));
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());

            }
        });
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


    public void fetchTimelineAsync(int page) {
        Toast.makeText(getApplicationContext(), "Updating...", Toast.LENGTH_SHORT).show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Event>> call = apiService.getAllEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                int statusCode = response.code();
                Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                findEvents = response.body();
                recyclerView.setAdapter(new FindEventsAdapter(findEvents, R.layout.list_item_event_find, getApplicationContext()));
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
