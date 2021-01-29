package com.example.shopcatalog.di;

import androidx.paging.PagedList;

import com.example.shopcatalog.data.model.Product;
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

@Module(includes = {ProductsRemoteModule.class, ProductsLocalModule.class})
public class ProductsRepositoryModule {

    @Provides
    @Remote
    @AppScoped
    ProductsData provideCatalogRemoteData(QueryLoadProducts catalogQuery) {
        return new ProductsRemoteData(catalogQuery);
    }

    @Provides
    @Local
    @AppScoped
    ProductsData provideCatalogLocakData(ProductDao productDao) {
        return new ProductsLocalData(productDao);
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
