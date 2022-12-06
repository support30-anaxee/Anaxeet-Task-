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

public class AdapterTalukas extends RecyclerView.Adapter<AdapterTalukas.ViewHolder> {

    List<Talukas> talukas;
    private Context ctx;
    public AdapterTalukas(Context ctx, List<Talukas> talukas){
        this.ctx = ctx;
        this.talukas = talukas;
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
        Talukas taluka = talukas.get(position);
        holder.statesName.setText(taluka.getName());
        //changes
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx,ListOfVillage.class);
                intent.putExtra("sId", taluka.tid);
                ctx.startActivity(intent);
                Log.d("TAG", "onClick: :::::::::::::::::::"+taluka.tid);
            }
        });
    }

    @Override
    public int getItemCount() {
        return talukas.size();
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
