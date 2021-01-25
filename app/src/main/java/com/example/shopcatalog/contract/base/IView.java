package com.example.shopcatalog.contract.base;

import com.example.shopcatalog.repository.ProductsCatalogDataSource;

public interface IView {

    void showProgressBar();

    void hideProgressBar();

    void showProducts(ProductsCatalogDataSource productsCatalogDataSource);
}
