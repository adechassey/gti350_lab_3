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
        TextView minDuration;
        TextView maxDuration;
        TextView minParticipants;
        TextView maxParticipants;
        TextView minAge;
        TextView maxAge;
        TextView minContribution;
        TextView maxContribution;
        TextView level;
        TextView latitude;
        TextView longitude;

        public EventViewHolder(View v) {
            super(v);
            eventsLayout = (LinearLayout) v.findViewById(R.id.events_layout);
            type = (TextView) v.findViewById(R.id.type);
            category = (TextView) v.findViewById(R.id.category);
            date_time = (TextView) v.findViewById(R.id.date_time);
            minDuration = (TextView) v.findViewById(R.id.minDuration);
            maxDuration = (TextView) v.findViewById(R.id.maxDuration);
            minParticipants = (TextView) v.findViewById(R.id.minParticipants);
            maxParticipants = (TextView) v.findViewById(R.id.maxParticipants);
            minAge = (TextView) v.findViewById(R.id.minAge);
            maxAge = (TextView) v.findViewById(R.id.maxAge);
            minContribution = (TextView) v.findViewById(R.id.minContribution);
            maxContribution = (TextView) v.findViewById(R.id.maxContribution);
            level = (TextView) v.findViewById(R.id.level);
            latitude = (TextView) v.findViewById(R.id.latitude);
            longitude = (TextView) v.findViewById(R.id.longitude);
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
        holder.minDuration.setText(events.get(position).getMinDuration().toString());
        holder.maxDuration.setText(events.get(position).getMaxDuration().toString());
        holder.minParticipants.setText(events.get(position).getMinParticipants().toString());
        holder.minAge.setText(events.get(position).getMinAge().toString());
        holder.maxAge.setText(events.get(position).getMaxAge().toString());
        holder.minContribution.setText(events.get(position).getMinContribution().toString());
        holder.maxContribution.setText(events.get(position).getMaxContribution().toString());
        holder.level.setText(events.get(position).getLevel());
        holder.latitude.setText(events.get(position).getLatitude().toString());
        holder.longitude.setText(events.get(position).getLongitude().toString());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}