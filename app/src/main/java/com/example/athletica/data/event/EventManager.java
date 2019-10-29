package com.example.athletica.data.event;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.athletica.R;
import com.example.athletica.data.account.LoginRegisterManager;
import com.example.athletica.data.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventManager {
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private Context context;
    private String displayDateFormat = "dd/MM/yyyy HH:mm";
    private SimpleDateFormat sdf = new SimpleDateFormat(displayDateFormat, Locale.getDefault());

    public EventManager(Context context) {
        this.context = context;
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public boolean validateDetails(String name, String discipline, String description, String location, int maxParticipants, double price, String dateStart, String dateEnd) {
        Date currentDate = new Date();
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdf.parse(dateStart);
            endDate = sdf.parse(dateEnd);
            currentDate = sdf.parse(sdf.format(currentDate));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Problem with dates at the beginning!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(context, context.getString(R.string.empty_name), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(discipline)) {
            Toast.makeText(context, context.getString(R.string.empty_discipline), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(location)) {
            Toast.makeText(context, context.getString(R.string.empty_discipline), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (name.length() > 50) {
            Toast.makeText(context, "Name too long!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (description.length() > 150) {
            Toast.makeText(context, "Description too long!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (startDate.after(endDate)) {
            Toast.makeText(context, "Event cannot start after it is finished", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (startDate.before(currentDate) || startDate.equals(currentDate)) {
            Toast.makeText(context, "Problem with dates! start", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (endDate.before(currentDate) || endDate.equals(currentDate)) {
            Toast.makeText(context, "Problem with dates! end", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (price < 0) {
            Toast.makeText(context, "Price cannot be negative!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (maxParticipants < 0) {
            Toast.makeText(context, "Maximium participants number cannot be negative", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    // Creates a new event and save into the dartabase
    public boolean saveEvent(String name, String discipline, String description, String location, int maxParticipants, double price, String startDate, String endDate) {
        // Retrieve the ket for event_info
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("events_info");
        String key = firebaseDatabase.getReference().child("events_info").push().getKey();

        // Initialise a new event object
        Event event = new Event(name, location, startDate, endDate, description, discipline, LoginRegisterManager.loggedUser.getProfile().getName(), maxParticipants, price, 0);

        // Adds the new event into the database
        databaseReference.child(key).setValue(event);
        Toast.makeText(context, "Event saved", Toast.LENGTH_SHORT).show();
        return true;
    }

    public boolean joinEvent(Event event, User user) {
        if (event.canBeJoined() && user.canJoin(event.getId())) {
            event.addNewParticipant();
            user.addEventToList(event.getId());
            Toast.makeText(context, "Participant added!", Toast.LENGTH_SHORT).show();
            Log.i("Join event", "New user joined event!");
            return true;
        } else {
            return false;
        }
    }
}
