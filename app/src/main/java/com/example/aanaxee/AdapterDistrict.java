package com.example.aanaxee;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterDistrict extends RecyclerView.Adapter<AdapterDistrict.ViewHolder> {

    List<District> districts;
    private Context ctx;
    public AdapterDistrict(Context ctx, List<District> districts){
        this.ctx = ctx;
        this.districts = districts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.custom_list_layout,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        District district = districts.get(position);
        holder.statesName.setText(district.getName());
        //changes
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx,ListOfTalukas.class);
                intent.putExtra("dId", district.did);
                ctx.startActivity(intent);
                Log.d("TAG", "onClick: :::::::::::::::::::"+district.did);
            }
        });
    }

    @Override
    public int getItemCount() {
        return districts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView statesName;
        LinearLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            statesName = itemView.findViewById(R.id.state_name);
            container = itemView.findViewById(R.id.container);
        }
    }
}
