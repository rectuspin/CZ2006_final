package com.example.athletica.ui.profile;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.athletica.R;

import java.util.ArrayList;

public class CustomArrayAdapter extends BaseAdapter {

    private TextView sport;
    private ImageButton delete;
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<String> items;


    public CustomArrayAdapter(Activity activity, ArrayList<String> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_sportpreference, null);

        sport = convertView.findViewById(R.id.sportName);
        delete = convertView.findViewById(R.id.deleteButton);
        String sportString = items.get(position);
        sport.setText(sportString);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

}
