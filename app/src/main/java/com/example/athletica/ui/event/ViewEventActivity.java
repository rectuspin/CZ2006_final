package com.example.athletica.ui.event;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athletica.R;
import com.example.athletica.data.account.LoginRegisterManager;
import com.example.athletica.data.event.Event;
import com.example.athletica.data.event.EventManager;
import com.example.athletica.data.user.DataManager;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class ViewEventActivity extends AppCompatActivity {

    private TextView tvEventName, tvEventLocation, tvEventStartDate, tvEventEndDate, tvEventCapacity, tvEventPrice, tvEventOrganiser;

    private ExtendedFloatingActionButton btnJoinEvent;
    private Event event;

    private DataManager dataManager;
    private EventManager eventManager;
    private String index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        tvEventName = findViewById(R.id.event_name);
        tvEventLocation = findViewById(R.id.event_location);
        tvEventStartDate = findViewById(R.id.start_date);
        tvEventEndDate = findViewById(R.id.end_date);
        tvEventCapacity = findViewById(R.id.max_people);
        tvEventPrice = findViewById(R.id.event_price);
        tvEventOrganiser = findViewById(R.id.event_organiser);
        btnJoinEvent = findViewById(R.id.action_join_event);

        dataManager = new DataManager();
        eventManager = new EventManager(getApplicationContext());

        index = getIntent().getStringExtra("key");

        getDetails();


        btnJoinEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnJoinEvent.getText().equals("Withdraw")){
                    eventManager.withdrawFromEvent(event,LoginRegisterManager.loggedUser);
                }
                else{
                    eventManager.joinEvent(event, LoginRegisterManager.loggedUser);
                }
                setButton();
                //Toast.makeText(ViewEventActivity.this, event.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setButton() {
        if (!LoginRegisterManager.loggedUser.canJoin(event.getId())) {
            btnJoinEvent.setClickable(true);
            btnJoinEvent.setText("Withdraw");
        } else if (!event.canBeJoined()) {
            btnJoinEvent.setClickable(false);
            btnJoinEvent.setText("Event is full");
        }
        else{
            btnJoinEvent.setClickable(true);
            btnJoinEvent.setText("Join Event");
        }
        btnJoinEvent.setVisibility(Button.VISIBLE);
    }


    // Retrieve the event's details
    private void getDetails() {
        dataManager.getEventByKey(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                event = (Event) object;
                event.setId(index);
                populate(event);
                setButton();
            }
        }, index);
    }


    // Populate the textviews with the event's details
    private void populate(Event event) {
        tvEventName.setText(event.getName());
        tvEventLocation.setText(event.getLocation());
        tvEventStartDate.setText(event.getStartDate());
        tvEventEndDate.setText(event.getEndDate());
        tvEventCapacity.setText(String.valueOf(event.getMaxParticipants()));
        tvEventPrice.setText(String.valueOf(event.getPrice()));
        tvEventOrganiser.setText(event.getCreatedBy());
    }

}
