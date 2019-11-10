package com.example.athletica.ui.facility;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.athletica.R;
import com.example.athletica.data.facility.Comments;
import com.example.athletica.data.facility.Facility;
import com.example.athletica.data.facility.FacilityManager;
import com.example.athletica.data.facility.Ratings;
import com.example.athletica.data.user.DataManager;
import com.example.athletica.ui.maps.MapsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class ViewFacilityActivity extends AppCompatActivity {


    float ratingAvg;
    private String facilityIndex;
    private ImageView imageView, mapBtn;
    private RatingBar ratingRatingBar;
    private TextView tvFacilityName, tvFacilityOffered, tvWebsiteLink, tvAddress;
    private int[] images;
    private Facility facility;
    private FacilityManager facilityManager;
    private DataManager dataManager;
    private Button submitButton;
    private TextView ratingDisplayTextView;
    private TextView currentRating;
    private EditText addcomment;
    private Button sendComment;
    private ListView listViewComments;
    private List<Comments> commentsList;

    private List<Ratings> ratingsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_facility);

        imageView = findViewById(R.id.facility_imageView);
        mapBtn = findViewById(R.id.map_button);
        ratingRatingBar = findViewById(R.id.facility_rating_bar);
        tvFacilityName = findViewById(R.id.facility_name);
        tvFacilityOffered = findViewById(R.id.facilities_offered);
        tvWebsiteLink = findViewById(R.id.website);
        tvAddress = findViewById(R.id.facility_address);


        submitButton = findViewById(R.id.submit_button);
        ratingDisplayTextView = findViewById(R.id.rating_display_text_View);
        currentRating = findViewById(R.id.current_rating);

        listViewComments = findViewById(R.id.list_view_comment);
        addcomment = findViewById(R.id.add_comment);
        sendComment = findViewById(R.id.send);
        commentsList = new ArrayList<>();
        ratingsList=new ArrayList<>();

        //getting data and methods

        dataManager = new DataManager();
        facilityIndex = getIntent().getStringExtra("index");
        facility = dataManager.readIndex(this, facilityIndex);
        facilityManager = new FacilityManager(this, facilityIndex);

        // setting textViews

        tvFacilityName.setText(facility.getName());
        tvFacilityOffered.setText(facility.getFacilities());
        tvWebsiteLink.setText(facility.getWebsite());
        tvAddress.setText(facility.getAddress(this));
        images = new int[]{R.raw.i0, R.raw.i1, R.raw.i2, R.raw.i3, R.raw.i4, R.raw.i5, R.raw.i6, R.raw.i7, R.raw.i8, R.raw.i9, R.raw.i10};
        imageView.setImageResource(images[Integer.parseInt(facilityIndex) % 11]);
        mapBtn.setImageResource(R.drawable.mapicon);


        // Starts the MapsActivity with the facility's location data
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat = facility.getLat();
                double lng = facility.getLong();

                Intent intent = new Intent(ViewFacilityActivity.this, MapsActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                startActivity(intent);
            }
        });


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addRatings();
                float submitted_rating = ratingRatingBar.getRating();
                facilityManager.addRating(submitted_rating);
//                ratingDisplayTextView.setText("Your rating is :" + submitted_rating);
                ratingDisplayTextView.setText("Rating submitted!");
            }
        });

        //add comment
        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Boolean result = facilityManager.addComments(addcomment.getText().toString());
                if (result)
                    addcomment.setText("");
                else
                    Toast.makeText(ViewFacilityActivity.this, "Please type the content", Toast.LENGTH_SHORT);
            }
        });
    }

    //View comments
    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference Comments_DB_Reference = facility.getComments_DB_Reference();
        DatabaseReference Ratings_DB_Reference = facility.getRatings_DB_Ref();

        DatabaseReference rat = FirebaseDatabase.getInstance().getReference("facility_ratings").child(facilityIndex);
        rat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ratingsList.clear();
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                    Ratings rating = Snapshot.getValue(Ratings.class);
                   ratingsList.add(rating);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        Comments_DB_Reference.child(facilityIndex).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentsList.clear();
                for (DataSnapshot commentsSnapshot : dataSnapshot.getChildren()) {
                    Comments comment = commentsSnapshot.getValue(Comments.class);
                    commentsList.add(comment);

                }

                CommentAdapter adapter = new CommentAdapter(ViewFacilityActivity.this, commentsList,ratingsList);

                listViewComments.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Ratings_DB_Reference.child(facilityIndex).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                float sum = 0;
                long numChild = 0;

                for (DataSnapshot ratingsSnapshot : dataSnapshot.getChildren()) {
                    Ratings rating = ratingsSnapshot.getValue(Ratings.class);
                    sum += rating.getRatingContent();
                    numChild = dataSnapshot.getChildrenCount();

                }
                ratingAvg = sum / numChild;
                String rat = String.valueOf(ratingAvg);
//                currentRating.setText("Current rating of this facility is " + rat);
                ratingRatingBar.setRating(ratingAvg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
