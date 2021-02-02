package com.example.shopcatalog.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopcatalog.R;
import com.example.shopcatalog.common.Constants;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WebViewActivity extends AppCompatActivity {

    private Disposable internetDisposable;
    WebView webView;
    String url;

    @Override
    public void onResume() {
        super.onResume();

        internetDisposable = ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnected -> {
                    if (isConnected) {
                        webView.setVisibility(View.VISIBLE);
                        webView.clearCache(true);
                        webView.loadUrl(url);
                    } else {
                        webView.setVisibility(View.INVISIBLE);
                        Toast.makeText(this, R.string.warning_no_connection, Toast.LENGTH_SHORT).show();
                    }
                });

        Log.i("myLogs", "WebViewActivity Resume.....");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = (WebView) findViewById(R.id.webView);

        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            url = Constants.CATALOG_API_BASE_URL;
        } else url = bundle.getString("url");
    }

    @Override
    public void onPause() {
        super.onPause();
        internetDisposable.dispose();
        Log.i("myLogs", "WebViewActivity pause.....");
    }
}