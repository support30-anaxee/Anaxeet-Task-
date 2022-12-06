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

public class ListOfDistrict extends AppCompatActivity {
    RecyclerView recyclerView;
    List<District> districts;
    int sId;
    private static String JSON_URL;
    AdapterDistrict adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_district);
        Intent mIntent = getIntent();
        int intValue = mIntent.getIntExtra("sId",'0');
        JSON_URL = "https://project-swarksha.uc.r.appspot.com/districts?sid="+intValue;
        Log.d("TAG", "onCreate: "+JSON_URL);
        districts = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterDistrict(this, districts);
        recyclerView.setAdapter(adapter);
        extractState();
    }

    private void extractState(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(JSON_URL, response -> {
            if (response != null) {
                Gson gson = new Gson();
                JSONArray jsonArray = response.optJSONArray("districts");
                if (jsonArray != null) {
                    District[] district = gson.fromJson(jsonArray.toString(), District[].class);
                    for (District d: district) {
                        districts.add(d);
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
