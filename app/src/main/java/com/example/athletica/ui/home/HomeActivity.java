package com.example.athletica.ui.home;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.athletica.R;
import com.example.athletica.data.event.Event;
import com.example.athletica.ui.event.CreateEventActivity;
import com.example.athletica.ui.profile.ViewProfileActivity;
import com.example.athletica.ui.search.SearchResultActivity;
import com.example.athletica.ui.settings.SettingsActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private SearchView searchView;
    private ImageButton btnMenu;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private HomeEventRecyclerViewAdapter homeEventRecyclerViewAdapter;
    private List<Event> eventList = new ArrayList<Event>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        searchView = findViewById(R.id.action_search);
        btnMenu = findViewById(R.id.action_menu);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view_home);
        recyclerView = findViewById(R.id.rv_home);


        setupSearchView();
        setupNavigationView();
        setupRecyclerView();

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.END);
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (this.drawer.isDrawerOpen(GravityCompat.END))
            this.drawer.closeDrawer(GravityCompat.END);
        else
            super.onBackPressed();
    }


    @Override
    protected void onResume() {
        super.onResume();

        // Uncheck navigation drawer menu item
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            if (navigationView.getMenu().getItem(i).isChecked())
                navigationView.getMenu().getItem(i).setChecked(false);
        }

    }


    // Setup the Search View
    public void setupSearchView() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView.setIconifiedByDefault(false);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(HomeActivity.this, SearchResultActivity.class)));
        searchView.onActionViewCollapsed();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                // Pass the search query to SearchResultActivity to display the results
                Intent intent = new Intent(HomeActivity.this, SearchResultActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    // Setup the navigation drawer
    private void setupNavigationView() {
        // Setting NavigationItemSelectedListener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Check which item was being clicked and perform the appropriate action
                switch (item.getItemId()) {
                    case R.id.nav_create_event:
                        item.setChecked(false);
                        startActivity(new Intent(HomeActivity.this, CreateEventActivity.class));
                        drawer.closeDrawers();
                        return true;

                    case R.id.nav_view_profile:
                        item.setChecked(false);
                        startActivity(new Intent(HomeActivity.this, ViewProfileActivity.class));
                        drawer.closeDrawers();
                        return true;

                    case R.id.nav_settings:
                        item.setChecked(false);
                        startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                        drawer.closeDrawers();
                        return true;

                    default:
                        return false;

                }
            }
        });


        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                // Uncheck navigation drawer menu item
                for (int i = 0; i < navigationView.getMenu().size(); i++)
                    if (navigationView.getMenu().getItem(i).isChecked())
                        navigationView.getMenu().getItem(i).setChecked(false);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }


    // Setup the recyclerView
    private void setupRecyclerView() {
        homeEventRecyclerViewAdapter = new HomeEventRecyclerViewAdapter(eventList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(homeEventRecyclerViewAdapter);

        loadEventData();

        homeEventRecyclerViewAdapter.notifyDataSetChanged();

    }


    private void loadEventData() {

    }

}
