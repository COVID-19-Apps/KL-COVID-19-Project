package com.klcovid19project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;

public class ImageDetailedActivity extends AppCompatActivity {

    private ImageView Image_Detailed, Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detailed);

        Intent intent = getIntent();
        String image = intent.getStringExtra("image");

        Image_Detailed = findViewById(R.id.image);
        Back= findViewById(R.id.toolbar_icon);
        Glide.with(ImageDetailedActivity.this)
                .load(image)
                .into(Image_Detailed);
        Image_Detailed.setOnTouchListener(new ImageMatrixTouchHandler(ImageDetailedActivity.this));

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
