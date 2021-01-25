package com.example.shopcatalog.ui;

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
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class ProductsViewHolder extends RecyclerView.ViewHolder {
    TextView productName, productPrice;
    ImageView productimage;
    ProgressBar productProgressBar;

    @Inject
    Picasso picasso;

    public ProductsViewHolder(@NonNull View itemView) {
        super(itemView);

        productProgressBar = (ProgressBar) itemView.findViewById(R.id.product_progressBar);
        productimage = (ImageView) itemView.findViewById(R.id.product_image);
        productName = (TextView) itemView.findViewById(R.id.product_name);
        productPrice = (TextView) itemView.findViewById(R.id.product_price);
    }

    public void bind(Product product) {

        productName.setText(Constants.CATALOG_API_URL_IMAGE_CATALOG + product.getPathImage());
        productPrice.setText(String.valueOf(product.getPrice()));


        picasso.
                load(Constants.CATALOG_API_URL_IMAGE_CATALOG + product.getPathImage())
                .error(R.drawable.ic_baseline_photo_camera_150)
                .fit()
                .centerInside()
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
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
}
