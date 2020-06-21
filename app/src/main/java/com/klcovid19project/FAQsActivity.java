package com.klcovid19project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.klcovid19project.Adapter.FAQAdapter;
import com.klcovid19project.Adapter.TestLabsAdapter;
import com.klcovid19project.Models.FAQ;
import com.klcovid19project.Models.Test_Labs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FAQsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<FAQ> viewItems;

    private RecyclerView.Adapter mAdapter;
    private RequestQueue mRequestQueue;

    private ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_qs);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        Back = findViewById(R.id.toolbar_icon);
        mRecyclerView = findViewById(R.id.faq_list);
        mRecyclerView.setHasFixedSize(true);
        viewItems = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FAQAdapter(FAQsActivity.this, viewItems);
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
                                String question = hit.getString("question");
                                String answer = hit.getString("answer");

                                viewItems.add(new FAQ(question, answer));
                            }

                            mAdapter = new FAQAdapter(FAQsActivity.this, viewItems);
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
