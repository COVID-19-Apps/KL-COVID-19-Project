package com.klcovid19project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.klcovid19project.Adapter.TestLabsAdapter;
import com.klcovid19project.Adapter.TollNumbersAdapter;
import com.klcovid19project.Models.Test_Labs;
import com.klcovid19project.Models.Toll_Numbers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TollNumbersActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private List<Toll_Numbers> viewItems;

    private TollNumbersAdapter mAdapter;
    private RequestQueue mRequestQueue;

    private ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toll_numbers);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        Back = findViewById(R.id.toolbar_icon);
        mRecyclerView = findViewById(R.id.toll_numbers_list);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        viewItems = new ArrayList<>();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON(url);

        mAdapter = new TollNumbersAdapter(TollNumbersActivity.this, viewItems);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void parseJSON(String url1) {
        JsonObjectRequest request = new JsonObjectRequest(url1, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String district = hit.getString("district");
                                String phone_number = hit.getString("phone_number");

                                viewItems.add(new Toll_Numbers(district, phone_number));
                            }

                            mAdapter = new TollNumbersAdapter(TollNumbersActivity.this, viewItems);
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

        mRequestQueue.add(request);
    }

}