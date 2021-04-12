package com.iprokopyuk.shopcatalog.repository.remote;

import com.iprokopyuk.shopcatalog.data.model.Product;
import com.iprokopyuk.shopcatalog.di.scopes.AppScoped;
import com.iprokopyuk.shopcatalog.repository.ProductsData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@AppScoped
public class ProductsRemoteData implements ProductsData {

    private QueryLoadProducts queryLoadProducts;
    private CompositeDisposable compositeDisposable;

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
                .retry(2)
                .flattenAsObservable(products -> products)
                .map(product -> {
                    product.setCategory(category);
                    return product;
                })
                .toList()
                .subscribe(productList -> loadProductsCallback.onResultCallback(productList),
                        throwable -> {
                            loadProductsCallback.onErrorCallback();
                            //Log.d(Constants.LOG_TAG, throwable.getMessage());
                        }
                ));
    }

    @Override
    public void insertProduct(List<Product> productList) {

    }
}

