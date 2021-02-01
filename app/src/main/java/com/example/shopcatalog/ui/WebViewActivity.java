package com.example.shopcatalog.ui;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopcatalog.R;
import com.example.shopcatalog.common.Constants;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        Bundle bundle = getIntent().getExtras();

        String url;

        if (bundle == null) {
            url = Constants.CATALOG_API_BASE_URL;
        } else url = bundle.getString("url");

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(url);
    }
}