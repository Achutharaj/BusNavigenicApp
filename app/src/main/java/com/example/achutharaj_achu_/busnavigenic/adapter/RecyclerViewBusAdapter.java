package com.example.achutharaj_achu_.busnavigenic.adapter;

import android.content.Context;
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
import com.example.achutharaj_achu_.busnavigenic.map.MapsActivity;
import com.example.achutharaj_achu_.busnavigenic.model.Driver;

import java.util.ArrayList;

public class RecyclerViewBusAdapter extends RecyclerView.Adapter<RecyclerViewBusAdapter.ViewHolder> {

    ArrayList<Driver> driverArrayList;
    public RecyclerViewBusAdapter(ArrayList<Driver> driverArrayList) {
        this.driverArrayList = driverArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_bus,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Driver driver = driverArrayList.get(position);
        holder.tvbusno.setText(driver.busno);
        holder.tvbusname.setText(driver.busname);
        holder.tvbusroute.setText(driver.busroute);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Driver driver1 = driverArrayList.get(holder.getAdapterPosition());
                Toast.makeText(holder.itemView.getContext(), "Clicked  " + driver1.busno + "  Bus number ", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnfindbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Intent intent = new Intent(context, MapsActivity.class);
                context.startActivity(intent);

                Toast.makeText(holder.itemView.getContext()," Button Clicked.!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return  driverArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvbusno,tvbusname,tvbusroute;
        public Button btnfindbus;
        public ViewHolder(View itemView) {
            super(itemView);

            tvbusno = itemView.findViewById(R.id.tvBusNo);
            tvbusname= itemView.findViewById(R.id.tvBusName);
            tvbusroute= itemView.findViewById(R.id.tvBusRoute);
            btnfindbus=itemView.findViewById(R.id.btnFindBus);

        }
    }
}
