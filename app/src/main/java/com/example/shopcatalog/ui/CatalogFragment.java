package com.example.shopcatalog.ui;

import android.annotation.SuppressLint;
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

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class CatalogFragment extends DaggerFragment implements IContract.View {

    @Inject
    CatalogPresenter catalogPresenter;


    ProductsPagedListAdapter productsAdapter;

    PagedList.Config config;

    PagedList<Product> pagedList;

    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(10)
                .build();

        productsAdapter = new ProductsPagedListAdapter(null);


        //setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.list);

        recyclerView.setAdapter(productsAdapter);

        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onResume() {
        super.onResume();
        catalogPresenter.attachView(this);


        Log.i(Constants.LOG_TAG, "Fragmnet onResume");

        catalogPresenter.loadProducts(Constants.CATALOG_CATEGORY_PHONE);


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
    public void showProducts(ProductsCatalogDataSource productsCatalogDataSource) {

        pagedList = new PagedList.Builder<>(productsCatalogDataSource, config)
                .setNotifyExecutor(new UiThreadExecutor())
                .setFetchExecutor(new BackgroundThreadExecutor())
                .build();

        Log.i(Constants.LOG_TAG, "Fragment showProducts PagedList.Builder");

        productsAdapter.submitList(pagedList);


    }
}