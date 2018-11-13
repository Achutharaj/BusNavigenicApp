package com.example.achutharaj_achu_.busnavigenic.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.achutharaj_achu_.busnavigenic.model.Location;
import com.example.achutharaj_achu_.busnavigenic.usersActivity.DriverActivity;
import com.example.achutharaj_achu_.busnavigenic.utility.Constant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import im.delight.android.location.SimpleLocation;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DriverLocation extends AppCompatActivity {

    SimpleLocation simpleLocation;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    CompositeDisposable compositeDisposable;

    double latitude;
    double longitude;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


    }

    public CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable==null || compositeDisposable.isDisposed()){
            compositeDisposable = new CompositeDisposable();
        }
        return compositeDisposable;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Disposable disposable = new RxPermissions(this)
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(aBoolean -> {
                    if(aBoolean) {
                        if (aBoolean) {
                            simpleLocation = new SimpleLocation(DriverLocation.this);
                            if (simpleLocation.hasLocationEnabled()) {
                                simpleLocation.beginUpdates();
                                Observable.interval(0,5, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<Long>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {
                                                getCompositeDisposable().add(d);
                                            }

                                            @Override
                                            public void onNext(Long aLong) {
                                                Location location = new Location(simpleLocation.getLatitude(),simpleLocation.getLongitude());
                                                DatabaseReference data = FirebaseDatabase.getInstance().getReference(Constant.LOCATION_DATABSE_REFERENCE);
                                                data.child("Location").push().setValue(location);


                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });
                            } else {
                                SimpleLocation.openSettings(DriverLocation.this);
                            }
                        } else {

                        }
                    }
                });
        getCompositeDisposable().add(disposable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getCompositeDisposable().dispose();
    }
}
