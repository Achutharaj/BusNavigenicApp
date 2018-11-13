package com.example.achutharaj_achu_.busnavigenic.usersActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.achutharaj_achu_.busnavigenic.R;
import com.example.achutharaj_achu_.busnavigenic.adminactivity.AdminDashboardActivity;
import com.example.achutharaj_achu_.busnavigenic.adminactivity.LoginActivity;
import com.example.achutharaj_achu_.busnavigenic.model.DriverProfile;
import com.example.achutharaj_achu_.busnavigenic.model.Location;
import com.example.achutharaj_achu_.busnavigenic.utility.Constant;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import im.delight.android.location.SimpleLocation;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DriverActivity extends AppCompatActivity {

    ToggleButton toggle;
    SimpleLocation simpleLocation;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    boolean isMapOn;


    CompositeDisposable compositeDisposable;

    double latitude;
    double longitude;
    private DatabaseReference driverReference;
    private DatabaseReference currentDriversReference;
    SimpleLocation.Listener locationListener;

    public CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable==null || compositeDisposable.isDisposed()){
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }



    @OnClick(R.id.btnOnOff)
    public void ToggleButton(){
        if(toggle.isChecked())
        {
            Toast.makeText(DriverActivity.this, "Location ON ", Toast.LENGTH_SHORT).show();
            toggle.setText("Map ON");
            onMap();


        }else{
            Toast.makeText(DriverActivity.this, "Location OFF", Toast.LENGTH_SHORT).show();
            toggle.setText("Map OFF");
            offMap();

        }
    }

    private void offMap() {
        getCompositeDisposable().dispose();
        driverReference.setValue(null);
        currentDriversReference.setValue(null);
    }


    private void onMap() {
        isMapOn = true;
        currentDriversReference.setValue(DriverProfile.getDriverProfile().getDriver());

        Disposable disposable =  new RxPermissions(this)
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(aBoolean -> {
                    if(aBoolean) {
                        if (aBoolean) {
                            simpleLocation = getSimpleLocation();
                            if (simpleLocation.hasLocationEnabled()) {
                                simpleLocation.beginUpdates();

                                simpleLocation.setListener(locationListener);

                            } else {
                                SimpleLocation.openSettings(DriverActivity.this);
                            }
                        } else {

                        }
                    }
                });

        getCompositeDisposable().add(disposable);
    }

    private SimpleLocation getSimpleLocation() {
        boolean requireFineGranularity = false;
        boolean passiveMode = false;
        long updateIntervalInMilliseconds = 200;
        boolean requireNewLocation = true;
       return new SimpleLocation(this, requireFineGranularity, passiveMode, updateIntervalInMilliseconds, requireNewLocation);
    }

    private String getBusNo() {
        return DriverProfile.getDriverProfile().getBusNo();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_layout);
        ButterKnife.bind(this);
            toggle = (ToggleButton) findViewById(R.id.btnOnOff);

        driverReference = FirebaseDatabase.getInstance()
                .getReference(Constant.LOCATION_DATABSE_REFERENCE)
                .child(getBusNo());

        currentDriversReference =
               FirebaseDatabase.getInstance()
                        .getReference(Constant.CURRENT_DRIVERS)
                        .child(getBusNo());

        locationListener = () -> {
            Toast.makeText(DriverActivity.this, "Location Changes", Toast.LENGTH_SHORT).show();
            Location location = new Location(simpleLocation.getLatitude(),simpleLocation.getLongitude());
            driverReference.push().setValue(location);
        };


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        offMap();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        simpleLocation.endUpdates();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(toggle.isChecked()){
            onMap();
        }else {
            offMap();
        }
    }

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
        startActivity(new Intent(DriverActivity.this,LoginActivity.class));
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




}



 /*Observable.interval(0,5, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<Long>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {
                                                getCompositeDisposable().add(d);
                                            }

                                            @Override
                                            public void onNext(Long aLong) {
                                                Location location = new Location(simpleLocation.getLatitude(),simpleLocation.getLongitude());
                                                driverReference.push().setValue(location);
                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });*/