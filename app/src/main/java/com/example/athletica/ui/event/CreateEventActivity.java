package com.example.athletica.ui.event;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athletica.R;
import com.example.athletica.data.event.EventManager;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener {

    private Calendar startCalendar, endCalendar;
    private String startDate, endDate;
    private String displayDateFormat = "dd/MM/yyyy HH:mm";
    private SimpleDateFormat sdf = new SimpleDateFormat(displayDateFormat, Locale.getDefault());
    private EditText etName, etDescription, etDisplne, etLocation, etMaxParticipants, etStartDate, etEndDate, etPrice;
    private CheckBox cbIsPaid;
    private EventManager eventManager;
    private ExtendedFloatingActionButton btnSubmit;
    private TextInputLayout priceTextInputLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        eventManager = new EventManager(this.getApplicationContext());

        etName = findViewById(R.id.et_event_name);
        etDescription = findViewById(R.id.et_event_description);
        etDisplne = findViewById(R.id.et_event_discipline);
        etLocation = findViewById(R.id.et_event_location);
        etMaxParticipants = findViewById(R.id.et_event_max_participants);
        etStartDate = findViewById(R.id.et_event_start_date);
        etEndDate = findViewById(R.id.et_event_end_date);
        cbIsPaid = findViewById(R.id.cb_paid_event);
        etPrice = findViewById(R.id.et_price);
        priceTextInputLayout = findViewById(R.id.input_layout_price);
        btnSubmit = findViewById(R.id.action_create_event);

        // Show the price edit text only when paid event check box is checked
        cbIsPaid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    priceTextInputLayout.setVisibility(View.VISIBLE);
                else {
                    priceTextInputLayout.setVisibility(View.INVISIBLE);
                    hideKeyboard(etPrice);
                }
            }
        });

        etStartDate.setOnClickListener(this);
        etEndDate.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_event_start_date:
                datePicker(startCalendar, etStartDate);
                endCalendar = (Calendar) startCalendar.clone();
                break;
            case R.id.et_event_end_date:
                datePicker(endCalendar, etEndDate);
                break;
            case R.id.action_create_event:
                saveData();
                break;

        }

    }


    private void datePicker(final Calendar calendar, final EditText et) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateEventActivity.this, R.style.DialogTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        timePicker(calendar, et);

                    }
                }, year, month, day);

        // Restrict selection of start date earlier than current date and end date earlier than start date
        if (et == etStartDate) {
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        } else if (et == etEndDate) {
            try {
                Date startDate = sdf.parse(etStartDate.getText().toString());
                datePickerDialog.getDatePicker().setMinDate(startDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        datePickerDialog.show();
    }


    private void timePicker(final Calendar calendar, final EditText et) {
        int hour;
        int minute;

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEventActivity.this, R.style.DialogTheme,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        // Get date & time from DatePicker and TimePicker and set to the EditText
                        et.setText(sdf.format(calendar.getTime()));
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }


    private void saveData() {
        Date currentDate = Calendar.getInstance().getTime();
        String name = etName.getText().toString();
        String discipline = etDisplne.getText().toString();
        String description = etDescription.getText().toString();
        String location = etLocation.getText().toString();
        String startDate = etStartDate.getText().toString();
        String endDate = etEndDate.getText().toString();
        int maxParticipant;
        double price;


        // Set max number of participants to 0 if not indicated
        if (etMaxParticipants.getText().toString().equals("")) {
            maxParticipant = 0;
        } else {
            maxParticipant = Integer.parseInt(etMaxParticipants.getText().toString());
        }

        // Set price to 0 if not indicated
        if (etPrice.getText().toString().equals("")) {
            price = 0d;
        } else {
            price = Double.parseDouble(etPrice.getText().toString());
        }

        // Validates the event details with EventManager and save the event into the database
        if (eventManager.validateDetails(name, discipline, description, location, maxParticipant, price, startDate, endDate)) {
            Toast.makeText(this, "Details valid", Toast.LENGTH_SHORT).show();
            if (eventManager.saveEvent(name, discipline, description, location, maxParticipant, price, startDate, endDate)) {
                finish();
            }
        }

    }


    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}
