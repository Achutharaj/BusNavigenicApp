package com.example.achutharaj_achu_.busnavigenic.usersActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.achutharaj_achu_.busnavigenic.R;
import com.example.achutharaj_achu_.busnavigenic.adapter.RecyclerViewAdapter;
import com.example.achutharaj_achu_.busnavigenic.adapter.RecyclerViewBusAdapter;
import com.example.achutharaj_achu_.busnavigenic.adminactivity.AdminDashboardActivity;
import com.example.achutharaj_achu_.busnavigenic.adminactivity.CreateDriverActivity;
import com.example.achutharaj_achu_.busnavigenic.adminactivity.LoginActivity;
import com.example.achutharaj_achu_.busnavigenic.model.Driver;
import com.example.achutharaj_achu_.busnavigenic.utility.Constant;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewBusActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RecyclerViewBusAdapter recyclerAdepterBus;
    ArrayList<Driver> driverArrayList;
    ArrayList<Driver> searchResult;

    @BindView(R.id.rcBus)
    RecyclerView rcBus;

    @BindView(R.id.btnBusSearchView)
    SearchView svBus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_bus_layout);
        initializeFirebase();
        ButterKnife.bind(this);
        setRecyclerView1();
        getUserList();
        configureSearchView();
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
                recyclerAdepterBus.notifyItemInserted(size-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Driver driver = dataSnapshot.getValue(Driver.class);
                int index = driverArrayList.indexOf(driver);
                driverArrayList.remove(driver);
                driverArrayList.add(index,driver);
                recyclerAdepterBus.notifyItemChanged(index);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                Driver driver = dataSnapshot.getValue(Driver.class);
                int index = driverArrayList.indexOf(driver);

                if (driverArrayList.remove(driver)){
                    recyclerAdepterBus.notifyItemRemoved(index);
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

    private void configureSearchView() {
        searchResult = new ArrayList<>();
        svBus.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        recyclerAdepterBus = new RecyclerViewBusAdapter(searchResult);
        rcBus.setAdapter(recyclerAdepterBus);
        rcBus.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setRecyclerView1() {
        recyclerAdepterBus = new RecyclerViewBusAdapter(driverArrayList);
        rcBus.setAdapter(recyclerAdepterBus);
        rcBus.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Constant.DRIVER_DATABASE_REFERENCE);
        driverArrayList = new ArrayList();
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
        startActivity(new Intent(ViewBusActivity.this,LoginActivity.class));
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
