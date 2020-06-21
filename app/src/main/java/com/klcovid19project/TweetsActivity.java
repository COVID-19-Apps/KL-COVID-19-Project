package com.klcovid19project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.klcovid19project.Services.WebViewClientService;

public class TweetsActivity extends AppCompatActivity {

    private  WebView webView;
    private ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        Back = findViewById(R.id.toolbar_icon);

        webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClientService(this));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData(url,"text/html; charset=utf-8", "utf-8");
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TweetsActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
