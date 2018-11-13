package com.example.achutharaj_achu_.busnavigenic.adminactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.achutharaj_achu_.busnavigenic.R;
import com.example.achutharaj_achu_.busnavigenic.adapter.RecyclerViewAdapter;
import com.example.achutharaj_achu_.busnavigenic.adapter.RecyclerViewPassengerAdapter;
import com.example.achutharaj_achu_.busnavigenic.model.Driver;
import com.example.achutharaj_achu_.busnavigenic.model.Passenger;
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

public class ViewPassengerActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    ArrayList<Passenger> passArrayList;
    RecyclerViewPassengerAdapter recyclerAdepterPassenger;

    ArrayList<Passenger> SearchResult1;

    @BindView(R.id.rcPassenger)
    RecyclerView rcPassenger;

    @BindView(R.id.btnDPassSearchView)
    android.support.v7.widget.SearchView passsearchView;


    @OnClick(R.id.fln)
    public void viewuseractivity() {
        Intent intent = new Intent(this, CreatePassengerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_passenger_layout);
        initializeFirebase();
        ButterKnife.bind(this);
        setRecyclerView1();
        getUserList();
        configureSearchView();
    }

    private void configureSearchView() {
        SearchResult1 = new ArrayList<>();
        passsearchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(">>/", "onQueryTextSubmit:" + query);
                searchuser(query);
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(">>/", "onQueryTextChange :" + newText);
                if (newText.isEmpty()) {
                    updateRecyclerView(passArrayList);
                }
                if (newText.length() == 3) {
                    searchuser(newText);
                }
                return false;


            }
        });
    }

    private void searchuser(String query) {

        SearchResult1.clear();

        for (Passenger pass : passArrayList) {
            if (pass.userid.contentEquals(query)) {
                SearchResult1.clear();
                SearchResult1.add(pass);
                updateRecyclerView(SearchResult1);
            }

        }
    }

    private void updateRecyclerView(ArrayList<Passenger> searchResult) {
        recyclerAdepterPassenger = new RecyclerViewPassengerAdapter(SearchResult1);
        rcPassenger.setAdapter(recyclerAdepterPassenger);
        rcPassenger.setLayoutManager(new LinearLayoutManager(this));

    }
        private void getUserList () {
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Passenger pass = dataSnapshot.getValue(Passenger.class);
                    passArrayList.add(pass);
                    //TODO
                    //sort the list in id order
                    int size = passArrayList.size();
                    recyclerAdepterPassenger.notifyItemInserted(size - 1);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Passenger pass = dataSnapshot.getValue(Passenger.class);
                    int index = passArrayList.indexOf(pass);
                    passArrayList.remove(pass);
                    passArrayList.add(index, pass);
                    recyclerAdepterPassenger.notifyItemChanged(index);
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    Passenger pass = dataSnapshot.getValue(Passenger.class);
                    int index = passArrayList.indexOf(pass);

                    if (passArrayList.remove(pass)) {
                        recyclerAdepterPassenger.notifyItemRemoved(index);
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

        private void setRecyclerView1 () {
            recyclerAdepterPassenger = new RecyclerViewPassengerAdapter(passArrayList);
            rcPassenger.setAdapter(recyclerAdepterPassenger);
            rcPassenger.setLayoutManager(new LinearLayoutManager(this));
        }

        private void initializeFirebase () {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference(Constant.PASSENGER_DATABSE_REFERENCE);
            passArrayList = new ArrayList();
        }
    }
