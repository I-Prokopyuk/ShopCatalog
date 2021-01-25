package com.example.shopcatalog.di;

import com.example.shopcatalog.di.scopes.AppScoped;
import com.example.shopcatalog.di.scopes.Remote;
import com.example.shopcatalog.repository.ProductsData;
import com.example.shopcatalog.repository.remote.QueryLoadProducts;
import com.example.shopcatalog.repository.remote.ProductsRemoteData;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ProductsRemoteModule.class})
public class ProductsRepositoryModule {

    @Provides
    @Remote
    @AppScoped
    ProductsData provideCatalogRemoteData(QueryLoadProducts catalogQuery) {
        return new ProductsRemoteData(catalogQuery);
    }


}
