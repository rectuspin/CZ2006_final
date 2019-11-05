package com.example.athletica.ui.search;


import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.athletica.R;
import com.example.athletica.data.DisplayAll.DisplayController;
import com.example.athletica.data.facility.Facility;
import com.example.athletica.data.user.DataManager;

import java.util.ArrayList;

public class DisplayAll extends AppCompatActivity {


    private int state;

    private DataManager dataManager;
    private DisplayController displayController;
    private ArrayList<Facility> facilities, sortedFacilties;
    private ImageButton btnSort;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all);


        state = Integer.parseInt(getIntent().getStringExtra("state"));
        dataManager = new DataManager();
        displayController = new DisplayController(this, state);
        btnSort = findViewById(R.id.action_sort);


        if (state == 0) displayController.getFacilities(this);
        else if (state == 1) displayController.getEvents(this);
        else displayController.getUsers(this);


        sortedFacilties = displayController.sortFacilityByName();
        ArrayList<String> sortedFFacilityNames = new ArrayList<>();
        ArrayList<String> sortedFacilityIndex = new ArrayList<>();

        for (Facility facility : sortedFacilties) {
            sortedFacilityIndex.add(facility.getFacilityIndex());
            sortedFFacilityNames.add(facility.getName());
        }

        if (state != 0) {
            btnSort.setVisibility(View.GONE);
        }

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRecyclerView(state, sortedFFacilityNames, sortedFacilityIndex);
            }
        });


    }

    public void initRecyclerView(int id, ArrayList<String> names, ArrayList<String> index) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView3);
        recyclerView.setLayoutManager(layoutManager);
        Layout_mainpage adapter = new Layout_mainpage(this, names, index, id);
        recyclerView.setAdapter(adapter);
    }

}
