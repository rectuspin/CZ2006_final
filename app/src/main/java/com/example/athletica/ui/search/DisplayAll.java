package com.example.athletica.ui.search;


import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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
    private TextView Title;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all);


        state = Integer.parseInt(getIntent().getStringExtra("state"));
        dataManager = new DataManager();
        displayController = new DisplayController(this, state);
        btnSort = findViewById(R.id.action_sort);
        Title=findViewById(R.id.DisplayAllTitle);


        if (state == 0) {
            Title.setText("Facilities");displayController.getFacilities(this);}
        else if (state == 1){ Title.setText("Events"); displayController.getEvents(this);}
        else{Title.setText("Users"); displayController.getUsers(this);}


        sortedFacilties = displayController.sortFacilityByName();
        ArrayList<String> sortedFFacilityNames = new ArrayList<>();
        ArrayList<String> sortedFacilityIndex = new ArrayList<>();

        for (Facility facility : sortedFacilties) {
            sortedFacilityIndex.add(facility.getFacilityIndex());
            sortedFFacilityNames.add(facility.getName());
        }


        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (state) {
                    case 0:
                        initRecyclerView(state, sortedFFacilityNames, sortedFacilityIndex);
                        btnSort.setClickable(false);
                        break;
                    case 1:
                        displayController.getSortedEvents(DisplayAll.this);
                        btnSort.setClickable(false);
                        break;
                    case 2:
                        displayController.getSortedUsers(DisplayAll.this);
                        btnSort.setClickable(false);
                        break;
                }
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
