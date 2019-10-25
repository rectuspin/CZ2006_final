package com.example.athletica.ui.profile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athletica.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class ViewProfileActivity extends AppCompatActivity {

    TextView tvName, tvEmail, tvGender, tvAge, tvBio, tvInterest, tvUpComing;
    ExtendedFloatingActionButton btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvGender = findViewById(R.id.tvGender);
        tvAge = findViewById(R.id.tvAge);
        tvBio = findViewById(R.id.tvBio);
        tvInterest = findViewById(R.id.tvUpComing);

    }
}
