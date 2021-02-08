package com.example.shopcatalog.ui;

import com.example.shopcatalog.contract.IContract;
import com.example.shopcatalog.contract.base.PresenterBase;
import com.example.shopcatalog.di.scopes.ActivityScoped;
import com.example.shopcatalog.repository.ProductsCatalogDataSource;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

@ActivityScoped
public class CatalogPresenter extends PresenterBase implements IContract.Presenter {

    @Inject
    CompositeDisposable compositeDisposable;

    @Inject
    ProductsCatalogDataSource productsCatalogDataSource;

    @Inject
    public CatalogPresenter() {

    }

    @Override
    public void viewIsReady() {

    }

    @Override
    public void detachView() {
        super.detachView();
        compositeDisposable.clear();
    }

    @Override
    public void destroy() {
        compositeDisposable.clear();
        compositeDisposable.dispose();
    }

    @Override
    public void loadProducts(String category) {
        if (compositeDisposable.size() > 0) compositeDisposable.dispose();
        getView().showProducts();
    }

    public void updatePageList() {
        getView().updatePageList();
    }
}
