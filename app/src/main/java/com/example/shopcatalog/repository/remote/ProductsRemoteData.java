package com.example.shopcatalog.repository.remote;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.shopcatalog.common.Constants;
import com.example.shopcatalog.data.model.Product;
import com.example.shopcatalog.di.scopes.AppScoped;
import com.example.shopcatalog.repository.ProductsData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@AppScoped
public class ProductsRemoteData implements ProductsData {

    @Inject
    QueryLoadProducts queryLoadProducts;

    @Inject
    public ProductsRemoteData(QueryLoadProducts catalogQuery) {
        this.queryLoadProducts = catalogQuery;

    }

    private static SingleSource<?> apply(List<Product> products) {
        return products;
    }

    private static ObservableSource<?> apply(Product product) {
        return product.setName("ref");
    }

    @Override
    public void getProducts(String category, int startPosition, int loadSize, LoadProductsCallback loadProductsCallback) {

        Single<List<Product>> singleProducts = queryLoadProducts.getProducts(category, startPosition, loadSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());


        Disposable disposable = singleProducts
                .flattenAsObservable(products -> products)
                .map(product -> product.setCategory(category))
                .toList()
                .subscribe(

                        productList -> {

                            loadProductsCallback.onResultCallback(productList);

                        }, throwable -> Log.i(Constants.LOG_TAG, throwable.getMessage()));
    }

    List<Product> modernProducts(List<Product> listProducts, String category) {

        List<Product> modernProducts = new ArrayList<>();

        for (Product product : listProducts) {
            product.setCategory(category);
            modernProducts.add(product);
        }
        return modernProducts;
    }
}

