package com.example.shopcatalog.utils;

import com.example.shopcatalog.di.scopes.AppScoped;

import javax.inject.Inject;

@AppScoped
public class OnlineConnectedStatus {

    private boolean onlineConnected;

    @Inject
    public OnlineConnectedStatus() {

    }

//    private final ConnectivityManager connectivityManager;
//
//    public OnlineConnectedStatus(ConnectivityManager connectivityManager) {
//        this.connectivityManager = connectivityManager;
//    }
//
//    public Boolean isOnlineConnected() {
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        return networkInfo != null && networkInfo.isConnectedOrConnecting();
//    }

    //using library Reactivenetwork, save the state of your Internet connection

    public void setStatusOnlineConnected(Boolean onlineConnected) {
        this.onlineConnected = onlineConnected;
    }

    public Boolean isOnlineConnected() {
        return onlineConnected;
    }
}
