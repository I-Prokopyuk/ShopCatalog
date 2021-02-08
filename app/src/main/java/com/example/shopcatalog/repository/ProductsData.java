package com.example.shopcatalog.repository;

import com.example.shopcatalog.data.model.Product;

import java.util.List;

public interface ProductsData {

    interface LoadProductsCallback {
        void onResultCallback(List<Product> products);
    }

    void getProducts(String category, int startPosition, int loadSize, LoadProductsCallback loadProductsCallback);

    void insertProduct(List<Product> productList);
}
