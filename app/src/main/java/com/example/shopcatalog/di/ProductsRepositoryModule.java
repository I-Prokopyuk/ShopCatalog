package com.example.shopcatalog.di;

import androidx.paging.PagedList;
import androidx.room.Room;

import com.example.shopcatalog.app.Application;
import com.example.shopcatalog.common.Constants;
import com.example.shopcatalog.di.scopes.AppScoped;
import com.example.shopcatalog.di.scopes.Remote;
import com.example.shopcatalog.repository.ProductsData;
import com.example.shopcatalog.repository.local.ProductDao;
import com.example.shopcatalog.repository.local.ProductDatabase;
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

    @Provides
    @AppScoped
    PagedList.Config providePagedListConfig() {
        return new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(1)
                .build();
    }

    @Provides
    @AppScoped
    ProductDatabase provideProductDatabase(Application application) {
        return Room.databaseBuilder(application.getApplicationContext(), ProductDatabase.class, Constants.DB_NAME).build();
    }

    @Provides
    @AppScoped
    ProductDao provideProductDao(ProductDatabase productDatabase) {
        return productDatabase.productDao();
    }


}
