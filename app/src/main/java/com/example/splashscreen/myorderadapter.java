package com.example.splashscreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myorderadapter extends FirebaseRecyclerAdapter<orderModel,myorderadapter.myviewholder> {

    public myorderadapter(@NonNull FirebaseRecyclerOptions<orderModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull orderModel model) {
        holder.ServiceName.setText(model.getServiceType());
        holder.ProviderName.setText(model.getProviderName());
        holder.ProviderPhone.setText(model.getProviderPhone());
        holder.ServicePrice.setText(model.getServicePrice());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordersinglerow,parent,false);
        return new myviewholder(view);
    }


    public class myviewholder extends RecyclerView.ViewHolder
    {

        TextView ServiceName,ServicePrice,ProviderName,ProviderPhone;

        public myviewholder(@NonNull View itemView) {
            super(itemView);


            ServiceName=(TextView) itemView.findViewById(R.id.ServiceName);
            ServicePrice=(TextView)itemView.findViewById(R.id.ServicePrice);
           ProviderName=(TextView)itemView.findViewById(R.id.ProviderName);
            ProviderPhone=(TextView)itemView.findViewById(R.id.ProviderPhone);



        }
    }
}
