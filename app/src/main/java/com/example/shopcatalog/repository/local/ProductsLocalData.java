package com.example.shopcatalog.repository.local;

import android.util.Log;

import com.example.shopcatalog.common.Constants;
import com.example.shopcatalog.data.model.Product;
import com.example.shopcatalog.di.scopes.AppScoped;
import com.example.shopcatalog.repository.ProductsData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@AppScoped
public class ProductsLocalData implements ProductsData {

    ProductDao productDao;

    public ProductsLocalData(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void getProducts(String category, int startPosition, int loadSize, LoadProductsCallback loadProductsCallback) {

        productDao.getProducts(category, startPosition, loadSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(productList -> {

                    loadProductsCallback.onResultCallback(productList);

                    Log.i(Constants.LOG_TAG, "ProductsLocalData Local loaded...");

                }, throwable -> Log.i(Constants.LOG_TAG, throwable.getMessage() + "Error local loaded"));
    }


    public void insertProduct(List<Product> productList) {

        Completable.fromAction(() -> productDao.insertProduct(productList))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
