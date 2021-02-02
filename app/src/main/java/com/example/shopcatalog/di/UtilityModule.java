package com.example.shopcatalog.di;

import android.content.Context;
import android.net.ConnectivityManager;

import com.example.shopcatalog.app.Application;
import com.example.shopcatalog.di.scopes.AppScoped;
import com.example.shopcatalog.utils.FullUrl;
import com.example.shopcatalog.utils.OnlineConnectedStatus;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilityModule {

    @AppScoped
    @Provides
    ConnectivityManager provideConnectivityManager(Application application) {
        return (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @AppScoped
    @Provides
    OnlineConnectedStatus provideStatusOnlineConnected(ConnectivityManager connectivityManager) {
        return new OnlineConnectedStatus(connectivityManager);
    }

    @AppScoped
    @Provides
    Picasso providePicasso(Application application) {
        return Picasso.get();
    }
}
