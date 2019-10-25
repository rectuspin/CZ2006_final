package com.example.athletica.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.athletica.R;
import com.example.athletica.data.account.LoginRegisterManager;


public class LoginFragment extends CustomFragment implements View.OnClickListener {

    private TextView tvState;
    private Button btnLogin;
    private EditText etEmail, etPassword;
    private LoginRegisterManager loginRegisterManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialise an LoginRegisterManager
        loginRegisterManager = new LoginRegisterManager(this.getContext());

        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        tvState = view.findViewById(R.id.stateTextView);

        return view;
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.action_login)
            login();

    }


    private void login() {
        // Retrieve the user's input
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        // Validates the user's credentials and logs the user into the application
        if (loginRegisterManager.validateLoginRegisterInput(email, password))
            loginRegisterManager.login(email, password);

    }
}
