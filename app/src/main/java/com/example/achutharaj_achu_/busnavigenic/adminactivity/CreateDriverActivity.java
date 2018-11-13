package com.example.achutharaj_achu_.busnavigenic.adminactivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.achutharaj_achu_.busnavigenic.R;
import com.example.achutharaj_achu_.busnavigenic.model.Driver;
import com.example.achutharaj_achu_.busnavigenic.utility.Constant;
import com.example.achutharaj_achu_.busnavigenic.utility.FirebaseReference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateDriverActivity extends Activity {

    FirebaseDatabase database;
    DatabaseReference userDatabaseRef;
    FirebaseReference firebaseReference;

    @BindView(R.id.tetDriverName)
    TextInputEditText tetdrivername;

    @BindView(R.id.tetBusNo)
    TextInputEditText tetbusno;

    @BindView(R.id.tetBusName)
    TextInputEditText tetbusname;

    @BindView(R.id.tetBusRoute)
    TextInputEditText tetbusroute;

    @BindView(R.id.tetDriverEmail)
    TextInputEditText tetdriverid;


    @BindView(R.id.btnCreate)
    Button btnCreate;

    @BindView(R.id.btnUpdate)
    Button btnUpdate;

    String drivername;
    String busno;
    String busname;
    String busroute;
    String driveremail;
    String driverpasswowrd;

    @OnClick(R.id.btnCreate)
    public void createNewUser(){
        Driver driver = new Driver (getDrivername(),getBusno(),getBusname(),getBusroute(), getDriveremail());
        if(!driver.busno.isEmpty())
            userDatabaseRef.child(driver.busno).setValue(driver).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    tetdrivername.setText("");
                    tetbusno.setText("");
                    tetbusname.setText("");
                    tetbusroute.setText("");
                    tetdriverid.setText("");
                    Toast.makeText(CreateDriverActivity.this,"User Added Successfully..!",Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_driver_layout);
        ButterKnife.bind(this);
        initiateFirebase();
        getIntentData();
        firebaseReference = new FirebaseReference();
    }

    private void getIntentData() {

        drivername = getIntent().getStringExtra(Constant.KEY_DRIVERNAME);
        if (drivername != null)
        {
            tetbusno.setEnabled(false);

            busno = getIntent().getStringExtra(Constant.KEY_BUSNO);
            busname = getIntent().getStringExtra(Constant.KEY_BUSNAME);
            busroute = getIntent().getStringExtra(Constant.KEY_BUSROUTE);
            driveremail =getIntent().getStringExtra(Constant.KEY_DRIVERID);


            tetdrivername.setText(drivername);
            tetbusno.setText(busno);
            tetbusname.setText(busname);
            tetbusroute.setText(busroute);
            tetdriverid.setText(driveremail);

            btnUpdate.setVisibility(View.VISIBLE);
//            Toast.makeText(this,"User Started this activity with username of " + username, Toast.LENGTH_SHORT).show();
        }else
        {
            btnCreate.setVisibility(View.VISIBLE);
            Toast.makeText(this,"Enter Your Correct Details",Toast.LENGTH_SHORT).show();
        }
    }

    private void initiateFirebase() {

        database = FirebaseDatabase.getInstance();
        userDatabaseRef = database.getReference(Constant.DRIVER_DATABASE_REFERENCE);
    }


    private String getDrivername(){
        return tetdrivername.getText().toString();
    }
    private String getBusno(){
        return tetbusno.getText().toString();
    }
    private String getBusname(){ return tetbusname.getText().toString(); }
    private String getBusroute(){
        return tetbusroute.getText().toString();
    }
    private String getDriveremail(){ return tetdriverid.getText().toString(); }

}
