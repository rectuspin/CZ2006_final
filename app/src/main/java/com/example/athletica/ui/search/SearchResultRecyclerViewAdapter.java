package com.example.athletica.ui.search;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.athletica.R;
import com.example.athletica.ui.event.ViewEventActivity;
import com.example.athletica.ui.facility.ViewFacilityActivity;
import com.example.athletica.ui.profile.ViewProfileActivity;

import java.util.ArrayList;

public class SearchResultRecyclerViewAdapter extends RecyclerView.Adapter<SearchResultRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "SearchResultRecyclerViewAdapter";
    private int id;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> index = new ArrayList<>();
    private Context mContext;


    public SearchResultRecyclerViewAdapter(Context mContext, ArrayList<String> mNames, ArrayList<String> index, int id) {
        this.mNames = mNames;
        this.mContext = mContext;
        this.index = index;
        this.id = id;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_recyclerview_card, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.v("recycler running ",mNames.get(position));
        holder.name.setText(mNames.get(position));
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start different activity based on the id
                Intent intent;
                switch (id) {
                    case 0:
                        // Start ViewFacilityActivity with the
                        intent = new Intent(mContext, ViewFacilityActivity.class);
                        intent.putExtra("index", index.get(position));
                        mContext.startActivity(intent);
                        break;
                    case 1:
                        // Start ViewEventActivity with the corresponding event
                        intent = new Intent(mContext, ViewEventActivity.class);
                        intent.putExtra("key", index.get(position));
                        mContext.startActivity(intent);
                        break;
                    case 2:
                        // Start ViewProfileActivity
                        intent = new Intent(mContext, ViewProfileActivity.class);
                        intent.putExtra("key", index.get(position));
                        mContext.startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
        }
    }

}
