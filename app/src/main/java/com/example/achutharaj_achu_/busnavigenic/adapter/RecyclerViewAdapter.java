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

import com.example.achutharaj_achu_.busnavigenic.adminactivity.CreateDriverActivity;
import com.example.achutharaj_achu_.busnavigenic.R;
import com.example.achutharaj_achu_.busnavigenic.model.Driver;
import com.example.achutharaj_achu_.busnavigenic.utility.Constant;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<Driver> driverArrayList;
    public RecyclerViewAdapter(ArrayList<Driver> driverArrayList) {
        this.driverArrayList = driverArrayList;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_drivers,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Driver driver = driverArrayList.get(position);
        holder.tvdrivername.setText(driver.drivername);
        holder.tvbusno.setText(driver.busno);
        holder.tvbusname.setText(driver.busname);
        holder.tvbusroute.setText(driver.busroute);
        holder.tvdriverid.setText(driver.drivermail);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Driver driver1 = driverArrayList.get(holder.getAdapterPosition());
                Toast.makeText(holder.itemView.getContext(),"Click user name is " + driver1.drivername, Toast.LENGTH_SHORT).show();
            }
        });


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Driver driver1 = driverArrayList.get(holder.getAdapterPosition());
                FirebaseDatabase.getInstance().getReference(Constant.DRIVER_DATABASE_REFERENCE)
                        .child(driver1.busno).setValue(null);

                Toast.makeText(holder.itemView.getContext(),driver.drivername +" Deleted Successfully!", Toast.LENGTH_SHORT).show();
            }
        });


//        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Driver driver1 = driverArrayList.get(holder.getAdapterPosition());
//                Intent intent = new Intent(holder.itemView.getContext(),
//                        CreateDriverActivity.class);
//
//                intent.putExtra(Constant.KEY_DRIVERNAME,driver1.drivername);
//                intent.putExtra(Constant.KEY_BUSNO,driver1.busno);
//                intent.putExtra(Constant.KEY_BUSNAME,driver1.busname);
//                intent.putExtra(Constant.KEY_BUSROUTE,driver1.busroute);
//                intent.putExtra(Constant.KEY_DRIVERID,driver1.drivermail);
//
//                holder.itemView.getContext().startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() { return driverArrayList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvdrivername,tvbusno,tvbusname,tvbusroute,tvdriverid;
        public Button btnDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            tvdrivername = itemView.findViewById(R.id.tvDrivername);
            tvbusno = itemView.findViewById(R.id.tvBusNo);
            tvbusname= itemView.findViewById(R.id.tvBusName);
            tvbusroute= itemView.findViewById(R.id.tvBusRoute);
            tvdriverid= itemView.findViewById(R.id.tvDriverEmail);
            btnDelete=itemView.findViewById(R.id.btnDriverDelete);
//            btnEdit=itemView.findViewById(R.id.btnDriverEdit);

        }
    }
}
