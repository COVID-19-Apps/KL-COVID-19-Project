package com.klcovid19project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.klcovid19project.Adapter.EpassAdapter;
import com.klcovid19project.Models.Epass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideosActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Epass> viewItems;

    private RecyclerView.Adapter mAdapter;
    private RequestQueue mRequestQueue;

    private ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        Back = findViewById(R.id.toolbar_icon);
        mRecyclerView = findViewById(R.id.video_list);
        mRecyclerView.setHasFixedSize(true);
        viewItems = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new EpassAdapter(VideosActivity.this, viewItems);
        mRecyclerView.setAdapter(mAdapter);

        mRequestQueue = Volley.newRequestQueue(this);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        parseJSON(url);
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
                                String name = hit.getString("name");
                                String link = hit.getString("link");

                                viewItems.add(new Epass(name, link));
                            }

                            mAdapter = new EpassAdapter(VideosActivity.this, viewItems);
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