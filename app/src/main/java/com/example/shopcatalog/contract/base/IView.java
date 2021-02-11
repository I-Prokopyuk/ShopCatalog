package com.example.shopcatalog.contract.base;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.shopcatalog.data.model.Product;

public interface IView {

    void showProgressBar();

    void hideProgressBar();

    void showDisplayInfo(int imageResource, int stringResource);

    void hideDisplayInfo();

    void showProducts(LiveData<PagedList<Product>> pagedListLiveData);
}
