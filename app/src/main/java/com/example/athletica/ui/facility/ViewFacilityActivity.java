package com.example.athletica.ui.facility;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athletica.R;
import com.example.athletica.data.user.DataManager;
import com.example.athletica.ui.maps.MapsActivity;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class ViewFacilityActivity extends AppCompatActivity {

    private ImageView imageView, mapBtn;
    private RatingBar ratingBar;
    private TextView tvFacilityName, tvFacilityOffered, tvWebsiteLink, tvAddress;

    private int[] images;
    private Map<String, String> data;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_facility);

        imageView = findViewById(R.id.facility_imageView);
        mapBtn = findViewById(R.id.map_button);
        ratingBar = findViewById(R.id.facility_rating_bar);
        tvFacilityName = findViewById(R.id.facility_name);
        tvFacilityOffered = findViewById(R.id.facilities_offered);
        tvWebsiteLink = findViewById(R.id.website);
        tvAddress = findViewById(R.id.facility_address);

        dataManager = new DataManager();
        images = new int[]{R.raw.i0, R.raw.i1, R.raw.i2, R.raw.i3, R.raw.i4, R.raw.i5, R.raw.i6, R.raw.i7, R.raw.i8, R.raw.i9, R.raw.i10};


        //getting facility details
        String index = getIntent().getStringExtra("index"); // gets the index passed in the previous intent from Search_Results refer to RecyclerViewAdapter.java
        data = dataManager.readIndex(this, index); // fetched the record from the search function
        imageView.setImageResource(images[Integer.parseInt(index) % 11]);
        mapBtn.setImageResource(R.drawable.mapicon);
        tvFacilityName.setText(data.get("name"));
        String temp = data.get("Facilities");
        tvFacilityOffered.setText(temp.replace("/", "  "));
        tvWebsiteLink.setText(data.get("website"));

        int rating = 3; // get rating from DB
        ratingBar.setRating(rating);


        //get address from lat long
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        double lat = Double.parseDouble(data.get("lat"));
        double lng = Double.parseDouble(data.get("long"));
        try {
            String addr = geocoder.getFromLocation(lat, lng, 1).get(0).getAddressLine(0);
            tvAddress.setText(addr);
        } catch (IOException e) {
            Log.d("Address error", "address error");
        }

        // Starts the MapsActivity with the facility's location data
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat = Double.parseDouble(data.get("lat"));
                double lng = Double.parseDouble(data.get("long"));

                Log.wtf("map running", data.get("lat") + "   " + data.get("long"));

                Intent intent = new Intent(ViewFacilityActivity.this, MapsActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                startActivity(intent);
            }
        });

    }

}
