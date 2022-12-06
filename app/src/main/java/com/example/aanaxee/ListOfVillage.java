package com.example.aanaxee;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ListOfVillage extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Village> villages;
    int tId;
    private static String JSON_URL;
    AdapterVillage adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ofvillage);
        Intent mIntent = getIntent();
        int tidValue = mIntent.getIntExtra("tId",'0');
        JSON_URL = "https://project-swarksha.uc.r.appspot.com/villages?tid="+tidValue;
        Log.d("TAG", "onCreate: "+JSON_URL);
        villages = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_viewV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterVillage(this, villages);
        recyclerView.setAdapter(adapter);
        extractVillage();
    }

    private void extractVillage(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(JSON_URL, response -> {
            if (response != null) {
                Gson gson = new Gson();
                JSONArray jsonArray = response.optJSONArray("villages");
                if (jsonArray != null) {
                    Village[] district = gson.fromJson(jsonArray.toString(), Village[].class);
                    for (Village d: district) {
                        villages.add(d);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG", error.toString());
            }
        });
        queue.add(jsonObjectRequest);
    }
}
