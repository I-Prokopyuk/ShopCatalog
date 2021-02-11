package com.example.shopcatalog.repository;

import androidx.paging.DataSource;

import com.example.shopcatalog.data.model.Product;
import com.example.shopcatalog.di.scopes.AppScoped;

import javax.inject.Inject;

@AppScoped
public class MySourceFactory extends DataSource.Factory<Integer, Product> {

    private ProductsCatalogRepository productsCatalogRepository;

    private ProductsCatalogDataSource productsCatalogDataSource;

    private String category;

    @Inject
    public MySourceFactory(ProductsCatalogRepository productsCatalogRepository) {
        this.productsCatalogRepository = productsCatalogRepository;
    }

    @Override
    public DataSource create() {

        return productsCatalogDataSource = new ProductsCatalogDataSource(productsCatalogRepository, category);
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ProductsCatalogDataSource getCatalogDataSource() {
        return productsCatalogDataSource;
    }
}