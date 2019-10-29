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

public class Layout_mainpage extends RecyclerView.Adapter<Layout_mainpage.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> index = new ArrayList<>();
    private Context mContext;
    private int state;

    public Layout_mainpage(Context mContext, ArrayList<String> mNames, ArrayList<String> index, int state) {
        this.mNames = mNames;
        this.mContext = mContext;
        this.index = index;
        this.state = state;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_mainpage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.v("view all running", mNames.toString());
        holder.txt1.setText(mNames.get(position));
        holder.txt2.setText(String.valueOf(position));
        holder.txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state == 0) {
                    Intent intent = new Intent(mContext, ViewFacilityActivity.class);
                    intent.putExtra("index", index.get(position));
                    mContext.startActivity(intent);
                } else if (state == 1) {
                    Intent intent = new Intent(mContext, ViewEventActivity.class);
                    intent.putExtra("key", index.get(position));
                    mContext.startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, ViewProfileActivity.class);
                    intent.putExtra("key", index.get(position));
                    mContext.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt1;
        TextView txt2;

        public ViewHolder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textView_1_main_page);
            txt2 = itemView.findViewById(R.id.textView_2_main_page);
        }
    }

}
