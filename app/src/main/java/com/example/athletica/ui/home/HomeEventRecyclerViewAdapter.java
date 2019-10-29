package com.example.athletica.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.athletica.R;
import com.example.athletica.data.event.Event;

import java.util.List;

public class HomeEventRecyclerViewAdapter extends RecyclerView.Adapter<HomeEventRecyclerViewAdapter.ViewHolder> {

    private List<Event> eventList;

    public HomeEventRecyclerViewAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_event_list_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.tvEventName.setText(event.getName());
        holder.tvEventLocation.setText(event.getLocation());
        holder.tvEventDate.setText(event.getStartDate());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvEventName, tvEventDate, tvEventLocation;


        public ViewHolder(View view) {
            super(view);
            tvEventName = view.findViewById(R.id.tv_event_date);
            tvEventDate = view.findViewById(R.id.tv_event_date);
            tvEventLocation = view.findViewById(R.id.tv_event_location);
        }


    }
}
