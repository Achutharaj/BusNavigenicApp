package com.example.achutharaj_achu_.busnavigenic.utility;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Driver;

public class FirebaseReference {
    FirebaseDatabase firebaseDatabase;

    static FirebaseReference firebaseReference;

    public static FirebaseReference newInstance(){
        if(firebaseReference==null){
            firebaseReference = new FirebaseReference();
        }
        return firebaseReference;
    }

    public FirebaseReference() {
        firebaseDatabase = FirebaseDatabase.getInstance();

    }


    public DatabaseReference getDriverDatabaseReference() {
        return firebaseDatabase.getReference(Constant.DRIVER_DATABASE_REFERENCE);
    }

    public DatabaseReference getLocationDatabaseReference() {
        return firebaseDatabase.getReference(Constant.LOCATION_DATABSE_REFERENCE);

    }

    public DatabaseReference getPassDatabaseReference() {
        return firebaseDatabase.getReference(Constant.PASSENGER_DATABSE_REFERENCE);

    }

    public DatabaseReference getAllUserDatabaseReference() {
        return firebaseDatabase.getReference(Constant.USER_DB_REFERENCE);
    }
}
