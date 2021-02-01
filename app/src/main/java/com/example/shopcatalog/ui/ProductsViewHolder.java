package com.example.shopcatalog.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopcatalog.R;
import com.example.shopcatalog.common.Constants;
import com.example.shopcatalog.data.model.Product;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

public class ProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView productName, productPrice, productSpecialPrice, productCode;
    ImageView productimage;
    ProgressBar productProgressBar;
    String productPriceCurrency;

    Picasso picasso;

    Product product;

    public ProductsViewHolder(@NonNull View itemView) {
        super(itemView);

        this.picasso = Picasso.get();

        productProgressBar = (ProgressBar) itemView.findViewById(R.id.product_progressBar);
        productimage = (ImageView) itemView.findViewById(R.id.product_image);
        productName = (TextView) itemView.findViewById(R.id.product_name);
        productPrice = (TextView) itemView.findViewById(R.id.product_price);
        productSpecialPrice = (TextView) itemView.findViewById(R.id.product_special_price);
        productCode = (TextView) itemView.findViewById(R.id.product_code);

        productPriceCurrency = itemView.getResources().getString(R.string.product_price_currency);

        itemView.setOnClickListener(this);
    }

    public void bind(Product product) {

        this.product = product;

        productName.setText(product.getName());
        productPrice.setText(String.valueOf(product.getPrice()) + " " + productPriceCurrency);

        if (product.getSpecialPrice() > 0) {
            productPrice.setPaintFlags(productPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            productSpecialPrice.setText(String.valueOf(product.getSpecialPrice()) + " " + productPriceCurrency);
        }
        productCode.setText(String.valueOf(product.getCode()));

        picasso.
                load(Constants.CATALOG_API_URL_IMAGE_CATALOG + product.getPathImage())
                .error(R.drawable.ic_baseline_photo_camera_150)
                .fit()
                .centerInside()
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(productimage, new Callback() {
                    @Override
                    public void onSuccess() {
                        productProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        Intent intent = new Intent(v.getContext(), WebViewActivity.class);
        intent.putExtra("url", product.getUrl());
        v.getContext().startActivity(intent);
    }
}
