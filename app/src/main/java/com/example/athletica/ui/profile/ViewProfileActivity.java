package com.example.athletica.ui.profile;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athletica.R;
import com.example.athletica.data.account.LoginRegisterManager;
import com.example.athletica.data.profile.ProfileManager;
import com.example.athletica.data.user.DataManager;
import com.example.athletica.data.user.UserProfile;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class ViewProfileActivity extends AppCompatActivity {

    TextView tvName, tvEmail, tvGender, tvBio, tvInterest, tvUpComing;
    ExtendedFloatingActionButton btnEdit;
    private DataManager dataManager;
    private ProfileManager profileManager;
    private String index;
    private UserProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvGender = findViewById(R.id.tvGender);
        tvBio = findViewById(R.id.tvBio);
        tvInterest = findViewById(R.id.tvUpComing);

        index = getIntent().getStringExtra("key");

        if(TextUtils.isEmpty(index)){
            index = LoginRegisterManager.loggedUser.getId();
        }
        dataManager = new DataManager();
        profileManager = new ProfileManager(getApplicationContext());
        getDetails();
    }

    private void getDetails() {
        dataManager.getProfileByKey(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                profile = (UserProfile) object;
                profile.setId(index);
                populate(profile);
            }
        }, index);
    }

    private void populate(UserProfile profile) {
        tvName.setText(profile.getName());
        tvGender.setText(profile.getGender());
        tvBio.setText(profile.getDescription());

        String allInterests = "";
        for (String nextInterest : profile.getSportPreferences()) {
            allInterests += nextInterest + "\n";
        }
        tvInterest.setText(allInterests);
    }
}
