package com.example.shopcatalog.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.example.shopcatalog.common.Constants;
import com.example.shopcatalog.data.model.Product;

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

        Log.i(Constants.LOG_TAG, "loadInitial --->  " + params.requestedStartPosition + " , " + params.requestedLoadSize);

        Log.i(Constants.LOG_TAG, "My loadInitial --->  " + loadRangestartPosition + " , " + params.requestedLoadSize);

        productsCatalogRepository.getProducts(category, loadRangestartPosition, params.requestedLoadSize, new ProductsData.LoadProductsCallback() {
            @Override
            public void onResultCallback(List<Product> products) {
                callback.onResult(products, 0);

                for (Product product : products) {
                    Log.i("myLogs", product.getName());
                }


                Log.i("myLogs", "loadInitial onResultCallback");
            }

            @Override
            public void onErrorCallback() {

            }
        });

        loadRangestartPosition = params.requestedStartPosition + params.requestedLoadSize;
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Product> callback) {


        Log.i(Constants.LOG_TAG, "loadRange --->  " + params.startPosition + " , " + params.loadSize);

        Log.i(Constants.LOG_TAG, "My loadRange --->  " + loadRangestartPosition + " , " + params.loadSize);

        productsCatalogRepository.getProducts(category, loadRangestartPosition, params.loadSize, new ProductsData.LoadProductsCallback() {
            @Override
            public void onResultCallback(List<Product> products) {

                //           if (category == "tv") products = new ArrayList<>();

                callback.onResult(products);
                Log.i("myLogs", "loadRange onResultCallback");
            }

            @Override
            public void onErrorCallback() {

            }
        });

        loadRangestartPosition = loadRangestartPosition + params.loadSize;
    }
}
