package com.example.shopcatalog.contract.base;

public interface IView {

    void showProgressBar();

    void hideProgressBar();

    //void showProducts(ProductsCatalogDataSource productsCatalogDataSource);
    void showProducts();

    void updatePageList();
}
