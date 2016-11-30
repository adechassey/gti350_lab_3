package com.company.meetsports.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.meetsports.DataProvider.ApiClient;
import com.company.meetsports.DataProvider.ApiInterface;
import com.company.meetsports.Entities.Event;
import com.company.meetsports.R;

import java.text.SimpleDateFormat;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.company.meetsports.Activities.MainActivity.id_user;


public class FindEventsAdapter extends RecyclerView.Adapter<FindEventsAdapter.EventViewHolder> {
    private static final String TAG = "MyEventsAdapter";

    private List<Event> findEvents;
    private int rowLayout;
    private Context context;

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        LinearLayout eventsLayout;
        CardView id_event;
        TextView type;
        TextView category;
        TextView date;
        TextView time;
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
            type = (TextView) v.findViewById(R.id.type);
            category = (TextView) v.findViewById(R.id.category);
            date = (TextView) v.findViewById(R.id.date);
            time = (TextView) v.findViewById(R.id.time);
            duration = (TextView) v.findViewById(R.id.duration);
            participants = (TextView) v.findViewById(R.id.nb_participants);
            age = (TextView) v.findViewById(R.id.age);
            contribution = (TextView) v.findViewById(R.id.contribution);
            level = (TextView) v.findViewById(R.id.level);
            place = (TextView) v.findViewById(R.id.place);
            address = (TextView) v.findViewById(R.id.address);
        }
    }

    public FindEventsAdapter(List<Event> findEvents, int rowLayout, Context context) {
        this.findEvents = findEvents;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public FindEventsAdapter.EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new EventViewHolder(view);
    }


    @Override
    public void onBindViewHolder(EventViewHolder holder, final int position) {
        // Ici pour controller ce qui est affiché dans les CardViews
        holder.id_event.setTag(findEvents.get(position).getId_event());
        holder.type.setText(findEvents.get(position).getType());
        holder.category.setText(findEvents.get(position).getCategory());
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
        holder.date.setText(sdfDate.format(findEvents.get(position).getDate_time()));
        holder.time.setText(sdfTime.format(findEvents.get(position).getDate_time()));
        holder.duration.setText(findEvents.get(position).getMinDuration().toString() + " - " + findEvents.get(position).getMaxDuration().toString() + "h");
        holder.age.setText(findEvents.get(position).getMinAge().toString() + " - " + findEvents.get(position).getMaxAge().toString() + " years");
        holder.participants.setText(findEvents.get(position).getMinParticipants().toString() + " - " + findEvents.get(position).getMaxParticipants().toString() + " persons");
        /*holder.contribution.setText(findEvents.get(position).getMinContribution().toString() + " - " + findEvents.get(position).getMaxContribution().toString() + "$");
        holder.level.setText(findEvents.get(position).getLevel());
        holder.place.setText(findEvents.get(position).getPlace());*/
        holder.address.setText(findEvents.get(position).getAddress());

        // Set a click listener for TextView
        /*
        holder.id_event.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Event event = findEvents.get(position);
                Toast.makeText(context, event.getId_event(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        */


        // Ici pour controller les détails supplémentaires lors d'un clic sur un event
        holder.id_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder((Activity) view.getContext(), R.style.CardView_Dark);
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
                ListView modeList = new ListView((Activity) view.getContext());
                String[] stringArray = new String[]{
                        "Type: " + findEvents.get(position).getType(),
                        "Category: " + findEvents.get(position).getCategory(),
                        "Date: " + sdfDate.format(findEvents.get(position).getDate_time()),
                        "Time: " + sdfTime.format(findEvents.get(position).getDate_time()),
                        "Duration: " + findEvents.get(position).getMinDuration().toString() + " - " + findEvents.get(position).getMaxDuration().toString() + "h",
                        "Age: " + findEvents.get(position).getMinAge().toString() + " - " + findEvents.get(position).getMaxAge().toString() + " years",
                        "Participants: " + findEvents.get(position).getMinParticipants().toString() + " - " + findEvents.get(position).getMaxParticipants().toString() + " persons",
                        "Contributions: " + findEvents.get(position).getMinContribution().toString() + " - " + findEvents.get(position).getMaxContribution().toString() + "$",
                        "Level: " + findEvents.get(position).getLevel(),
                        "Place: " + findEvents.get(position).getPlace(),
                        "Address: " + findEvents.get(position).getAddress()
                };
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>((Activity) view.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
                modeList.setAdapter(modeAdapter);

                builder
                        .setTitle("More details")
                        .setIcon(R.drawable.ic_my_events)
                        .setView(modeList)
                        .setNegativeButton("Back", null)
                        .setPositiveButton("I am going", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final Event event = findEvents.get(position);

                                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                                Call<ResponseBody> call = apiService.addAttendance(event.getId_event(), id_user);
                                call.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        int statusCode = response.code();
                                        Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                                        if (statusCode == 204) {
                                            // Remove the item on remove/button click
                                            /*findEvents.add(event);
                                            notifyItemInserted(position);
                                            notifyItemRangeChanged(position, findEvents.size());*/

                                            // Show the removed item label
                                            Toast.makeText(context, "Event added successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        // Log error here since request failed
                                        Log.e(TAG, t.toString());
                                    }
                                });
                            }
                        });
                final Dialog dialog = builder.create();

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (findEvents == null)
            return 0;
        return findEvents.size();
    }
}