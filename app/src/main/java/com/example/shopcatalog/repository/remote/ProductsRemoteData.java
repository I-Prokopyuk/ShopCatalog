package com.example.shopcatalog.repository.remote;

import android.util.Log;

import com.example.shopcatalog.common.Constants;
import com.example.shopcatalog.data.model.Product;
import com.example.shopcatalog.di.scopes.AppScoped;
import com.example.shopcatalog.repository.ProductsData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@AppScoped
public class ProductsRemoteData implements ProductsData {

    QueryLoadProducts queryLoadProducts;
    CompositeDisposable compositeDisposable;

    @Inject
    public ProductsRemoteData(QueryLoadProducts catalogQuery, CompositeDisposable compositeDisposable) {
        this.queryLoadProducts = catalogQuery;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void getProducts(String category, int startPosition, int loadSize, LoadProductsCallback loadProductsCallback) {

        compositeDisposable.add(queryLoadProducts.getProducts(category, startPosition, loadSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .retry(3)
                .flattenAsObservable(products -> products)
                .map(product -> {
                    product.setCategory(category);
                    return product;
                })
                .toList()
                .subscribe(productList -> {

                    loadProductsCallback.onResultCallback(productList);

                    Log.i(Constants.LOG_TAG, "ProductsRemoteData Remote loaded...");
                }, throwable -> Log.i(Constants.LOG_TAG, throwable.getMessage() + " Error remote connected! <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")));
    }

    @Override
    public void insertProduct(List<Product> productList) {

    }
}

