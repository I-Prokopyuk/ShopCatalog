package com.example.shopcatalog.ui;

import android.util.Log;

import com.example.shopcatalog.common.Constants;
import com.example.shopcatalog.contract.IContract;
import com.example.shopcatalog.contract.base.PresenterBase;
import com.example.shopcatalog.di.scopes.ActivityScoped;
import com.example.shopcatalog.repository.ProductsCatalogDataSource;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

import static com.example.shopcatalog.common.Constants.LOG_TAG;

@ActivityScoped
public class CatalogPresenter extends PresenterBase implements IContract.Presenter {

    CompositeDisposable compositeDisposable;

    @Inject
    ProductsCatalogDataSource productsCatalogDataSource;


    @Inject
    public CatalogPresenter() {

        Log.i(Constants.LOG_TAG, "create Presenter");

    }

    @Override
    public void viewIsReady() {
//???
    }

    @Override
    public void destroy() {
//???
    }

    @Override
    public void detachView() {
        super.detachView();

        compositeDisposable.clear();
        compositeDisposable.dispose();

        Log.i(LOG_TAG, "Delete obsever");
    }

    @Override
    public void loadProducts(String category) {
        productsCatalogDataSource.setCategory(category);
        getView().showProducts(productsCatalogDataSource);
    }
}
