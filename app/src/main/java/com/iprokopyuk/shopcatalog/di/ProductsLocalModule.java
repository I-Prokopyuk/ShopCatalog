package com.iprokopyuk.shopcatalog.di;

import androidx.room.Room;

import com.iprokopyuk.shopcatalog.app.Application;
import com.iprokopyuk.shopcatalog.common.Constants;
import com.iprokopyuk.shopcatalog.di.scopes.AppScoped;
import com.iprokopyuk.shopcatalog.repository.local.ProductDao;
import com.iprokopyuk.shopcatalog.repository.local.ProductDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class ProductsLocalModule {

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
