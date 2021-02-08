package com.example.shopcatalog.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopcatalog.R;
import com.example.shopcatalog.common.Constants;
import com.example.shopcatalog.contract.IContract;
import com.example.shopcatalog.data.model.Product;
import com.example.shopcatalog.executor.BackgroundThreadExecutor;
import com.example.shopcatalog.repository.MySourceFactory;
import com.example.shopcatalog.repository.ProductsCatalogDataSource;
import com.example.shopcatalog.repository.ProductsCatalogRepository;
import com.example.shopcatalog.repository.local.ProductDao;
import com.example.shopcatalog.repository.remote.ProductsRemoteData;
import com.example.shopcatalog.utils.OnlineConnectedStatus;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CatalogFragment extends DaggerFragment implements IContract.View {

    @Inject
    CatalogPresenter catalogPresenter;

    @Inject
    PagedList.Config config;

    @Inject
    ProductsCatalogDataSource productsCatalogDataSource;

    @Inject
    OnlineConnectedStatus onlineConnectedStatus;

    ////
    @Inject
    ProductsCatalogRepository productsCatalogRepository;

    ///
    MySourceFactory mySourceFactory;

    ///
    @Inject
    ProductDao productDao;

    //
    @Inject
    ProductsRemoteData productsRemoteData;

    //
    LiveData<PagedList<Product>> pagedListLiveData;


    private ProductsPagedListAdapter productsAdapter;

    private PagedList<Product> pagedList;

    private RecyclerView recyclerView;

    private Disposable internetDisposable;

    boolean isFragmentCreated;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        productsAdapter = new ProductsPagedListAdapter(Product.DIFF_CALLBACK);

        setRetainInstance(true);
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

        internetDisposable = ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnected -> {

                    //default loaded

                    if (!isFragmentCreated) {

                        onlineConnectedStatus.setStatusOnlineConnected(isConnected);

                        catalogPresenter.loadProducts(Constants.CATALOG_CATEGORY_PHONE);

                        isFragmentCreated = true;
                    } else {


                        if ((onlineConnectedStatus.isOnlineConnected() != isConnected) && isFragmentCreated) {

                            onlineConnectedStatus.setStatusOnlineConnected(isConnected);

                            if (isConnected) {
                                Toast.makeText(getContext(), "Internet is connected", Toast.LENGTH_SHORT).show();
                                //pagedListLiveData.getValue().getDataSource().invalidate();
                                mySourceFactory.getProductsCatalogDataSource().invalidate();
                            } else
                                Toast.makeText(getContext(), R.string.warning_no_connection, Toast.LENGTH_SHORT).show();
                        }
                    }


                });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        catalogPresenter.detachView();
        Log.i(Constants.LOG_TAG, "Fragmnet Destroy View");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        catalogPresenter.destroy();
        Log.i(Constants.LOG_TAG, "Fragmnet Destroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        internetDisposable.dispose();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showProducts() {
        mySourceFactory = new MySourceFactory(productsCatalogRepository);

        LiveData<PagedList<Product>> pagedListLiveData = new LivePagedListBuilder<>(mySourceFactory, config)
                .setFetchExecutor(new BackgroundThreadExecutor())
                .build();


//        pagedListLiveData = new LivePagedListBuilder<>(productDao.getAll(Constants.CATALOG_CATEGORY_PHONE), config)
//                .setFetchExecutor(new BackgroundThreadExecutor())
//                .setBoundaryCallback(new PagedList.BoundaryCallback<Product>() {
//                    @Override
//                    public void onZeroItemsLoaded() {
//                        super.onZeroItemsLoaded();
//
//                        Log.i("myLogs", "Zero Items.........");
//
//                        productsRemoteData.getProducts(Constants.CATALOG_CATEGORY_PHONE, startPosition, 5, new ProductsData.LoadProductsCallback() {
//                            @Override
//                            public void onResultCallback(List<Product> products) {
//
//                                for (Product product : products) {
//
//                                    Log.i("myLogs", product.getName() + " " + product.getPrice() + " " + product.getCategory());
//                                }
//
//                                Completable.fromAction(() -> productDao.insertProduct(products))
//                                        .subscribeOn(Schedulers.io())
//                                        .subscribe();
//
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onItemAtFrontLoaded(Product itemAtFront) {
//                        super.onItemAtFrontLoaded(itemAtFront);
//                    }
//
//                    @Override
//                    public void onItemAtEndLoaded(Product itemAtEnd) {
//                        super.onItemAtEndLoaded(itemAtEnd);
//
//                        startPosition =startPosition + 5;
//
//
//                        Log.i("myLogs", "End Loaded.........startPosition: " + startPosition);
//
//                        productsRemoteData.getProducts(Constants.CATALOG_CATEGORY_PHONE, startPosition, 5, new ProductsData.LoadProductsCallback() {
//                            @Override
//                            public void onResultCallback(List<Product> products) {
//
//                                Completable.fromAction(() -> productDao.insertProduct(products))
//                                        .subscribeOn(Schedulers.io())
//                                        .subscribe();
//                            }
//                        });
//
//
//                    }
//                })
//                .build();


        pagedListLiveData.observe(this, new Observer<PagedList<Product>>() {
            @Override
            public void onChanged(PagedList<Product> products) {
                productsAdapter.submitList(products);
            }
        });


//        pagedList = new PagedList.Builder<>(productsCatalogDataSource, config)
//                .setNotifyExecutor(new UiThreadExecutor())
//                .setFetchExecutor(new BackgroundThreadExecutor())
//                .build();
//
//        productsAdapter.submitList(pagedList);
    }

    @Override
    public void updatePageList() {

    }

}



