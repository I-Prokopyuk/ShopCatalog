package com.example.shopcatalog.repository;

import androidx.paging.DataSource;

import com.example.shopcatalog.data.model.Product;

public class MySourceFactory extends DataSource.Factory<Integer, Product> {

    private ProductsCatalogRepository productsCatalogRepository;
    ProductsCatalogDataSource productsCatalogDataSource;

    public MySourceFactory(ProductsCatalogRepository productsCatalogRepository) {
        this.productsCatalogRepository = productsCatalogRepository;
    }

    @Override
    public DataSource create() {
        productsCatalogDataSource = new ProductsCatalogDataSource(productsCatalogRepository);
        return productsCatalogDataSource;
    }

    public ProductsCatalogDataSource getProductsCatalogDataSource() {
        return productsCatalogDataSource;
    }
}