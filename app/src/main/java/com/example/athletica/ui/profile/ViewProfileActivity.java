package com.example.athletica.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athletica.R;
import com.example.athletica.data.account.LoginRegisterManager;
import com.example.athletica.data.profile.ProfileManager;
import com.example.athletica.data.user.DataManager;
import com.example.athletica.data.user.UserProfile;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class ViewProfileActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvName, tvGenderAge, tvBio, tvInterests, tvFollowers, tvUpComing;
    ExtendedFloatingActionButton btnEdit;
    //Button tvFollow;

    private DataManager dataManager;
    private ProfileManager profileManager;
    private LoginRegisterManager loginRegisterManager;
    private String index;
    private UserProfile selectedProfile;
    private UserProfile currentProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        tvName = findViewById(R.id.tvName);
        tvGenderAge = findViewById(R.id.tvGenderAge);
        tvBio = findViewById(R.id.tvBio);
        tvInterests = findViewById(R.id.tvInterests);
        tvFollowers = findViewById(R.id.tvFollowers);
        //tvFollow = findViewById(R.id.tvFollow);
        btnEdit = findViewById(R.id.action_edit);

        index = getIntent().getStringExtra("key");

        if (TextUtils.isEmpty(index)) {
            index = LoginRegisterManager.loggedUser.getId();
        }
        dataManager = new DataManager();
        loginRegisterManager = new LoginRegisterManager();
        profileManager = new ProfileManager(getApplicationContext());
        getDetails();

        btnEdit.setOnClickListener(this);
    }

    private void getDetails() {
        dataManager.getProfileByKey(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                selectedProfile = (UserProfile) object;
                selectedProfile.setId(index);
                populate(selectedProfile);
            }
        }, index);
    }

    private void populate(UserProfile profile) {
        if (!index.equals(LoginRegisterManager.loggedUser.getId())) {
            btnEdit.setIcon(null);
            btnEdit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add_24px, 0, 0, 0);
            setFollowButton(profile.getId());
            //btnEdit.setText("Follow");
        }
        tvName.setText(profile.getName());
        tvGenderAge.setText(profile.getGender());
        tvBio.setText(profile.getDescription());
        tvFollowers.setText(profile.getFollowers());
        if (tvFollowers.getText().equals(""))
            tvFollowers.setText("0");

        String allInterests = "";
        for (String nextInterest : profile.getSportPreferences()) {
            allInterests += nextInterest + "\n";
        }
        tvInterests.setText(allInterests);
    }

    private void setFollowButton(final String selectedUId)
    {
        dataManager.getProfileByKey(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                currentProfile = (UserProfile) object;
                currentProfile.setId(profileManager.getCurrentUser());

                for (String nextFollow: currentProfile.getFollows()) {
                    if(nextFollow.equals(selectedUId))
                        btnEdit.setText("UNFOLLOW");
                    else
                        btnEdit.setText("FOLLOW");

                }
            }
        }, profileManager.getCurrentUser());
    }
    public void follow(View view)
    {
        ArrayList<String> newFollows = currentProfile.getFollows();
        String id = selectedProfile.getId();
        String newFollowers;

        if (btnEdit.getText().equals("FOLLOW"))
        {
            newFollows.add(selectedProfile.getId());
            if (selectedProfile.getFollowers() != null) {
                newFollowers = String.valueOf(Integer.parseInt(selectedProfile.getFollowers()) + 1);
            }
            else {
                newFollowers = "1";
            }

            loginRegisterManager.follow(newFollows, id, newFollowers);
            tvFollowers.setText(String.valueOf(Integer.parseInt(tvFollowers.getText().toString()) + 1));
            btnEdit.setText("UNFOLLOW");
        }
        else
        {
            for (int i = 0; i < newFollows.size(); i++)
            {
                if (newFollows.get(i).equals(id))
                {
                    newFollows.remove(i);
                    break;
                }
            }
            newFollowers = String.valueOf(Integer.parseInt(selectedProfile.getFollowers()) - 1);
            loginRegisterManager.follow(newFollows, id, newFollowers);
            tvFollowers.setText(String.valueOf(Integer.parseInt(tvFollowers.getText().toString()) - 1));
            btnEdit.setText("FOLLOW");
        }

    }
    @Override
    public void onClick(View view) {
        String button  = ((ExtendedFloatingActionButton)findViewById(view.getId())).getText().toString();
        if(button.equals("FOLLOW")) {
            follow(null);
        }
        else if (button.equals("UNFOLLOW")) {
            follow(null);
        }
        else {
            Intent intent = new Intent(getApplicationContext(), CreateProfileActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Edit profile Selected)", Toast.LENGTH_SHORT).show();
        }
    }
}
