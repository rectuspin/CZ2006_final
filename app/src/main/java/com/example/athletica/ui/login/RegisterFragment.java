package com.example.athletica.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.athletica.R;
import com.example.athletica.data.account.LoginRegisterManager;
import com.example.athletica.data.account.NetworkStatus;

public class RegisterFragment extends CustomFragment implements View.OnClickListener {


    private LoginRegisterManager loginRegisterManager;
    private EditText etEmail, etPassword, etConfrimPassword;
    private Button btnRegister;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        loginRegisterManager = new LoginRegisterManager(this.getContext());

        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        etConfrimPassword = view.findViewById(R.id.etConfirmPassword);
        btnRegister = view.findViewById(R.id.action_register);

        networkCheck();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        networkCheck();
    }

    private void register() {

        // Retrieve user's input
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfrimPassword.getText().toString().trim();


        // Validates the user credentials and register the user into the database
        if (loginRegisterManager.validateLoginRegisterInput(email, password)) {
            loginRegisterManager.register(email, password, confirmPassword);
        }
    }

    private void networkCheck(){
        if(!NetworkStatus.isConnected(getActivity())){
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "You are not connected to the Internet. Connect and restart the app!",Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    register();
                }
            });
        }
    }

}