package com.example.athletica.data.account;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.athletica.data.user.DataManager;
import com.example.athletica.data.user.User;
import com.example.athletica.data.user.UserProfile;
import com.example.athletica.ui.home.HomeActivity;
import com.example.athletica.ui.login.LoginRegisterActivity;
import com.example.athletica.ui.profile.CreateProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class LoginRegisterManager {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private Context context;
    private ProgressDialog progressDialog;
    public static User loggedUser;

    public LoginRegisterManager() {
        this.firebaseDatabase = FirebaseDatabase.getInstance();
    }


    public LoginRegisterManager(Context context) {
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(context);
    }


    public void register(String email, final String password, final String confirmPassword) {
        if (password.equals(confirmPassword)) {
            progressDialog.setMessage("Registering account...");
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.cancel();
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Account created!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, CreateProfileActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context, "Could not create account! Try again later.", Toast.LENGTH_SHORT).show();
                                FirebaseException e = (FirebaseException) task.getException();
                                System.out.println(e.getMessage());
                            }
                        }

                    });
        } else {
            Toast.makeText(context, "Passwords don't match!", Toast.LENGTH_SHORT).show();
        }
    }


    public void login(String email, String password) {
        progressDialog.setMessage("Logging in...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.cancel();
                        if (task.isSuccessful()) {
                            setLoggedUser();
                            Toast.makeText(context, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        } else {
                            FirebaseAuthException e = (FirebaseAuthException) task.getException();
                            Toast.makeText(context, "Invalid e-mail / password!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    public void logOut() {
        FirebaseAuth.getInstance().signOut();
        loggedUser = null;
        Intent intent = new Intent(context, LoginRegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public boolean isLoggedIn() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            return false;
        } else {
            firebaseUser.getIdToken(true);
            setLoggedUser();
            return true;
        }
    }


    public void changePassword(final String password, final String confirmPassword, final String oldPassword) {
        if (password.equals(confirmPassword)) {
            final FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null) {
                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);

                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "User re-authenticated!", Toast.LENGTH_SHORT).show();
                        //add method for message if not authenticated
                        if (validateLoginRegisterInput(user.getEmail(), password)) {

                            user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(context, "Passwod updated!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "Password has not been updated", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });

            } else {
                Toast.makeText(context, "User is not signed in!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, LoginRegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

        }
    }

    public void saveUserProfile(String userId, String name, String birthdate, String sex, String description, ArrayList<String> sports, ArrayList<String> follows) {

        if (sports.size() < 1) {
            sports.add("N/A");
        }
        if (follows.size() < 1)
            follows.add("N/A");

        ArrayList<String> emptyEventList = new ArrayList<>();
        emptyEventList.add("Nothing here yet");
        UserProfile userProfile = new UserProfile(name, sex, description, birthdate, sports, follows);

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users_info");
        databaseReference.child(userId).setValue(userProfile);
        databaseReference = firebaseDatabase.getReference().child("users_events");
        databaseReference.child(userId).setValue(emptyEventList);

        setLoggedUser();
    }


    public boolean validateLoginRegisterInput(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(context, "E-mail cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Please enter valid e-mail!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "Password cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(context, "Password must be at least 6 digits long!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    public boolean validateProfileDetails(Date current, Date birthdayDate, String name) {
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(context, "Name is required!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (name.length() > 50) {
            Toast.makeText(context, "Your name is too long!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (birthdayDate.after(current) || birthdayDate.equals(current)) {
            Toast.makeText(context, "Are you sure about your birthdate? ;)", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public static void setLoggedUser() {
        final DataManager dataManager = new DataManager();
        dataManager.getUser(new DataManager.DataStatus() {
            @Override
            public void dataLoaded(Object object) {
                loggedUser = (User) object;
                dataManager.getUserEvents(new DataManager.DataStatus() {
                    @Override
                    public void dataLoaded(Object object) {
                        loggedUser.setEventsJoined((ArrayList<String>) object);
                    }
                }, loggedUser.getId());
            }
        }, FirebaseAuth.getInstance().getCurrentUser().getUid());
    }


}
