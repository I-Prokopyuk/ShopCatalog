package com.example.shopcatalog.repository.local;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.shopcatalog.data.model.Product;

import java.util.List;

@Dao
public interface ProductDao {

//    @Query("SELECT * FROM Catalog WHERE category = :category")
//    List<Product> getProducts(String category);
}
