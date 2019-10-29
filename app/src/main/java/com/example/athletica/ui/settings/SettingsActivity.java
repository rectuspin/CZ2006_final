package com.example.athletica.ui.settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.example.athletica.R;
import com.example.athletica.data.account.LoginRegisterManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.preferences, new SettingsFragment())
                .commit();
    }


    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }

        @Override
        public boolean onPreferenceTreeClick(androidx.preference.Preference preference) {

            switch (preference.getKey()) {
                case "change_password":
                    getActivity().startActivity(new Intent(getContext(), ChangePasswordActivity.class));

                case "logout":
                    LoginRegisterManager loginRegisterManager = new LoginRegisterManager(getContext());
                    if (loginRegisterManager.isLoggedIn()) {
                        loginRegisterManager.logOut();
                        getActivity().finish();
                    }
                    return true;

                default:
                    return super.onPreferenceTreeClick(preference);
            }
        }
    }

}

