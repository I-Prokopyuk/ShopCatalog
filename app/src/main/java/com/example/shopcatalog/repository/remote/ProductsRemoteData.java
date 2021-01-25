package com.example.shopcatalog.repository.remote;

import android.util.Log;

import com.example.shopcatalog.common.Constants;
import com.example.shopcatalog.di.scopes.AppScoped;
import com.example.shopcatalog.repository.ProductsData;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@AppScoped
public class ProductsRemoteData implements ProductsData {

    @Inject
    QueryLoadProducts queryLoadProducts;

    @Inject
    public ProductsRemoteData(QueryLoadProducts catalogQuery) {
        this.queryLoadProducts = catalogQuery;

    }

    @Override
    public void getProducts(String category, int startPosition, int loadSize, LoadProductsCallback loadProductsCallback) {

        queryLoadProducts.getProducts(category, startPosition, loadSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(productList -> {

                    loadProductsCallback.onResultCallback(productList);

                }, throwable -> Log.i(Constants.LOG_TAG, throwable.getMessage()));
    }
}
