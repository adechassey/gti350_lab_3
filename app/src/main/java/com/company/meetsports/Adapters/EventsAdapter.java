package com.company.meetsports.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.meetsports.Entities.Event;
import com.company.meetsports.R;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {

    private List<Event> events;
    private int rowLayout;
    private Context context;

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        LinearLayout eventsLayout;
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
    }

    @Override
    public int getItemCount() {
        if (events == null)
            return 0;
        return events.size();
    }
}