package com.example.shopcatalog.repository.local;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.shopcatalog.data.model.Product;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM Catalog WHERE category = :category")
    DataSource.Factory<Integer, Product> getAll(String category);

    @Query("SELECT * FROM Catalog WHERE category = :category LIMIT :rowOffset,:rowCount")
    Single<List<Product>> getProducts(String category, int rowOffset, int rowCount);

    //adds to the database if not in the database or updates
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProduct(List<Product> productList);

    @Query("DELETE FROM Catalog WHERE category = :category")
    void deleteProducts(String category);
}
