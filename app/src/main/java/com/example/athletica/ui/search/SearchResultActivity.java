package com.example.athletica.ui.search;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athletica.R;
import com.example.athletica.data.search.SearchManager;
import com.example.athletica.ui.event.ViewEventActivity;
import com.example.athletica.ui.facility.ViewFacilityActivity;
import com.example.athletica.ui.profile.ViewProfileActivity;

import java.util.ArrayList;


public class SearchResultActivity extends AppCompatActivity implements View.OnClickListener {


    private String str;
    private com.example.athletica.data.search.SearchManager searchManager;

    private ListView rvFacility;
    private ListView rvEvents;
    private ListView rvUsers;
    private Intent intent;


   private TextView facilityText,eventText,userText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);


        facilityText=findViewById(R.id.TextViewFacility);
        eventText=findViewById(R.id.TextViewEvent);
        userText=findViewById(R.id.TextViewUser);

        TextView viewAllText_facilities = findViewById(R.id.button1);
        viewAllText_facilities.setOnClickListener(this);
        TextView viewAllText_events = findViewById(R.id.button2);
        viewAllText_events.setOnClickListener(this);
        TextView viewAllText_Users = findViewById(R.id.button3);
        viewAllText_Users.setOnClickListener(this);


        rvFacility = findViewById(R.id.rvFacility);
        rvEvents = findViewById(R.id.rvEvent);
        rvUsers = findViewById(R.id.rvUsers);


    }


    public void onStart() {
        super.onStart();
        // Get the search query from search manager
        Intent intent = getIntent();
        str = intent.getStringExtra("query");
        searchManager = new SearchManager(this, str); //running constructor for controller
        // the following three statements display the entities;
        searchManager.getFacilities(this);
        searchManager.getEvents(this);
        searchManager.getUsers(this);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    // this is used to initialize the  recycler view


    public void init_ListView(ArrayList<String> names, final ArrayList<String> index, int id) {
        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);


        if (id == 0) {
            if(names.isEmpty())
                facilityText.setText("No Matching Facilities Found");

            rvFacility.setAdapter(arrayAdapter);
            rvFacility.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    intent = new Intent(SearchResultActivity.this, ViewFacilityActivity.class);
                    intent.putExtra("index", index.get(i));
                    startActivity(intent);
                }
            });
        } else if (id == 1) {

            if(names.isEmpty())
                eventText.setText("No Matching Events Found");

            rvEvents.setAdapter(arrayAdapter);
            rvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    intent = new Intent(SearchResultActivity.this, ViewEventActivity.class);
                    intent.putExtra("key", index.get(i));
                    startActivity(intent);
                }
            });
        } else {

            if(names.isEmpty())
                userText.setText("No Matching Users Found");

            rvUsers.setAdapter(arrayAdapter);
            rvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    intent = new Intent(SearchResultActivity.this, ViewProfileActivity.class);
                    intent.putExtra("key", index.get(i));
                    startActivity(intent);
                }
            });
        }

    }

    //Method if the user presses view all button below the entity.

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {

            case R.id.button1:
                intent = new Intent(SearchResultActivity.this, DisplayAll.class);
                intent.putExtra("state", "0");
                startActivity(intent);
                break;

            case R.id.button2:
                intent = new Intent(SearchResultActivity.this, DisplayAll.class);
                intent.putExtra("state", "1");
                startActivity(intent);
                break;

            case R.id.button3:
                intent = new Intent(SearchResultActivity.this, DisplayAll.class);
                intent.putExtra("state", "2");
                startActivity(intent);
                break;


        }
    }

}