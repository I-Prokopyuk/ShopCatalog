package com.example.shopcatalog.contract.base;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.shopcatalog.data.model.Product;

public interface IView {

    void showProgressBar();

    void hideProgressBar();

    void showProducts(LiveData<PagedList<Product>> pagedListLiveData);
}
