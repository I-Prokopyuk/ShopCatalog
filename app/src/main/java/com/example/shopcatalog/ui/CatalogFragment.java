package com.example.shopcatalog.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopcatalog.R;
import com.example.shopcatalog.common.Constants;
import com.example.shopcatalog.contract.IContract;
import com.example.shopcatalog.data.model.Product;
import com.example.shopcatalog.utils.OnlineConnectedStatus;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CatalogFragment extends DaggerFragment implements IContract.View {

    @Inject
    OnlineConnectedStatus onlineConnectedStatus;

    @Inject
    CatalogPresenter catalogPresenter;

    private ProductsPagedListAdapter productsAdapter;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ImageView imageInfo;
    TextView textInfo;


    private Disposable internetDisposable;

    private boolean isFragmentCreated;

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
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        imageInfo = (ImageView) view.findViewById(R.id.image_info);
        textInfo = (TextView) view.findViewById(R.id.text_info);

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

                        catalogPresenter.loadProducts(Constants.CATALOG_CATEGORY_DEFAULT);

                        isFragmentCreated = true;
                    } else {

                        if ((onlineConnectedStatus.isOnlineConnected() != isConnected) && isFragmentCreated) {

                            onlineConnectedStatus.setStatusOnlineConnected(isConnected);

                            if (isConnected) {
                                Toast.makeText(getContext(), R.string.display_info_connection, Toast.LENGTH_SHORT).show();
                                catalogPresenter.invalidateDataSource();
                            } else
                                Toast.makeText(getContext(), R.string.display_info_no_connection, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        catalogPresenter.detachView();
        internetDisposable.dispose();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        catalogPresenter.destroy();
    }


    @Override
    public void showProducts(LiveData<PagedList<Product>> pagedListLiveData) {
        pagedListLiveData.observe(this, new Observer<PagedList<Product>>() {
            @Override
            public void onChanged(PagedList<Product> products) {
                productsAdapter.submitList(products);
            }
        });

    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showDisplayInfo(int imageResource, int stringResource) {
        imageInfo.setImageResource(imageResource);
        textInfo.setText(getString(stringResource));
        imageInfo.setVisibility(View.VISIBLE);
        textInfo.setVisibility(View.VISIBLE);
    }


    @Override
    public void hideDisplayInfo() {
        imageInfo.setVisibility(View.GONE);
        textInfo.setVisibility(View.GONE);
    }
}
