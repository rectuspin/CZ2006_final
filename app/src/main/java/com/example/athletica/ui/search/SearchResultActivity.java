package com.example.athletica.ui.search;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.athletica.R;
import com.example.athletica.data.Search.SearchController;
import java.util.ArrayList;


/*
This boundary class displays the search results corresponding to each entity (users/facilities/events)
 */


public class SearchResultActivity extends AppCompatActivity implements View.OnClickListener {


    private String str;
    private SearchController searchController;


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


        Intent intent = getIntent();


        // Get the search query from search manager
        str = intent.getStringExtra("query");

        searchController=new SearchController(this,str); //running constructor for controller

        // the following three statements display the entities;
        searchController.getFacilities(this);
        searchController.getEvents(this);
        searchController.getUsers(this);

    }







    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    // this is used to initialize the  recycler view
    public void initRecyclerView(ArrayList<String> names, ArrayList<String> index, int id) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView;


        if (id == 0)
            recyclerView = findViewById(R.id.rvFacility);

        else if (id == 1)
            recyclerView = findViewById(R.id.rvEvent);

        else
            recyclerView = findViewById(R.id.rvUsers);


        recyclerView.setLayoutManager(layoutManager);
        SearchResultRecyclerViewAdapter adapter = new SearchResultRecyclerViewAdapter(this, names, index, id);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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