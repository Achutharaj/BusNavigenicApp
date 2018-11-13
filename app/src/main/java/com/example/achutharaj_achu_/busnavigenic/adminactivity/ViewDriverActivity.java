package com.example.achutharaj_achu_.busnavigenic.adminactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;

import com.example.achutharaj_achu_.busnavigenic.R;
import com.example.achutharaj_achu_.busnavigenic.adapter.RecyclerViewAdapter;
import com.example.achutharaj_achu_.busnavigenic.model.Driver;
import com.example.achutharaj_achu_.busnavigenic.utility.Constant;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewDriverActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RecyclerViewAdapter recyclerAdepterDriver;
    ArrayList<Driver> driverArrayList;
    ArrayList<Driver> searchResult;

    @BindView(R.id.rcDriver)
    RecyclerView rcDriver;

    @BindView(R.id.btnDriverSearchView)
    SearchView svDriver;


    @OnClick(R.id.fln)
    public void viewuseractivity(){
        Intent intent= new Intent(this,CreateDriverActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_driver_layout);
        initializeFirebase();
        ButterKnife.bind(this);
        setRecyclerView1();
        getUserList();
        configureSearchView();

    }

    private void configureSearchView() {
        searchResult = new ArrayList<>();
        svDriver.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(">>/","onQueryTextSubmit:"+query);
                searchUser(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(">>/","onQueryTextChange :"+newText);
                if(newText.isEmpty())
                {
                    updateRecyclerView(driverArrayList);
                }
                searchUser(newText);
                return false;
            }
        });
    }

    private void getUserList() {
        databaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Driver driver = dataSnapshot.getValue(Driver.class);
                driverArrayList.add(driver);
                //TODO
                //sort the list in id order
                int size = driverArrayList.size();
                recyclerAdepterDriver.notifyItemInserted(size-1);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Driver driver = dataSnapshot.getValue(Driver.class);
                int index = driverArrayList.indexOf(driver);
                driverArrayList.remove(driver);
                driverArrayList.add(index,driver);
                recyclerAdepterDriver.notifyItemChanged(index); }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                Driver driver = dataSnapshot.getValue(Driver.class);
                int index = driverArrayList.indexOf(driver);

                if (driverArrayList.remove(driver)){
                    recyclerAdepterDriver.notifyItemRemoved(index);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setRecyclerView1() {
        recyclerAdepterDriver = new RecyclerViewAdapter(driverArrayList);
        rcDriver.setAdapter(recyclerAdepterDriver);
        rcDriver.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Constant.DRIVER_DATABASE_REFERENCE);
        driverArrayList = new ArrayList();
    }


    private void searchUser(String query){
        searchResult.clear();
        for(Driver driver:driverArrayList){
            if(driver.busno.contains(query)) {
                searchResult.add(driver);
            }
            if (!searchResult.isEmpty()){
                updateRecyclerView(searchResult);
            }

        }
    }

    private void updateRecyclerView(ArrayList<Driver> searchResult) {
        recyclerAdepterDriver = new RecyclerViewAdapter(searchResult);
        rcDriver.setAdapter(recyclerAdepterDriver);
        rcDriver.setLayoutManager(new LinearLayoutManager(this));

    }
}

