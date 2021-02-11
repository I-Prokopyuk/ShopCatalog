package com.example.shopcatalog.repository.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.shopcatalog.common.Constants;
import com.example.shopcatalog.data.model.Product;

@Database(entities = {Product.class}, version = Constants.DB_VERSION)
public abstract class ProductDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}
