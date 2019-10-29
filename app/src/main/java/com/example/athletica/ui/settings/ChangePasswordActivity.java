package com.example.athletica.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athletica.R;
import com.example.athletica.data.account.LoginRegisterManager;
import com.example.athletica.ui.home.HomeActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private EditText etOldPassword, etNewPassword, etConfirmNewPassword;
    private ExtendedFloatingActionButton btnUpdatePassword;
    private LoginRegisterManager loginRegisterManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        firebaseAuth = FirebaseAuth.getInstance();
        loginRegisterManager = new LoginRegisterManager(this.getApplicationContext());
        currentUser = firebaseAuth.getCurrentUser();

        etOldPassword = findViewById(R.id.et_old_password);
        etNewPassword = findViewById(R.id.et_new_password);
        etConfirmNewPassword = findViewById(R.id.et_confirm_new_password);
        btnUpdatePassword = findViewById(R.id.action_change_password);

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword(etOldPassword.getText().toString(), etNewPassword.getText().toString(), etConfirmNewPassword.getText().toString());
            }
        });
    }


    private void updatePassword(String oldPassword, String newPassword, String confirmNewPassword) {
        loginRegisterManager.changePassword(oldPassword, confirmNewPassword, confirmNewPassword);
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }
}
