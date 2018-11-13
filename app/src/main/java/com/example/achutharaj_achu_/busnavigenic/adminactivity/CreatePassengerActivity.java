package com.example.achutharaj_achu_.busnavigenic.adminactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.achutharaj_achu_.busnavigenic.R;
import com.example.achutharaj_achu_.busnavigenic.model.Driver;
import com.example.achutharaj_achu_.busnavigenic.model.Passenger;
import com.example.achutharaj_achu_.busnavigenic.utility.Constant;
import com.example.achutharaj_achu_.busnavigenic.utility.FirebaseReference;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreatePassengerActivity extends AppCompatActivity {

//    FirebaseDatabase database;
//    DatabaseReference userDatabaseRef;
//    FirebaseReference firebaseReference;
//
//    @BindView(R.id.tetPassName)
//    TextInputEditText tetPassname;
//
//    @BindView(R.id.tetPassMobileNo)
//    TextInputEditText tetMobNo;
//
//    @BindView(R.id.tetPassengerId)
//    TextInputEditText tetPassID;
//
//    @BindView(R.id.tetPasspassword)
//    TextInputEditText tetPasspassword;
//
//    @BindView(R.id.tetConpassword)
//    TextInputEditText tetConpassword;
//
//    @BindView(R.id.btnpassCreate)
//    Button btnpassCreate;
//
//    @BindView(R.id.btnpassUpdate)
//    Button btnpassUpdate;
//
//    String username;
//    String mobno;
//    String userid;
//    String password;
//    String conpassword;
//
//    @OnClick(R.id.btnpassCreate)
//    public void Craetepassenger(){
//
//        Passenger pass = new Passenger (getusername(),getmobno(),getuserid(),getpassword(),getconpassword());
//        if(!pass.userid.isEmpty())
//            userDatabaseRef.child(pass.userid).setValue(pass).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
//                    tetPassname.setText("");
//                    tetMobNo.setText("");
//                    tetPassID.setText("");
//                    tetPasspassword.setText("");
//                    tetConpassword.setText("");
//                    Toast.makeText(CreatePassengerActivity.this,"User Added Successfully..!",Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//            });
//    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.create_passenger_layout);
//        ButterKnife.bind(this);
//        initiateFirebase();
//        getIntentData();
//        firebaseReference = new FirebaseReference();
//    }
//
//    private void getIntentData() {
//        username = getIntent().getStringExtra(Constant.KEY_PASSENGERNAME);
//        if(username != null)
//        {
//            tetPassID.setEnabled(false);
//
//            mobno = getIntent().getStringExtra(Constant.KEY_PASSENGERMOBILENO);
//            userid = getIntent().getStringExtra(Constant.KEY_PASSENGERID);
//            password = getIntent().getStringExtra(Constant.KEY_PASSENGERPASSWORD);
//             conpassword=getIntent().getStringExtra(Constant.KEY_CONPASSENGERPASSWORD);
//
//
//            tetPassname.setText(username);
//            tetMobNo.setText(mobno);
//            tetPassID.setText(userid);
//            tetPasspassword.setText(password);
//            tetConpassword.setText(conpassword);
//
//
//            btnpassUpdate.setVisibility(View.VISIBLE);
////            Toast.makeText(this,"User Started this activity with username of " + username, Toast.LENGTH_SHORT).show();
//        }else
//        {
//            btnpassCreate.setVisibility(View.VISIBLE);
//            Toast.makeText(this,"Enter Your Correct Details",Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void initiateFirebase() {
//        database = FirebaseDatabase.getInstance();
//        userDatabaseRef = database.getReference(Constant.PASSENGER_DATABSE_REFERENCE);
//    }
//
//
//    private String getusername(){
//        return tetPassname.getText().toString();
//    }
//    private String getmobno(){
//        return tetMobNo.getText().toString();
//    }
//    private String getuserid(){ return tetPassID.getText().toString(); }
//    private String getpassword(){
//        return tetPasspassword.getText().toString();
//    }
//    private String getconpassword(){ return tetConpassword.getText().toString(); }

}
