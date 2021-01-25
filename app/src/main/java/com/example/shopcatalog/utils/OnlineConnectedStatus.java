package com.example.shopcatalog.utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class OnlineConnectedStatus {

    private final ConnectivityManager connectivityManager;

    public OnlineConnectedStatus(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

    public Boolean isOnlineConnected() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
