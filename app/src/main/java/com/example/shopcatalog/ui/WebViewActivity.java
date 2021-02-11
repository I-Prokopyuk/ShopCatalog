package com.example.shopcatalog.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shopcatalog.R;
import com.example.shopcatalog.common.Constants;
import com.example.shopcatalog.databinding.ActivityWebviewBinding;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WebViewActivity extends DaggerAppCompatActivity {

    private Disposable internetDisposable;
    private ActivityWebviewBinding binding;
    private String url;

    @Override
    protected void onResume() {
        super.onResume();

        internetDisposable = ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnected -> {
                            if (isConnected) {
                                binding.webView.setVisibility(View.VISIBLE);
                                binding.webView.clearCache(true);
                                binding.webView.loadUrl(url);
                            } else {
                                binding.webView.setVisibility(View.INVISIBLE);
                                Toast.makeText(this, R.string.display_info_no_connection, Toast.LENGTH_SHORT).show();
                            }
                        } //,throwable -> Log.i(Constants.LOG_TAG, throwable.getMessage() + "<<<Error remote connected!")
                );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            url = Constants.CATALOG_API_BASE_URL;
        } else url = bundle.getString("url");
    }

    @Override
    protected void onPause() {
        super.onPause();
        internetDisposable.dispose();
    }
}