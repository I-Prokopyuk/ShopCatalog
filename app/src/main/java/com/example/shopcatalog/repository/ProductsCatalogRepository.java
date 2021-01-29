package com.example.shopcatalog.repository;

import com.example.shopcatalog.data.model.Product;
import com.example.shopcatalog.di.scopes.AppScoped;
import com.example.shopcatalog.di.scopes.Local;
import com.example.shopcatalog.di.scopes.Remote;
import com.example.shopcatalog.utils.OnlineConnectedStatus;

import java.util.List;

import javax.inject.Inject;

@AppScoped
public class ProductsCatalogRepository implements ProductsData {

    private ProductsData productsLocalData;
    private ProductsData productsRemoteData;
    private OnlineConnectedStatus onlineConnectedStatus;
    ProductsData productsData;

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
            }
        });
    }
}