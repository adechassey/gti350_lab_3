package com.company.meetsports.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.company.meetsports.DataProvider.ApiClient;
import com.company.meetsports.DataProvider.ApiInterface;
import com.company.meetsports.Entities.Event;
import com.company.meetsports.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {
    private static final String TAG = "EventsAdapter";

    private List<Event> events;
    private int rowLayout;
    private Context context;

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        LinearLayout eventsLayout;
        CardView id_event;
        Button btn_delete;
        TextView type;
        TextView category;
        TextView date_time;
        TextView duration;
        TextView participants;
        TextView age;
        TextView contribution;
        TextView level;
        TextView place;
        TextView address;

        public EventViewHolder(View v) {
            super(v);
            eventsLayout = (LinearLayout) v.findViewById(R.id.events_layout);
            id_event = (CardView) v.findViewById(R.id.id_event);
            btn_delete = (Button) v.findViewById(R.id.btn_delete);
            type = (TextView) v.findViewById(R.id.type);
            category = (TextView) v.findViewById(R.id.category);
            date_time = (TextView) v.findViewById(R.id.date_time);
            duration = (TextView) v.findViewById(R.id.duration);
            participants = (TextView) v.findViewById(R.id.participants);
            age = (TextView) v.findViewById(R.id.age);
            contribution = (TextView) v.findViewById(R.id.contribution);
            level = (TextView) v.findViewById(R.id.level);
            place = (TextView) v.findViewById(R.id.place);
            address = (TextView) v.findViewById(R.id.address);
        }
    }

    public EventsAdapter(List<Event> events, int rowLayout, Context context) {
        this.events = events;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public EventsAdapter.EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new EventViewHolder(view);
    }


    @Override
    public void onBindViewHolder(EventViewHolder holder, final int position) {
        holder.id_event.setTag(events.get(position).getId_event());
        holder.type.setText(events.get(position).getType());
        holder.category.setText(events.get(position).getCategory());
        holder.date_time.setText(events.get(position).getDate_time().toString());
        holder.duration.setText(events.get(position).getMinDuration().toString() + " - " + events.get(position).getMaxDuration().toString() + "h");
        holder.participants.setText(events.get(position).getMinParticipants().toString() + " - " + events.get(position).getMaxParticipants().toString() + " persons");
        holder.age.setText(events.get(position).getMinAge().toString() + " - " + events.get(position).getMaxAge().toString() + " years");
        holder.contribution.setText(events.get(position).getMinContribution().toString() + " - " + events.get(position).getMaxContribution().toString() + "$");
        holder.level.setText(events.get(position).getLevel());
        holder.place.setText(events.get(position).getPlace());
        holder.address.setText(events.get(position).getAddress());


        // Set a click listener for TextView
        /*
        holder.id_event.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Event event = events.get(position);
                Toast.makeText(context, event.getId_event(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        */

        holder.id_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDialog.Builder builder = new MaterialDialog.Builder((Activity) view.getContext());
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
                                Event event = events.get(position);

                                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                                Call<Event> call = apiService.deleteEvent(event.getId_event());
                                call.enqueue(new Callback<Event>() {
                                    @Override
                                    public void onResponse(Call<Event> call, Response<Event> response) {
                                        int statusCode = response.code();
                                        Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                                        if (statusCode == 204) {
                                            // Remove the item on remove/button click
                                            events.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, events.size());

                                            // Show the removed item label
                                            Toast.makeText(context, "Removed event successfully", Toast.LENGTH_SHORT).show();
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
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) view.getContext());

                alertDialog.setTitle("Remove this event?");
                alertDialog.setMessage("Are you sure you want to remove this?");
                alertDialog.setIcon(R.drawable.ic_bin);
                alertDialog.setNegativeButton(
                        "Back",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }
                );
                alertDialog.setPositiveButton(
                        "Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Do the stuff..
                                // Get the clicked item label
                                Event event = events.get(position);

                                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                                Call<Event> call = apiService.deleteEvent(event.getId_event());
                                call.enqueue(new Callback<Event>() {
                                    @Override
                                    public void onResponse(Call<Event> call, Response<Event> response) {
                                        int statusCode = response.code();
                                        Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                                        if (statusCode == 204) {
                                            // Remove the item on remove/button click
                                            events.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, events.size());

                                            // Show the removed item label
                                            Toast.makeText(context, "Removed event successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Event> call, Throwable t) {
                                        // Log error here since request failed
                                        Log.e(TAG, t.toString());
                                    }
                                });
                            }
                        }
                );
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (events == null)
            return 0;
        return events.size();
    }
}