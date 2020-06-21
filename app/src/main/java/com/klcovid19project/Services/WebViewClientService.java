package com.klcovid19project.Services;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class WebViewClientService extends WebViewClient {

    private Activity activity = null;

    public WebViewClientService(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        webView.loadUrl(url);
        return true;
    }

}