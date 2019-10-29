package com.example.athletica.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.athletica.R;
import com.example.athletica.data.account.LoginRegisterManager;
import com.example.athletica.ui.home.HomeActivity;

import java.util.ArrayList;

public class LoginRegisterActivity extends AppCompatActivity {

    private LoginRegisterManager loginRegisterManager;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        // Initialise a LoginRegisterManager
        loginRegisterManager = new LoginRegisterManager(getApplicationContext());

        // Start the HomeActivity if the user is already logged in
        if (loginRegisterManager.isLoggedIn()) {
            startActivity(new Intent(LoginRegisterActivity.this, HomeActivity.class));
            finish();
        } else {
            // Setup the ViewPager
            viewPager = findViewById(R.id.viewPager);
            LoginRegisterPagerAdapter loginRegisterPagerAdapter = new LoginRegisterPagerAdapter(getSupportFragmentManager());
            loginRegisterPagerAdapter.addFragment(new LoginFragment());
            loginRegisterPagerAdapter.addFragment(new RegisterFragment());
            viewPager.setAdapter(loginRegisterPagerAdapter);
        }

    }


    // Adapter for the ViewPager
    class LoginRegisterPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentList = new ArrayList<>();

        public LoginRegisterPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        void addFragment(Fragment fragment) {
            fragmentList.add(fragment);
        }
    }

}


