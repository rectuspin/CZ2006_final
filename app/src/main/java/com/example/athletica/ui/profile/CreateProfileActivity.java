package com.example.athletica.ui.profile;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athletica.R;
import com.example.athletica.data.user.DataManager;
import com.example.athletica.data.user.UserProfile;
import com.example.athletica.data.profile.ProfileManager;
import com.example.athletica.data.account.LoginRegisterManager;
import com.example.athletica.ui.home.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateProfileActivity extends AppCompatActivity {

    public static String databaseBirthdayFormat = "yyyy-MM-dd";
    private final Calendar myCalendar = Calendar.getInstance();
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private RadioGroup sexRadioGroup;
    private RadioButton sexRadioButton;
    private EditText birthdayEditText, nameEditText, descriptionEditText, addNewSportEditText;
    private ViewFlipper viewFlipper;
    private ListView sportsListView, followsListView;
    private ArrayList<String> sportsArray = new ArrayList<String>();
    private ArrayList<String> followsArray = new ArrayList<String>();
    private CustomArrayAdapter sportsArrayAdapter, followsArrayAdapter;
    private TextView charactersLeftTextView;
    private Date currentDate = new Date();
    private LoginRegisterManager loginRegisterManager;
    private String validatedName, validatedBirthday, followers;

    private DataManager dataManager;
    private ProfileManager profileManager;
    private String index;
    private UserProfile profile;
    private boolean editing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        loginRegisterManager = new LoginRegisterManager(this.getApplicationContext());
        currentUser = firebaseAuth.getCurrentUser();

        sportsArrayAdapter = new CustomArrayAdapter(CreateProfileActivity.this, sportsArray);
        followsArrayAdapter = new CustomArrayAdapter(CreateProfileActivity.this, followsArray);

        addNewSportEditText = findViewById(R.id.newSportEditText);
        sportsListView = findViewById(R.id.sportsListView);
        sexRadioGroup = findViewById(R.id.sexRadioGroup);
        nameEditText = findViewById(R.id.nameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        charactersLeftTextView = findViewById(R.id.leftCharactersTextView);
        birthdayEditText = findViewById(R.id.birthdayEditText);
        viewFlipper = findViewById(R.id.viewFlipper);
        setUpFlipperAnimation();

        setUpListeners();
        sportsListView.setAdapter(sportsArrayAdapter);
        sportsListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        followsArray.add("");
        oldUser();
    }

    public void oldUser() {
        index = currentUser.getUid();
        dataManager = new DataManager();
        profileManager = new ProfileManager(getApplicationContext());

        dataManager.getProfileByKey(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                try {
                    profile = (UserProfile) object;
                    profile.setId(index);
                    populate(profile);
                    editing = true;
                } catch (NullPointerException e) {
                    editing = false;
                }
            }
        }, index);
    }

    public void populate(UserProfile profile)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(databaseBirthdayFormat, Locale.getDefault());
        nameEditText.setText(profile.getName());
        descriptionEditText.setText(profile.getDescription());
        if (profile.getGender().equals("female"))
        {
            ((RadioButton) findViewById(R.id.femaleRadioButton)).setChecked(true);
        }
        birthdayEditText.setText(profile.getBirthdate());
        try{
            Date birthdate = sdf.parse(profile.getBirthdate());
            myCalendar.setTime(birthdate);
        }
        catch(ParseException e){
            e.printStackTrace();
        }
        followers = profile.getFollowers();

        for (String nextSport: profile.getSportPreferences())
        {
            if(!nextSport.equals("N/A")){
                addNewSportEditText.setText(nextSport);
                addNewPreference(null);
            }
        }
    }

    public void setUpFlipperAnimation() {

        Animation leftIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation rightOut = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        viewFlipper.setInAnimation(leftIn);
        viewFlipper.setOutAnimation(rightOut);
    }

    public boolean saveData() {
        String description = descriptionEditText.getText().toString();
        sexRadioButton = findViewById(sexRadioGroup.getCheckedRadioButtonId());
        String sex = sexRadioButton.getText().toString().toLowerCase();

        if (editing)
            loginRegisterManager.updateUserProfile(currentUser.getUid(), validatedName, validatedBirthday, sex, description, sportsArray, followsArray, followers);
        else
            loginRegisterManager.saveUserProfile(currentUser.getUid(), validatedName, validatedBirthday, sex, description, sportsArray, followsArray, followers);

        return true;
    }

    public void next(View view) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(databaseBirthdayFormat, Locale.getDefault());

        Date current = null;
        Date birthdayDate = null;
        try {
            current = simpleDateFormat.parse(simpleDateFormat.format(currentDate));
            birthdayDate = simpleDateFormat.parse(simpleDateFormat.format(myCalendar.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (loginRegisterManager.validateProfileDetails(current, birthdayDate, nameEditText.getText().toString())) {
            viewFlipper.showNext();
            validatedBirthday = simpleDateFormat.format(myCalendar.getTime());
            validatedName = nameEditText.getText().toString();
        }
    }


    public void submit(View view) {
        if (saveData()) {
            Toast.makeText(this, "Profile created/updated successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }
    }

    private void updateBirthday() {
        String displayFormat = "yyyy-MM-dd";
        SimpleDateFormat displayDateFormat = new SimpleDateFormat(displayFormat, Locale.getDefault());

        birthdayEditText.setText(displayDateFormat.format(myCalendar.getTime()));
    }

    public void addNewPreference(View view) {
        String newSportString = addNewSportEditText.getText().toString();
        if (!newSportString.equals("")) {
            sportsArray.add(newSportString);
            sportsArrayAdapter.notifyDataSetChanged();
            addNewSportEditText.setText("");
        } else {
            addNewSportEditText.requestFocus();
        }

    }


    private void setUpListeners() {
        birthdayEditText.setFocusable(false);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateBirthday();
            }

        };

        birthdayEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateProfileActivity.this, R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        descriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                charactersLeftTextView.setText("Characters left " + (250 - s.toString().length()) + "/250");
            }
        });
    }

}
