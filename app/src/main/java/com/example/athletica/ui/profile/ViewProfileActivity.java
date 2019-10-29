package com.example.athletica.ui.profile;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athletica.R;
import com.example.athletica.data.account.LoginRegisterManager;
import com.example.athletica.data.profile.ProfileManager;
import com.example.athletica.data.user.DataManager;
import com.example.athletica.data.user.UserProfile;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class ViewProfileActivity extends AppCompatActivity implements View.OnClickListener{

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
        btnEdit = findViewById(R.id.action_edit);

        index = getIntent().getStringExtra("key");

        if(TextUtils.isEmpty(index)){
            index = LoginRegisterManager.loggedUser.getId();
        }
        dataManager = new DataManager();
        profileManager = new ProfileManager(getApplicationContext());
        getDetails();

        btnEdit.setOnClickListener(this);
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
        if(!index.equals(LoginRegisterManager.loggedUser.getId())){
            btnEdit.setIcon(null);
            btnEdit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add_24px, 0, 0, 0);
            btnEdit.setText("Follow");
        }
        tvName.setText(profile.getName());
        tvGender.setText(profile.getGender());
        tvBio.setText(profile.getDescription());

        String allInterests = "";
        for (String nextInterest : profile.getSportPreferences()) {
            allInterests += nextInterest + "\n";
        }
        tvInterest.setText(allInterests);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.action_edit:
                Toast.makeText(this,"Edit profile/Follow (will be implemented soon)", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
