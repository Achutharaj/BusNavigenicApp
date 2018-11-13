package com.example.achutharaj_achu_.busnavigenic.adminactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.achutharaj_achu_.busnavigenic.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class AdminDashboardActivity extends AppCompatActivity {

    @OnClick(R.id.btnDriver)
    public void DriverDetails(){
        Intent intent = new Intent(this,ViewDriverActivity.class);
        startActivity(intent);
    }

//    @OnClick(R.id.btnPassenger)
//    public void PassengerDetails(){
//
//        Intent intent = new Intent(this,ViewPassengerActivity.class);
//        startActivity(intent);
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.signout:
                signOutUser();
                break;
        }

        return true;
    }

    private void startLoginActivity() {
       startActivity(new Intent(AdminDashboardActivity.this,LoginActivity.class));
       finish();
    }

    private void signOutUser() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        startLoginActivity();

                    }
                });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard_layout);
        ButterKnife.bind(this);
    }

}
