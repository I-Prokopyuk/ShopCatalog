package com.example.shopcatalog.repository.local;

import com.example.shopcatalog.data.model.Product;
import com.example.shopcatalog.di.scopes.AppScoped;
import com.example.shopcatalog.repository.ProductsData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@AppScoped
public class ProductsLocalData implements ProductsData {

    ProductDao productDao;
    CompositeDisposable compositeDisposable;

    public ProductsLocalData(ProductDao productDao, CompositeDisposable compositeDisposable) {
        this.productDao = productDao;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void getProducts(String category, int startPosition, int loadSize, LoadProductsCallback loadProductsCallback) {
        compositeDisposable.add(productDao.getProducts(category, startPosition, loadSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(productList ->
                                loadProductsCallback.onResultCallback(productList)
                        //                ,throwable -> Log.i(Constants.LOG_TAG, throwable.getMessage())
                ));
    }

    @Override
    public void insertProduct(List<Product> productList) {
        compositeDisposable.add(Completable.fromAction(() -> productDao.insertProduct(productList))
                .subscribeOn(Schedulers.io())
                .subscribe());
    }
}
