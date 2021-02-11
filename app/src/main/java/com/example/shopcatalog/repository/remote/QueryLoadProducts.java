package com.example.shopcatalog.repository.remote;

import com.example.shopcatalog.data.model.Product;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QueryLoadProducts {
    @GET("catalog/model/catalog/query.php?")
    Single<List<Product>> getProducts(@Query("category") String category, @Query("row_offset") int startPosition, @Query("row_count") int loadSize);
}
