package com.klcovid19project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.klcovid19project.Adapter.CoronaAdapter;
import com.klcovid19project.Adapter.FAQAdapter;
import com.klcovid19project.Models.Corona;
import com.klcovid19project.Models.FAQ;
import com.klcovid19project.Models.Persons;
import com.google.firebase.database.DataSnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CoronaActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Corona> viewItems;

    private CoronaAdapter mAdapter;
    private RequestQueue mRequestQueue;

    private ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corona);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        Back= findViewById(R.id.toolbar_icon);
        mRecyclerView = findViewById(R.id.corona_list);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        viewItems = new ArrayList<>();

        mAdapter = new CoronaAdapter(CoronaActivity.this, viewItems);
        mRecyclerView.setAdapter(mAdapter);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON(url);
    }

    private void parseJSON(String url1) {

        JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET,url1, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("Kerala").getJSONObject("districtData");
                            Log.i("jsonresponse","hi");
                            Iterator iterator = jsonObject.keys();
                            while (iterator.hasNext()) {
                                String key = (String) iterator.next();
                                viewItems.add(new Corona(key, jsonObject.get(key).toString().split(",")[2].split(":")[1]));
                            }
                            mAdapter = new CoronaAdapter(CoronaActivity.this, viewItems);
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request1);

    }

}
