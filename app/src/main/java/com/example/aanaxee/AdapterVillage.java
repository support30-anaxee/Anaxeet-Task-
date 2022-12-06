package com.example.aanaxee;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterVillage extends RecyclerView.Adapter<AdapterVillage.ViewHolder> {

    List<Village> villages;
    private Context ctx;
    IAdaperVillage mListner;
    public AdapterVillage(Context ctx, List<Village> villages){
        this.ctx = ctx;
        this.villages = villages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.custom_list_of_village,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view,mListner);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Village village = villages.get(position);
        holder.statesName.setText(village.getName());
        //changes
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx,ListOfTalukas.class);
                intent.putExtra("dId", village.vid);
                ctx.startActivity(intent);
                Log.d("TAG", "onClick: :::::::::::::::::::"+village.vid);
            }
        });
    }

    @Override
    public int getItemCount() {
        return villages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView statesName;
        LinearLayout container;
        View rootView;
        int position;
        IAdaperVillage mListner;
        Village village;

        public ViewHolder(@NonNull View itemView, IAdaperVillage mListner) {
            super(itemView);
            this.mListner = mListner;
            rootView = itemView;
            statesName = itemView.findViewById(R.id.state_name);
            container = itemView.findViewById(R.id.container);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("demo","onClick: item clicked" + position+ village.getName());
                    Intent intent = new Intent(rootView.getContext(), SQLiteDatabase.class);
                    rootView.getContext().startActivity(intent);
                }
            });
            itemView.findViewById(R.id.add_Village).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("demo","onClick: Add for runner "+ village.getName());
                }
            });
        }
    }
    interface IAdaperVillage{
        void gotoVillage(Village village);
    }
}
