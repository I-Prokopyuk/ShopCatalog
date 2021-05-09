package com.iprokopyuk.shopcatalog.repository;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.iprokopyuk.shopcatalog.data.model.Product;

import java.util.List;

public class ProductsCatalogDataSource extends PositionalDataSource<Product> {

    private ProductsCatalogRepository productsCatalogRepository;
    private String category;
    private int loadRangestartPosition;

    public ProductsCatalogDataSource(ProductsCatalogRepository productsCatalogRepository, String category) {
        this.productsCatalogRepository = productsCatalogRepository;
        this.category = category;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Product> callback) {

        if (params.requestedStartPosition > 0)
            loadRangestartPosition = params.requestedStartPosition;

        productsCatalogRepository.getProducts(category, loadRangestartPosition, params.requestedLoadSize, new ProductsData.LoadProductsCallback() {
            @Override
            public void onResultCallback(List<Product> products) {
                callback.onResult(products, 0);
            }

            @Override
            public void onErrorCallback() {

            }
        });

        loadRangestartPosition = params.requestedStartPosition + params.requestedLoadSize;
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Product> callback) {

        productsCatalogRepository.getProducts(category, loadRangestartPosition, params.loadSize, new ProductsData.LoadProductsCallback() {
            @Override
            public void onResultCallback(List<Product> products) {
                callback.onResult(products);
            }

            @Override
            public void onErrorCallback() {

            }
        });

        loadRangestartPosition = loadRangestartPosition + params.loadSize;
    }
}
