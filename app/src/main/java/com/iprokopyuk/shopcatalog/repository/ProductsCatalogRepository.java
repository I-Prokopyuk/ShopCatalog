package com.iprokopyuk.shopcatalog.repository;

import com.iprokopyuk.shopcatalog.data.model.Product;
import com.iprokopyuk.shopcatalog.di.scopes.AppScoped;
import com.iprokopyuk.shopcatalog.di.scopes.Local;
import com.iprokopyuk.shopcatalog.di.scopes.Remote;
import com.iprokopyuk.shopcatalog.utils.OnlineConnectedStatus;

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

        getProductsFromRepository(productsData, category, startPosition, loadSize, loadProductsCallback);
    }

    void getProductsFromRepository(ProductsData productsData, String category, int startPosition, int loadSize, LoadProductsCallback loadProductsCallback) {

        productsData.getProducts(category, startPosition, loadSize, new LoadProductsCallback() {
            @Override
            public void onResultCallback(List<Product> products) {
                loadProductsCallback.onResultCallback(products);

                if (!products.isEmpty() && onlineConnectedStatus.isOnlineConnected())
                    insertProduct(products);
            }

            @Override
            public void onErrorCallback() {
                // get from local DB
                getProductsFromRepository(productsLocalData, category, startPosition, loadSize, loadProductsCallback);
            }
        });
    }

    @Override
    public void insertProduct(List<Product> productList) {
        productsLocalData.insertProduct(productList);
    }
}
