package com.iprokopyuk.shopcatalog.repository.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.iprokopyuk.shopcatalog.common.Constants;
import com.iprokopyuk.shopcatalog.data.model.Product;

@Database(entities = {Product.class}, version = Constants.DB_VERSION)
public abstract class ProductDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
}
