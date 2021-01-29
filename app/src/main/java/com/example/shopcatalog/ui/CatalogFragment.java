package com.example.shopcatalog.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopcatalog.R;
import com.example.shopcatalog.common.Constants;
import com.example.shopcatalog.contract.IContract;
import com.example.shopcatalog.data.model.Product;
import com.example.shopcatalog.executor.BackgroundThreadExecutor;
import com.example.shopcatalog.executor.UiThreadExecutor;
import com.example.shopcatalog.repository.ProductsCatalogDataSource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CatalogFragment extends DaggerFragment implements IContract.View {

    @Inject
    CatalogPresenter catalogPresenter;

    @Inject
    PagedList.Config config;

    @Inject
    ProductsCatalogDataSource productsCatalogDataSource;

    ProductsPagedListAdapter productsAdapter;

    PagedList<Product> pagedList;

    RecyclerView recyclerView;

    boolean isFragmentCreated;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        productsAdapter = new ProductsPagedListAdapter(Product.DIFF_CALLBACK);

        pagedList = new PagedList.Builder<>(productsCatalogDataSource, config)
                .setFetchExecutor(new BackgroundThreadExecutor())
                .setNotifyExecutor(new UiThreadExecutor())
                .build();

        Log.i(Constants.LOG_TAG, "Fragmnet onCreate " + isFragmentCreated);

        setRetainInstance(true);
    }

    public Product upgradeProduct(Product product) {
        product.setCategory("tv");
        return product;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.list);

        recyclerView.setAdapter(productsAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        catalogPresenter.attachView(this);

        if (!isFragmentCreated) {

            catalogPresenter.loadProducts(Constants.CATALOG_CATEGORY_PHONE);

            isFragmentCreated = true;
        }
        Log.i(Constants.LOG_TAG, "Fragmnet onResume " + isFragmentCreated);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        catalogPresenter.detachView();
    }


    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showProducts() {

//        pagedList = new PagedList.Builder<>(productsCatalogDataSource, config)
//                .setNotifyExecutor(new UiThreadExecutor())
//                .setFetchExecutor(new BackgroundThreadExecutor())
//                .build();

        Log.i(Constants.LOG_TAG, "Fragment showProducts PagedList.Builder");

        productsAdapter.submitList(pagedList);


    }
}