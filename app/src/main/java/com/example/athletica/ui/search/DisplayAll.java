package com.example.athletica.ui.search;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.athletica.R;
import com.example.athletica.data.event.Event;

import java.util.ArrayList;
import java.util.Map;

public class DisplayAll extends AppCompatActivity {


    private ArrayList<String> facilities = new ArrayList<>();
    private ArrayList<String> index = new ArrayList<>();

    private int state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all);


        ArrayList<Map> sample = (ArrayList<Map>) getIntent().getSerializableExtra("Array_List");
        state = Integer.parseInt(getIntent().getStringExtra("state"));


        if (state == 0)
            initFacility(sample);


    }

    private void initFacility(ArrayList<Map> sample) {

        for (Map<String, String> map : sample) {
            String str2 = map.get("name");  //parsing facilities and index's in separate lists
            String i = map.get("index");
            facilities.add(str2);
            index.add(i);
        }

        initRecyclerView(0);
    }

    private void initRecyclerView(int id) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView3);
        recyclerView.setLayoutManager(layoutManager);
        Layout_mainpage adapter = new Layout_mainpage(this, facilities, index, id);
        recyclerView.setAdapter(adapter);
    }


    private void initEvents(ArrayList<Event> eventList) {

        //get basic event details and unique id
    }

}
