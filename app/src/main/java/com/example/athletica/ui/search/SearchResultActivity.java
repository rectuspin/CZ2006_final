package com.example.athletica.ui.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.athletica.R;
import com.example.athletica.data.user.DataManager;

import java.util.ArrayList;
import java.util.Map;

public class SearchResultActivity extends AppCompatActivity implements View.OnClickListener {


    private ArrayList<String> facilityName = new ArrayList<>(); // names of all the facilities are stored in this list
    private ArrayList<String> facilityIds = new ArrayList<>();

    private ArrayList<String> eventsName = new ArrayList<String>();// unique indexes of all the records are stored in this list
    private ArrayList<String> eventIds = new ArrayList<>();

    private ArrayList<String> userName = new ArrayList<String>();// unique indexes of all the records are stored in this list
    private ArrayList<String> userIds = new ArrayList<>();


    //private ArrayList<Event> eventsList=new ArrayList<>();      // similar to facciities, details of events have to be stored, this has not been implemented yet.
    private ArrayList<Map> facilityMap;
    private ArrayList<Map> eventMap;
    private ArrayList<Map> userMap;

    private String str;
    private DataManager dataManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        TextView viewAllText_facilities = findViewById(R.id.button1);
        viewAllText_facilities.setOnClickListener(this);
        TextView viewAllText_events = findViewById(R.id.button2);
        viewAllText_events.setOnClickListener(this);
        TextView viewAllText_Users=findViewById(R.id.button3);
        viewAllText_Users.setOnClickListener(this);

        dataManager = new DataManager();

        // Get the search query from search manager
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            str = intent.getStringExtra(SearchManager.QUERY);
        }

        str = intent.getStringExtra("query");

        getFacilities();
        getEvents();
        getUsers();
    }


    private void getFacilities() {
        facilityMap = (ArrayList<Map>) dataManager.readDataAll(this, str);
        Log.v("running",facilityMap.toString());
        init_1(facilityMap);
    }


    private void getEvents() {
        dataManager.getEventKeys(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                eventMap = ((ArrayList<Map>) object);
                System.out.println(eventMap.size());
                init_2(eventMap);
            }
        }, 20, str);
    }

    private void getUsers() {
        dataManager.getAllUsers(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                userMap = ((ArrayList<Map>) object);
                init_3(userMap);
            }
        }, str);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    // this is used to initialize the first recycler view (to display the facilities)

    private void init_1(ArrayList<Map> sample) {
        for (Map<String, String> map : sample) {
            String str2 = map.get("name");  //
            // parsing facilities and index's in separate lis
            String i = map.get("index");
            facilityName.add(str2);
            facilityIds.add(i);
        }
        initRecyclerView(facilityName, facilityIds, 0);
    }

    // this is used to initialize the first recycler view (to display the Events)

    private void init_2(ArrayList<Map> sample) {
        for (Map<String, String> map : sample) {
            String str1 = map.get("key");
            String str2 = map.get("name");
            eventIds.add(str1);
            eventsName.add(str2);
        }
        initRecyclerView(eventsName, eventIds, 1);
    }


    private void init_3(ArrayList<Map> sample) {
        for (Map<String, String> map : sample) {
            String str1 = map.get("key");
            String str2 = map.get("name");
            userIds.add(str1);
            userName.add(str2);
        }
        initRecyclerView(userName, userIds, 2);
    }


    private void initRecyclerView(ArrayList<String> names, ArrayList<String> index, int id) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView;


        if (id == 2)
            recyclerView = findViewById(R.id.rvUsers);
        else if (id == 0)
            recyclerView = findViewById(R.id.rvFacility);
        else
            recyclerView = findViewById(R.id.rvEvent);

        recyclerView.setLayoutManager(layoutManager);
        SearchResultRecyclerViewAdapter adapter = new SearchResultRecyclerViewAdapter(this, names, index, id);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

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