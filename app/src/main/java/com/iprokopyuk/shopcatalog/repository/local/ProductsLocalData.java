package com.iprokopyuk.shopcatalog.repository.local;

import com.iprokopyuk.shopcatalog.data.model.Product;
import com.iprokopyuk.shopcatalog.di.scopes.AppScoped;
import com.iprokopyuk.shopcatalog.repository.ProductsData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@AppScoped
public class ProductsLocalData implements ProductsData {

    private ProductDao productDao;
    private CompositeDisposable compositeDisposable;

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
                        //                ,throwable -> Log.d(Constants.LOG_TAG, throwable.getMessage())
                ));
    }

    @Override
    public void insertProduct(List<Product> productList) {
        compositeDisposable.add(Completable.fromAction(() -> productDao.insertProduct(productList))
                .subscribeOn(Schedulers.io())
                .subscribe());
    }
}
