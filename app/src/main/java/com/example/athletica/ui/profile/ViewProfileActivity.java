package com.example.athletica.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.athletica.R;
import com.example.athletica.data.account.LoginRegisterManager;
import com.example.athletica.data.profile.ProfileManager;
import com.example.athletica.data.search.SearchManager;
import com.example.athletica.data.user.DataManager;
import com.example.athletica.data.user.UserProfile;
import com.example.athletica.ui.event.ViewEventActivity;
import com.example.athletica.ui.search.SearchResultActivity;
import com.example.athletica.data.DisplayAll.DisplayController;
import com.example.athletica.ui.search.Layout_mainpage;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class ViewProfileActivity extends AppCompatActivity implements View.OnClickListener {


    TextView tvName, tvGenderAge, tvBio, tvInterests, tvFollowers, tvComingUp;
    ExtendedFloatingActionButton btnFollowUpdate;
    ListView rvComingUp;


    //TextView tvName, tvGenderAge, tvBio, tvInterests, tvFollowers, tvUpComing;
    //ExtendedFloatingActionButton btnFollowUpdate;


    private DataManager dataManager;
    private ProfileManager profileManager;
    private LoginRegisterManager loginRegisterManager;
    private String index;
    private UserProfile selectedProfile;
    private UserProfile currentProfile;
    private DisplayController displayController;

    private Intent intent;
    private SearchManager searchManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        tvName = findViewById(R.id.tvName);
        tvGenderAge = findViewById(R.id.tvGenderAge);
        tvBio = findViewById(R.id.tvBio);
        tvInterests = findViewById(R.id.tvInterests);
        tvFollowers = findViewById(R.id.tvFollowers);


        rvComingUp = findViewById(R.id.rvComingUp);
        //tvComingUp = findViewById(R.id.tvComingUp);

        btnFollowUpdate = findViewById(R.id.action_edit);

        index = getIntent().getStringExtra("key");

        if (TextUtils.isEmpty(index)) {
            index = LoginRegisterManager.loggedUser.getId();
        }
        dataManager = new DataManager();
        loginRegisterManager = new LoginRegisterManager();
        profileManager = new ProfileManager(getApplicationContext());
        intent = getIntent();
        searchManager = new SearchManager(this, "");
        getDetails();

        btnFollowUpdate.setOnClickListener(this);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        this.recreate();

        btnFollowUpdate.setOnClickListener(this);
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
            btnFollowUpdate.setIcon(null);
            btnFollowUpdate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add_24px, 0, 0, 0);
            setFollowButton(profile.getId());
        }
        tvName.setText(profile.getName());
        tvGenderAge.setText(profile.getGender() + ", " + calculateAge(profile.getBirthdate()));
        tvBio.setText(profile.getDescription());
        tvFollowers.setText(profile.getFollowers());
        if (tvFollowers.getText().equals(""))
            tvFollowers.setText("0");

        String allInterests = "";
        for (String nextInterest : profile.getSportPreferences()) {
            allInterests += nextInterest + "\n";
        }
        tvInterests.setText(allInterests);

        System.out.println(profile.getId());
        searchManager.getEvents(this, profile.getId());
    }


    private int calculateAge(String dob) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar birthday = Calendar.getInstance();
        try {
            birthday.setTime(simpleDateFormat.parse(dob));
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        Calendar now = Calendar.getInstance();
        int age = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        if (birthday.get(Calendar.MONTH) > now.get(Calendar.MONTH))
            age--;
        else if (birthday.get(Calendar.MONTH) == now.get(Calendar.MONTH))
        {
            if (birthday.get(Calendar.DAY_OF_MONTH) > now.get(Calendar.DAY_OF_MONTH))
                age--;
        }
        return age;
    }

    private void setFollowButton(final String selectedUId) {
        dataManager.getProfileByKey(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                currentProfile = (UserProfile) object;
                currentProfile.setId(profileManager.getCurrentUser());

                for (String nextFollow : currentProfile.getFollows()) {
                    if (nextFollow.equals(selectedUId))
                        btnFollowUpdate.setText("UNFOLLOW");
                    else
                        btnFollowUpdate.setText("FOLLOW");

                }
            }
        }, profileManager.getCurrentUser());
    }

    public void follow(View view) {
        ArrayList<String> newFollows = currentProfile.getFollows();
        String id = selectedProfile.getId();
        String newFollowers;

        if (btnFollowUpdate.getText().equals("FOLLOW")) {
            newFollows.add(selectedProfile.getId());
            if (selectedProfile.getFollowers() != null) {
                newFollowers = String.valueOf(Integer.parseInt(selectedProfile.getFollowers()) + 1);
            } else {
                newFollowers = "1";
            }

            loginRegisterManager.follow(newFollows, id, newFollowers);
            tvFollowers.setText(String.valueOf(Integer.parseInt(tvFollowers.getText().toString()) + 1));
            btnFollowUpdate.setText("UNFOLLOW");
        } else {
            for (int i = 0; i < newFollows.size(); i++) {
                if (newFollows.get(i).equals(id)) {
                    newFollows.remove(i);
                    break;
                }
            }
            newFollowers = String.valueOf(Integer.parseInt(selectedProfile.getFollowers()) - 1);
            loginRegisterManager.follow(newFollows, id, newFollowers);
            tvFollowers.setText(String.valueOf(Integer.parseInt(tvFollowers.getText().toString()) - 1));
            btnFollowUpdate.setText("FOLLOW");
        }

    }

    @Override
    public void onClick(View view) {
        String button = ((ExtendedFloatingActionButton) findViewById(view.getId())).getText().toString();
        if (button.equals("FOLLOW")) {
            follow(null);
        } else if (button.equals("UNFOLLOW")) {
            follow(null);
        } else {
            Intent intent = new Intent(getApplicationContext(), CreateProfileActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Edit profile Selected)", Toast.LENGTH_SHORT).show();
        }
    }

    public void init_ListView(ArrayList<String> names, final ArrayList<String> index) {
        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);

        if(names.isEmpty())
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

        rvComingUp.setAdapter(arrayAdapter);
        rvComingUp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent = new Intent(ViewProfileActivity.this, ViewEventActivity.class);
                intent.putExtra("key", index.get(i));
                arrayAdapter.clear();
                arrayAdapter.notifyDataSetChanged();
                startActivity(intent);
            }
        });
    }
}
