package com.example.shopcatalog.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.example.shopcatalog.R;
import com.example.shopcatalog.common.Constants;
import com.example.shopcatalog.data.model.Product;

public class ProductsPagedListAdapter extends PagedListAdapter<Product, ProductsViewHolder> {

    protected ProductsPagedListAdapter(@NonNull DiffUtil.ItemCallback<Product> diffCallback) {
        super(diffCallback);
        Log.i(Constants.LOG_TAG, "create ProductsPagedListAdapter");
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_products, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
}
