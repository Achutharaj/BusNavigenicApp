package com.example.achutharaj_achu_.busnavigenic.adminactivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.achutharaj_achu_.busnavigenic.R;
import com.example.achutharaj_achu_.busnavigenic.model.Driver;
import com.example.achutharaj_achu_.busnavigenic.model.DriverProfile;
import com.example.achutharaj_achu_.busnavigenic.usersActivity.DriverActivity;
import com.example.achutharaj_achu_.busnavigenic.usersActivity.ViewBusActivity;
import com.example.achutharaj_achu_.busnavigenic.utility.FirebaseReference;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;


public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 665;
        private static final CharSequence ADMIN_EMAIL = "kururaj350@gmail.com";
//    private static final CharSequence ADMIN_EMAIL = "ubeshniriksha7@gmail.com";

    private TextView btnSignUp;
    Button btLogin;

    private ProgressDialog PD;

    ValueEventListener valueEventListener;
    DatabaseReference driverReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        PD = new ProgressDialog(this);
        PD.setMessage("Loading...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);
//        auth = FirebaseAuth.getInstance();

         btLogin = findViewById(R.id.btLogin);


        btLogin.setOnClickListener(v ->{

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if(user!=null) {
                loginUser(user);
                return;
            }
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.GoogleBuilder().build()
            );
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        });

        ImageView ivLogi = findViewById(R.id.ivLogin);
        Glide.with(this)
                .load(R.drawable.busicon)
                .into(ivLogi);


    }

    private void loginUser(FirebaseUser user) {
        String email = user.getEmail();

        if(email.contentEquals(ADMIN_EMAIL)){
            startActivity(new Intent(LoginActivity.this,AdminDashboardActivity.class));
        }else {
            loginAsDriver(email);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user!=null){
                   loginUser(user);


                }

                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    private void loginAsDriver(String email) {
        driverReference = FirebaseReference.newInstance().getDriverDatabaseReference();

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isDriverFound =false;
                if(dataSnapshot!=null) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Driver driver = dataSnapshot1.getValue(Driver.class);
                        if (driver.drivermail.contentEquals(email)) {
                            isDriverFound = true;
                            DriverProfile.setDriverProfile(driver);
                            startActivity(new Intent(LoginActivity.this, DriverActivity.class));
                        }
                    }

                    if (!isDriverFound) {
                        loginAsPassenger();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        driverReference.addValueEventListener(valueEventListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(driverReference!=null && valueEventListener!=null){
            driverReference.removeEventListener(valueEventListener);
        }
    }

    private void loginAsPassenger() {
        startActivity(new Intent(LoginActivity.this, ViewBusActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(driverReference !=null &&
                valueEventListener!=null){
            driverReference.removeEventListener(valueEventListener);
        }
    }
}
