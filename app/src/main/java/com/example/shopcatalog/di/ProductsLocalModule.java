package com.example.shopcatalog.di;

import androidx.room.Room;

import com.example.shopcatalog.app.Application;
import com.example.shopcatalog.common.Constants;
import com.example.shopcatalog.di.scopes.AppScoped;
import com.example.shopcatalog.repository.local.ProductDao;
import com.example.shopcatalog.repository.local.ProductDatabase;

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
