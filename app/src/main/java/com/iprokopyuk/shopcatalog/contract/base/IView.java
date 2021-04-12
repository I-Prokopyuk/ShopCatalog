package com.iprokopyuk.shopcatalog.contract.base;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.iprokopyuk.shopcatalog.data.model.Product;

public interface IView {

    void showProgressBar();

    void hideProgressBar();

    void showDisplayInfo(int imageResource, int stringResource);

    void hideDisplayInfo();

    void showProducts(LiveData<PagedList<Product>> pagedListLiveData);
}
