package com.example.athletica.ui.search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.athletica.R;
import com.example.athletica.ui.facility.ViewFacilityActivity;

import java.util.ArrayList;

public class Layout_mainpage extends RecyclerView.Adapter<Layout_mainpage.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> index = new ArrayList<>();
    private Context mContext;
    private int id;

    public Layout_mainpage(Context mContext, ArrayList<String> mNames, ArrayList<String> index, int id) {
        this.mNames = mNames;
        this.mContext = mContext;
        this.index = index;
        this.id = id;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_mainpage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txt1.setText(mNames.get(position));
        holder.txt2.setText(String.valueOf(position));
        holder.txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == 0) {
                    Intent intent = new Intent(mContext, ViewFacilityActivity.class);
                    intent.putExtra("index", index.get(position));
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_LONG).show();
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
