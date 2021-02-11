package com.example.shopcatalog.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.example.shopcatalog.data.model.Product;
import com.example.shopcatalog.databinding.ListProductsBinding;

public class ProductsPagedListAdapter extends PagedListAdapter<Product, ProductsViewHolder> {

    protected ProductsPagedListAdapter(@NonNull DiffUtil.ItemCallback<Product> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_products, parent, false);
        return new ProductsViewHolder(ListProductsBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
}
