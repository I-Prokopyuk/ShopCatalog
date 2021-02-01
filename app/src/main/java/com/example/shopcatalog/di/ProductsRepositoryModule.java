package com.example.shopcatalog.di;

import androidx.paging.PagedList;

import com.example.shopcatalog.data.model.Product;
import com.example.shopcatalog.di.scopes.ActivityScoped;
import com.example.shopcatalog.di.scopes.AppScoped;
import com.example.shopcatalog.di.scopes.Local;
import com.example.shopcatalog.di.scopes.Remote;
import com.example.shopcatalog.repository.ProductsData;
import com.example.shopcatalog.repository.local.ProductDao;
import com.example.shopcatalog.repository.local.ProductsLocalData;
import com.example.shopcatalog.repository.remote.ProductsRemoteData;
import com.example.shopcatalog.repository.remote.QueryLoadProducts;

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
                .setPageSize(1)
                .build();
    }
}
