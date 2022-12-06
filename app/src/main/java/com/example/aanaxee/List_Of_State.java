package com.example.aanaxee;

import android.annotation.SuppressLint;
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

public class List_Of_State extends AppCompatActivity {

    RecyclerView recyclerView;
    List<State> states;
    private static String JSON_URL = "https://project-swarksha.uc.r.appspot.com/states";
    AdapterState adapter;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_state);
        states = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterState(this, states);
        recyclerView.setAdapter(adapter);
        extractState();
    }

    private void extractState(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(JSON_URL, response -> {
            if (response != null) {
                Gson gson = new Gson();
                JSONArray jsonArray = response.optJSONArray("states");
                if (jsonArray != null) {
                    State[] state = gson.fromJson(jsonArray.toString(), State[].class);
                    for (State s: state) {
                        states.add(s);
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





//
//    private void extractState(){
//        RequestQueue queue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                for (int i = 0; i < response.length(); i++) {
//                    try {
//                        JSONObject stateObject = response.getJSONObject(i);
//                        State state = new State();
//                        state.setName(stateObject.getString("name").toString());
//                        states.add(state);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                adapter = new Adapter(getApplicationContext(), states);
//                recyclerView.setAdapter(adapter);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("tag", "onErrorResponse: " + error.getMessage());
//            }
//        });
//        queue.add(jsonArrayRequest);
//    }