package com.iprokopyuk.shopcatalog.di;

import androidx.paging.PagedList;

import com.iprokopyuk.shopcatalog.di.scopes.AppScoped;
import com.iprokopyuk.shopcatalog.di.scopes.Local;
import com.iprokopyuk.shopcatalog.di.scopes.Remote;
import com.iprokopyuk.shopcatalog.repository.ProductsData;
import com.iprokopyuk.shopcatalog.repository.local.ProductDao;
import com.iprokopyuk.shopcatalog.repository.local.ProductsLocalData;
import com.iprokopyuk.shopcatalog.repository.remote.ProductsRemoteData;
import com.iprokopyuk.shopcatalog.repository.remote.QueryLoadProducts;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module(includes = {ProductsRemoteModule.class, ProductsLocalModule.class})
public class ProductsRepositoryModule {

    @Provides
    @Remote
    @AppScoped
    ProductsData provideCatalogRemoteData(QueryLoadProducts catalogQuery, CompositeDisposable compositeDisposable) {
        return new ProductsRemoteData(catalogQuery, compositeDisposable);
    }

    @Provides
    @Local
    @AppScoped
    ProductsData provideCatalogLocakData(ProductDao productDao, CompositeDisposable compositeDisposable) {
        return new ProductsLocalData(productDao, compositeDisposable);
    }

    @Provides
    @AppScoped
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @AppScoped
    PagedList.Config providePagedListConfig() {
        return new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(15)
                .build();
    }
}