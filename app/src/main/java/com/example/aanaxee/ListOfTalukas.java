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

public class ListOfTalukas extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Talukas> talukas;
    int dId;
    private static String JSON_URL;
    AdapterTalukas adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_taluka);
        Intent mIntent = getIntent();
        int didValue = mIntent.getIntExtra("dId",'0');
        JSON_URL = "https://project-swarksha.uc.r.appspot.com/talukas?did="+didValue;
        Log.d("TAG", "onCreate: "+JSON_URL);
        talukas = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_viewT);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterTalukas(this, talukas);
        recyclerView.setAdapter(adapter);
        extractDistrict();
    }

    private void extractDistrict(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(JSON_URL, response -> {
            if (response != null) {
                Gson gson = new Gson();
                JSONArray jsonArray = response.optJSONArray("talukas");
                if (jsonArray != null) {
                    Talukas[] taluka = gson.fromJson(jsonArray.toString(), Talukas[].class);
                    for (Talukas t: taluka) {
                        talukas.add(t);
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
