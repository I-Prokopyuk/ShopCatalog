package com.example.shopcatalog.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shopcatalog.R;
import com.example.shopcatalog.common.Constants;
import com.example.shopcatalog.data.model.Product;
import com.example.shopcatalog.databinding.ListProductsBinding;
import com.example.shopcatalog.utils.FullUrl;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

public class ProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ListProductsBinding binding;

    String productPriceCurrency;

    Picasso picasso;

    Product product;

    public ProductsViewHolder(ListProductsBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

        this.picasso = Picasso.get();

        productPriceCurrency = binding.getRoot().getResources().getString(R.string.product_price_currency);

        binding.getRoot().setOnClickListener(this);
    }

    public void bind(Product product) {

        this.product = product;

        binding.productName.setText(product.getName());
        binding.productPrice.setText(String.valueOf(product.getPrice()) + " " + productPriceCurrency);

        if (product.getSpecialPrice() > 0) {
            binding.productPrice.setPaintFlags(binding.productPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            binding.productSpecialPrice.setText(String.valueOf(product.getSpecialPrice()) + " " + productPriceCurrency);
        }
        binding.productCode.setText(String.valueOf(product.getCode()));

        picasso.
                load(Constants.CATALOG_API_BASE_URL + Constants.CATALOG_API_URL_IMAGE_CATALOG + product.getPathImage())
                .error(R.drawable.ic_baseline_photo_camera_150)
                .fit()
                .centerInside()
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(binding.productImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        binding.productProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        Intent intent = new Intent(context, WebViewActivity.class);
        String fullUrl = FullUrl.getFullUrl(product.getCategory(), product.getUrl());
        intent.putExtra("url", fullUrl);
        context.startActivity(intent);
    }
}
