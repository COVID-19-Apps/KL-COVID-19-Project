package com.klcovid19project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.klcovid19project.Adapter.GridAdapter;
import com.klcovid19project.Services.WebViewActivity;

public class OnlineEducationActivity extends AppCompatActivity {

    private GridView Grid_View;

    String[] web = {
            "AP Stateboard",
            "CBSE",
            "Online Vocational Courses"

    };
    int[] imageId = {
            R.drawable.book,
            R.drawable.book,
            R.drawable.book

    };

    private ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_education);

        Intent intent = getIntent();
        final String sb = intent.getStringExtra("sb");
        final String cbse = intent.getStringExtra("cbse");
        final String vc = intent.getStringExtra("vc");

        Back = findViewById(R.id.toolbar_icon);

        Grid_View = findViewById(R.id.grid_view);
        GridAdapter adapter = new GridAdapter(OnlineEducationActivity.this, web, imageId);
        Grid_View.setAdapter(adapter);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Grid_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent1 = new Intent(Intent.ACTION_VIEW);
                        intent1.setData(Uri.parse(sb));
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent(Intent.ACTION_VIEW);
                        intent2.setData(Uri.parse(cbse));
                        startActivity(intent2);
                        break;

                    case 2:
                        Intent intent3 = new Intent(Intent.ACTION_VIEW);
                        intent3.setData(Uri.parse(vc));
                        startActivity(intent3);
                        break;

                }

            }
        });
    }
}
