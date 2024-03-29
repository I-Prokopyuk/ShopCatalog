package com.iprokopyuk.shopcatalog.ui;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.iprokopyuk.shopcatalog.R;
import com.iprokopyuk.shopcatalog.contract.IContract;
import com.iprokopyuk.shopcatalog.contract.base.PresenterBase;
import com.iprokopyuk.shopcatalog.data.model.Product;
import com.iprokopyuk.shopcatalog.di.scopes.ActivityScoped;
import com.iprokopyuk.shopcatalog.executor.BackgroundThreadExecutor;
import com.iprokopyuk.shopcatalog.repository.MySourceFactory;
import com.iprokopyuk.shopcatalog.utils.OnlineConnectedStatus;

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
    public void loadProducts(String category) {
        getView().showProgressBar();
        getView().hideDisplayInfo();

        if (compositeDisposable.size() > 0) clearCompositeDisposable();

        mySourceFactory.setCategory(category);

        LiveData<PagedList<Product>> pagedListLiveData = new LivePagedListBuilder<>(mySourceFactory, config)
                .setFetchExecutor(new BackgroundThreadExecutor())
                .setBoundaryCallback(new PagedList.BoundaryCallback<Product>() {
                    @Override
                    public void onZeroItemsLoaded() {
                        super.onZeroItemsLoaded();
                        getView().hideProgressBar();
                        if (onlineConnectedStatus.isOnlineConnected())
                            getView().showDisplayInfo(R.drawable.ic_baseline_cloud_off_150, R.string.display_info_error);
                        else
                            getView().showDisplayInfo(R.drawable.ic_baseline_cloud_off_150, R.string.display_info_no_connection);
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
}
