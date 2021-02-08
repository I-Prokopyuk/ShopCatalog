package com.example.shopcatalog.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.example.shopcatalog.common.Constants;
import com.example.shopcatalog.data.model.Product;
import com.example.shopcatalog.di.scopes.AppScoped;
import com.example.shopcatalog.repository.remote.QueryLoadProducts;

import java.util.List;

import javax.inject.Inject;

@AppScoped
public class ProductsCatalogDataSource extends PositionalDataSource<Product> {

    @Inject
    QueryLoadProducts queryLoadProducts;

    ProductsCatalogRepository productsCatalogRepository;

    private String category;

    @Inject
    public ProductsCatalogDataSource(ProductsCatalogRepository productsCatalogRepository) {
        this.productsCatalogRepository = productsCatalogRepository;
        this.category = Constants.CATALOG_CATEGORY_PHONE;
    }

    public void giveCategory(String category) {
        this.category = category;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Product> callback) {

        Log.i(Constants.LOG_TAG, "loadInitial --->  " + params.requestedStartPosition + " , " + params.requestedLoadSize);

        productsCatalogRepository.getProducts(category, params.requestedStartPosition, params.requestedLoadSize, new ProductsData.LoadProductsCallback() {
            @Override
            public void onResultCallback(List<Product> products) {
                callback.onResult(products, 0);

                for (Product product : products) {
                    Log.i(Constants.LOG_TAG, product.getName() + " цена: " + product.getPrice() + " <<<<<<<<< loadInitial <<<<<<<<<");
                }
            }
        });
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Product> callback) {

        Log.i(Constants.LOG_TAG, "loadRange --->  " + params.startPosition + " , " + params.loadSize);

        productsCatalogRepository.getProducts(category, params.startPosition, params.loadSize, new ProductsData.LoadProductsCallback() {
            @Override
            public void onResultCallback(List<Product> products) {

                callback.onResult(products);

                for (Product product : products) {
                    Log.i(Constants.LOG_TAG, product.getName() + " цена: " + product.getPrice() + " <<<<<<<<< loadRange <<<<<<<<<");
                }
            }
        });
    }
}
