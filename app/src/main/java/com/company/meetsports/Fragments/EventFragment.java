package com.company.meetsports.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.company.meetsports.Adapters.EventsAdapter;
import com.company.meetsports.DataProvider.ApiClient;
import com.company.meetsports.DataProvider.ApiInterface;
import com.company.meetsports.Entities.Event;
import com.company.meetsports.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventFragment extends Fragment {
    private static final String TAG = "EventFragment";

    private RecyclerView recyclerView;
    private static List<Event> events;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.events_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Event>> call = apiService.getAllEvents();
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                int statusCode = response.code();
                Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                events = response.body();
                recyclerView.setAdapter(new EventsAdapter(events, R.layout.list_item_event, getActivity().getApplicationContext()));

                /*
                recyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, final int position) {
                                // do whatever

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
                                                final ViewGroup layout = (LinearLayout) getActivity().findViewById(R.id.layout_events);

                                                final Integer id_event = Integer.parseInt(layout.findViewById(R.id.id_event).getTag().toString());

                                                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                                                Call<Event> call = apiService.deleteEvent(id_event);
                                                call.enqueue(new Callback<Event>() {
                                                    @Override
                                                    public void onResponse(Call<Event> call, Response<Event> response) {
                                                        int statusCode = response.code();
                                                        Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                                                        if (statusCode == 204) {
                                                            Log.d(TAG, "Position: " + String.valueOf(position));
                                                            Log.d(TAG, "id_event: " + String.valueOf(id_event));
                                                            events.remove(events.get(position));

                                                            recyclerView.getAdapter().notifyDataSetChanged();
                                                            recyclerView.setAdapter(new EventsAdapter(events, R.layout.list_item_event, getActivity().getApplicationContext()));


                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Event> call, Throwable t) {
                                                        // Log error here since request failed
                                                        Log.e(TAG, t.toString());
                                                    }
                                                });
                                                dialog.dismiss();
                                            }
                                        });
                                builder.show();
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // do whatever
                            }
                        })
                );
                */
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
