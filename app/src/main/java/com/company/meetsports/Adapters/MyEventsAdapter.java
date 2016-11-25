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
import android.widget.Button;
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

import static com.company.meetsports.Activities.SignInActivity.id_user;

public class MyEventsAdapter extends RecyclerView.Adapter<MyEventsAdapter.EventViewHolder> {
    private static final String TAG = "MyEventsAdapter";

    private List<Event> myEvents;
    private int rowLayout;
    private Context context;

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        LinearLayout eventsLayout;
        CardView id_event;
        Button btn_delete;
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
            btn_delete = (Button) v.findViewById(R.id.btn_delete);
            type = (TextView) v.findViewById(R.id.type);
            category = (TextView) v.findViewById(R.id.category);
            date = (TextView) v.findViewById(R.id.date);
            time = (TextView) v.findViewById(R.id.time);
            duration = (TextView) v.findViewById(R.id.duration);
            participants = (TextView) v.findViewById(R.id.participants);
            age = (TextView) v.findViewById(R.id.age);
            contribution = (TextView) v.findViewById(R.id.contribution);
            level = (TextView) v.findViewById(R.id.level);
            place = (TextView) v.findViewById(R.id.place);
            address = (TextView) v.findViewById(R.id.address);
        }
    }

    public MyEventsAdapter(List<Event> myEvents, int rowLayout, Context context) {
        this.myEvents = myEvents;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MyEventsAdapter.EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new EventViewHolder(view);
    }


    @Override
    public void onBindViewHolder(EventViewHolder holder, final int position) {
        // Ici pour controller ce qui est affiché dans les CardViews
        holder.id_event.setTag(myEvents.get(position).getId_event());
        holder.type.setText(myEvents.get(position).getType());
        holder.category.setText(myEvents.get(position).getCategory());
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
        holder.date.setText(sdfDate.format(myEvents.get(position).getDate_time()));
        holder.time.setText(sdfTime.format(myEvents.get(position).getDate_time()));
        holder.duration.setText(myEvents.get(position).getMinDuration().toString() + " - " + myEvents.get(position).getMaxDuration().toString() + "h");
        holder.age.setText(myEvents.get(position).getMinAge().toString() + " - " + myEvents.get(position).getMaxAge().toString() + " years");
        /*holder.participants.setText(myEvents.get(position).getMinParticipants().toString() + " - " + myEvents.get(position).getMaxParticipants().toString() + " persons");
        holder.contribution.setText(myEvents.get(position).getMinContribution().toString() + " - " + myEvents.get(position).getMaxContribution().toString() + "$");
        holder.level.setText(myEvents.get(position).getLevel());
        holder.place.setText(myEvents.get(position).getPlace());*/
        holder.address.setText("A definir..");

        // Set a click listener for TextView
        /*
        holder.id_event.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Event event = myEvents.get(position);
                Toast.makeText(context, event.getId_event(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        */
        /*
        Event event = new Event(
                myEvents.get(position).getId_event(),
                myEvents.get(position).getType(),
                myEvents.get(position).getCategory(),
                myEvents.get(position).getDate_time(),
                myEvents.get(position).getMinDuration(),
                myEvents.get(position).getMaxDuration(),
                myEvents.get(position).getMinAge(),
                myEvents.get(position).getMaxAge(),
                myEvents.get(position).getMinParticipants(),
                myEvents.get(position).getMaxParticipants(),
                myEvents.get(position).getMinContribution(),
                myEvents.get(position).getMaxContribution(),
                myEvents.get(position).getLevel(),
                myEvents.get(position).getPlace(),
                myEvents.get(position).getAddress()
        );
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
                        "Type: " + myEvents.get(position).getType(),
                        "Category: " + myEvents.get(position).getCategory(),
                        "Date: " + sdfDate.format(myEvents.get(position).getDate_time()),
                        "Time: " + sdfTime.format(myEvents.get(position).getDate_time()),
                        "Duration: " + myEvents.get(position).getMinDuration().toString() + " - " + myEvents.get(position).getMaxDuration().toString() + "h",
                        "Age: " + myEvents.get(position).getMinAge().toString() + " - " + myEvents.get(position).getMaxAge().toString() + " years",
                        "Participants: " + myEvents.get(position).getMinParticipants().toString() + " - " + myEvents.get(position).getMaxParticipants().toString() + " persons",
                        "Contributions: " + myEvents.get(position).getMinContribution().toString() + " - " + myEvents.get(position).getMaxContribution().toString() + "$",
                        "Level: " + myEvents.get(position).getLevel(),
                        "Place: " + myEvents.get(position).getPlace(),
                        "Address: " + myEvents.get(position).getAddress()
                };
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>((Activity) view.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
                modeList.setAdapter(modeAdapter);

                builder
                        .setTitle("More details")
                        .setIcon(R.drawable.ic_my_events)
                        .setView(modeList)
                        .setPositiveButton("Back", null)
                        .setNegativeButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Event event = myEvents.get(position);

                                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                                Call<ResponseBody> call = apiService.deleteAttendance(event.getId_event(), id_user);
                                call.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        int statusCode = response.code();
                                        Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                                        if (statusCode == 204) {
                                            // Remove the item on remove/button click
                                            myEvents.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, myEvents.size());

                                            // Show the removed item label
                                            Toast.makeText(context, "Removed event successfully", Toast.LENGTH_SHORT).show();
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

                /*
                MaterialDialog.Builder builder = new MaterialDialog.Builder((Activity) view.getContext());
                builder.title("Event details")
                        .positiveText("Close")
                        .negativeText("Delete")
                        .adapter(arrayAdapter, null)
                        //.customView(R.layout.list_item_event_details, true)
                        .titleColor(-1)
                        .positiveColor(-1)
                        .negativeColor(-1)
                        .backgroundColorRes(R.color.colorAppBackground)
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Event event = myEvents.get(position);

                                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                                Call<Event> call = apiService.deleteEvent(event.getId_event());
                                call.enqueue(new Callback<Event>() {
                                    @Override
                                    public void onResponse(Call<Event> call, Response<Event> response) {
                                        int statusCode = response.code();
                                        Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                                        if (statusCode == 204) {
                                            // Remove the item on remove/button click
                                            myEvents.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, myEvents.size());

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
                builder.show();*/
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
                                Event event = myEvents.get(position);

                                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                                Call<ResponseBody> call = apiService.deleteAttendance(event.getId_event(), id_user);
                                call.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        int statusCode = response.code();
                                        Log.d(TAG, "Status code: " + String.valueOf(statusCode));
                                        if (statusCode == 204) {
                                            // Remove the item on remove/button click
                                            myEvents.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, myEvents.size());

                                            // Show the removed item label
                                            Toast.makeText(context, "Removed event successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
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
        if (myEvents == null)
            return 0;
        return myEvents.size();
    }
}