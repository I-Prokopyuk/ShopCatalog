package com.example.shopcatalog.ui;

import android.os.Bundle;
import android.util.Log;

import com.example.shopcatalog.R;
import com.example.shopcatalog.utils.OnlineConnectedStatus;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Observable;

public class WebViewActivity extends DaggerAppCompatActivity {

    @Inject
    OnlineConnectedStatus onlineConnectedStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        Observable.just(onlineConnectedStatus.isOnlineConnected())
                .subscribe(aBoolean -> Log.i("myLogs","Connection "+aBoolean));

//        WebView webView = (WebView) findViewById(R.id.webView);
//
//        if (onlineConnectedStatus.isOnlineConnected()) {
//
//            Bundle bundle = getIntent().getExtras();
//
//            String url;
//
//            if (bundle == null) {
//                url = Constants.CATALOG_API_BASE_URL;
//            } else url = bundle.getString("url");
//
//            webView.clearCache(true);
//            webView.loadUrl(url);
//
//            Log.i("myLogs", url + " <<<<<<<< Full url");
//        } else {
//            webView.setVisibility(View.INVISIBLE);
//            Snackbar.make(findViewById(R.id.parent_layout), R.string.warning_no_connection, Snackbar.LENGTH_INDEFINITE).show();
//        }
    }
}