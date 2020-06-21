package com.klcovid19project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.klcovid19project.Adapter.ImageSliderAdapter;
import com.klcovid19project.Adapter.LinkAdapter;
import com.klcovid19project.Models.Image_Slider;
import com.klcovid19project.Models.Link;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeTreamentActivity extends AppCompatActivity {

    private SliderView sliderView;
    ImageSliderAdapter imageSliderAdapter;
    LinkAdapter linkAdapter;
    private RecyclerView Link_List;
    private List<Image_Slider> image_sliders;
    private List<Link> links;
    private RequestQueue mRequestQueue;

    private FloatingActionButton Call;
    private ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_treament);

        Intent intent = getIntent();
        String image_url = intent.getStringExtra("image_url");
        String link_url = intent.getStringExtra("link_url");

        Back = findViewById(R.id.toolbar_icon);
        Call=findViewById(R.id.call);
        sliderView = findViewById(R.id.image_slider);
        Link_List = findViewById(R.id.link_list);
        Link_List.setHasFixedSize(true);

        image_sliders = new ArrayList<>();
        links = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        Link_List.setLayoutManager(mLayoutManager);

        imageSliderAdapter = new ImageSliderAdapter(HomeTreamentActivity.this, image_sliders);
        sliderView.setSliderAdapter(imageSliderAdapter);

        linkAdapter = new LinkAdapter(HomeTreamentActivity.this, links);
        Link_List.setAdapter(linkAdapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.BLUE);
        sliderView.setScrollTimeInSec(4);
        sliderView.startAutoCycle();

        Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0471-2552056"));
                startActivity(intent);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSONImages(image_url);
        parseJSONLinks(link_url);
    }

    private void parseJSONImages(String image_url) {
        JsonObjectRequest request = new JsonObjectRequest(image_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String image = hit.getString("image");

                                image_sliders.add(new Image_Slider(image));
                            }

                            imageSliderAdapter = new ImageSliderAdapter(HomeTreamentActivity.this, image_sliders);
                            sliderView.setSliderAdapter(imageSliderAdapter);
                            imageSliderAdapter.notifyDataSetChanged();


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

    private void parseJSONLinks(String link_url) {
        JsonObjectRequest request = new JsonObjectRequest(link_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray2 = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray2.length(); i++) {
                                JSONObject hit = jsonArray2.getJSONObject(i);
                                String title = hit.getString("title");
                                String link = hit.getString("link");

                                links.add(new Link(title, link));
                            }

                            linkAdapter = new LinkAdapter(HomeTreamentActivity.this, links);
                            Link_List.setAdapter(linkAdapter);
                            linkAdapter.notifyDataSetChanged();

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
