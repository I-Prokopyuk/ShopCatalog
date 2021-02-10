package com.example.shopcatalog.ui;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.shopcatalog.contract.IContract;
import com.example.shopcatalog.contract.base.PresenterBase;
import com.example.shopcatalog.data.model.Product;
import com.example.shopcatalog.di.scopes.ActivityScoped;
import com.example.shopcatalog.executor.BackgroundThreadExecutor;
import com.example.shopcatalog.repository.MySourceFactory;
import com.example.shopcatalog.utils.OnlineConnectedStatus;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

@ActivityScoped
public class CatalogPresenter extends PresenterBase implements IContract.Presenter {

    @Inject
    CompositeDisposable compositeDisposable;

    @Inject
    PagedList.Config config;

    @Inject
    MySourceFactory mySourceFactory;

    @Inject
    OnlineConnectedStatus onlineConnectedStatus;

    @Inject
    public CatalogPresenter() {

    }

    @Override
    public void detachView() {
        super.detachView();
        clearCompositeDisposable();
    }

    @Override
    public void destroy() {
        clearCompositeDisposable();
        compositeDisposable.dispose();
    }

    @Override
    public void loadProducts(String category) {

        getView().showProgressBar();

        if (compositeDisposable.size() > 0) clearCompositeDisposable();

        mySourceFactory.setCategory(category);

        LiveData<PagedList<Product>> pagedListLiveData = new LivePagedListBuilder<>(mySourceFactory, config)
                .setFetchExecutor(new BackgroundThreadExecutor())
                .setBoundaryCallback(new PagedList.BoundaryCallback<Product>() {
                    @Override
                    public void onZeroItemsLoaded() {
                        super.onZeroItemsLoaded();
//
//                        if (onlineConnectedStatus.isOnlineConnected()) {
//
//                        } else


                    }
                })
                .build();

        getView().showProducts(pagedListLiveData);
    }

    @Override
    public void invalidateDataSource() {
        mySourceFactory.getCatalogDataSource().invalidate();
    }

    @Override
    public void clearCompositeDisposable() {
        compositeDisposable.clear();
    }
}
