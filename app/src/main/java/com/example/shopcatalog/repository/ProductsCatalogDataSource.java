package com.example.shopcatalog.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.example.shopcatalog.common.Constants;
import com.example.shopcatalog.data.model.Product;
import com.example.shopcatalog.di.scopes.ActivityScoped;
import com.example.shopcatalog.repository.remote.QueryLoadProducts;

import java.util.List;

import javax.inject.Inject;

@ActivityScoped
public class ProductsCatalogDataSource extends PositionalDataSource<Product> {

    @Inject
    QueryLoadProducts queryLoadProducts;

    ProductsCatalogRepository productsCatalogRepository;

    private String category;

    @Inject
    public ProductsCatalogDataSource(ProductsCatalogRepository productsCatalogRepository) {
        this.productsCatalogRepository = productsCatalogRepository;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Product> callback) {

        productsCatalogRepository.getProducts(category, params.requestedStartPosition, params.requestedLoadSize, new ProductsData.LoadProductsCallback() {
            @Override
            public void onResultCallback(List<Product> products) {
                callback.onResult(products, 0);

                for (Product product : products) {
                    Log.i(Constants.LOG_TAG, product.getName() + " цена: " + product.getPrice() + " <<<<<<<<< loadInitial <<<<<<<<<");
                }
            }
        });
//
//
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
//        Gson gson = gsonBuilder.create();
//
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .baseUrl(Constants.CATALOG_API_BASE_URL)
//                .build();
//
//        QueryLoadProducts queryLoadProducts1 = retrofit.create(QueryLoadProducts.class);
//
//        List<Product> result = new ArrayList<>();
//
//        queryLoadProducts1.getProducts("tv", params.requestedStartPosition, params.requestedLoadSize)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribe(productList -> {
//
//
//
////                    for (Product product : productList) {
////                        Log.i(Constants.LOG_TAG, product.getName() + " цена: " + product.getPrice()+"****");
////                    }
//                }, throwable -> Log.i(Constants.LOG_TAG, throwable.getMessage()));


    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Product> callback) {

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
