package com.example.shopcatalog.repository;

import android.util.Log;

import com.example.shopcatalog.data.model.Product;
import com.example.shopcatalog.di.scopes.AppScoped;
import com.example.shopcatalog.di.scopes.Local;
import com.example.shopcatalog.di.scopes.Remote;
import com.example.shopcatalog.utils.OnlineConnectedStatus;

import java.util.List;

import javax.inject.Inject;

@AppScoped
public class ProductsCatalogRepository implements ProductsData {

    private final ProductsData productsLocalData;
    private ProductsData productsRemoteData;
    private OnlineConnectedStatus onlineConnectedStatus;
    private ProductsData productsData;

    @Inject
    public ProductsCatalogRepository(@Remote ProductsData productsRemoteData, @Local ProductsData productsLocalData, OnlineConnectedStatus onlineConnectedStatus) {
        this.productsRemoteData = productsRemoteData;
        this.productsLocalData = productsLocalData;
        this.onlineConnectedStatus = onlineConnectedStatus;
    }

    @Override
    public void getProducts(String category, int startPosition, int loadSize, LoadProductsCallback loadProductsCallback) {

        productsData = onlineConnectedStatus.isOnlineConnected() ? productsRemoteData : productsLocalData;

        productsData.getProducts(category, startPosition, loadSize, new LoadProductsCallback() {
            @Override
            public void onResultCallback(List<Product> products) {

                loadProductsCallback.onResultCallback(products);

                if (products.isEmpty() && !onlineConnectedStatus.isOnlineConnected())
                    Log.i("myLogs", "Нет подключения к Интернету!");

                if (products.isEmpty() && onlineConnectedStatus.isOnlineConnected())
                    Log.i("myLogs", "Нет товаров в данной категории!");

                if (!products.isEmpty() && onlineConnectedStatus.isOnlineConnected()) {
                    insertProduct(products);
                }
            }
        });
    }

    @Override
    public void insertProduct(List<Product> productList) {
        productsLocalData.insertProduct(productList);
    }
}