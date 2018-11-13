package com.example.achutharaj_achu_.busnavigenic.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.achutharaj_achu_.busnavigenic.R;
import com.example.achutharaj_achu_.busnavigenic.adminactivity.CreatePassengerActivity;
import com.example.achutharaj_achu_.busnavigenic.model.Passenger;
import com.example.achutharaj_achu_.busnavigenic.utility.Constant;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerViewPassengerAdapter extends RecyclerView.Adapter<RecyclerViewPassengerAdapter.ViewHolder> {

    ArrayList<Passenger> passArrayList;
    public RecyclerViewPassengerAdapter(ArrayList<Passenger> passArrayList) {
        this.passArrayList = passArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_passenger,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Passenger pass = passArrayList.get(position);
        holder.tvUsername.setText(pass.username);
        holder.tvMobNo.setText(pass.mobno);
        holder.tvUserid.setText(pass.userid);
        holder.tvPassword.setText(pass.password);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Passenger pass = passArrayList.get(holder.getAdapterPosition());
                Toast.makeText(holder.itemView.getContext(), "Clicked  " + pass.username + "  Bus number ", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnpassDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Passenger pass = passArrayList.get(holder.getAdapterPosition());
                FirebaseDatabase.getInstance().getReference(Constant.PASSENGER_DATABSE_REFERENCE).child(pass.userid).setValue(null);

                Toast.makeText(holder.itemView.getContext(),pass.username +" Deleted Successfully!", Toast.LENGTH_SHORT).show();

            }
        });


        holder.btnpassEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Passenger pass = passArrayList.get(holder.getAdapterPosition());
                Intent intent = new Intent(holder.itemView.getContext(),CreatePassengerActivity.class);


                holder.itemView.getContext().startActivity(intent);
                Toast.makeText(holder.itemView.getContext(),pass.username +" Updated Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return passArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUsername,tvMobNo,tvUserid,tvPassword;
        public Button btnpassDelete, btnpassEdit;
        public ViewHolder(View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvpassname);
            tvMobNo = itemView.findViewById(R.id.tvPassMobNo);
            tvUserid= itemView.findViewById(R.id.tvPassID);
            tvPassword= itemView.findViewById(R.id.tvPassPassword);
            btnpassDelete= itemView.findViewById(R.id.btnpassDelete);
            btnpassEdit= itemView.findViewById(R.id.btnpassEdit);
        }
    }
}
