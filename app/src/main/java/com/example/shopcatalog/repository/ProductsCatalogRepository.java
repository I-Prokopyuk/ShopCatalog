package com.example.shopcatalog.repository;

import android.util.Log;

import com.example.shopcatalog.common.Constants;
import com.example.shopcatalog.data.model.Product;
import com.example.shopcatalog.di.scopes.ActivityScoped;
import com.example.shopcatalog.di.scopes.AppScoped;
import com.example.shopcatalog.di.scopes.Remote;
import com.example.shopcatalog.utils.OnlineConnectedStatus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@AppScoped
public class ProductsCatalogRepository implements ProductsData {

    private ProductsData productsRemoteData;
    private OnlineConnectedStatus onlineConnectedStatus;

    @Inject
    public ProductsCatalogRepository(@Remote ProductsData productsRemoteData, OnlineConnectedStatus onlineConnectedStatus) {
        this.productsRemoteData = productsRemoteData;
        this.onlineConnectedStatus = onlineConnectedStatus;
    }

    @Override
    public void getProducts(String category, int startPosition, int loadSize, LoadProductsCallback loadProductsCallback) {

        if (onlineConnectedStatus.isOnlineConnected()) {

            productsRemoteData.getProducts(category, startPosition, loadSize, new LoadProductsCallback() {
                @Override
                public void onResultCallback(List<Product> products) {
                    loadProductsCallback.onResultCallback(products);
                }
            });

        } else {

            //local load data
        }
    }
}