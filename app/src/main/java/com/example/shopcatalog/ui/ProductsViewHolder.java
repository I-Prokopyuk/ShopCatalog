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

public class ProductsViewHolder extends RecyclerView.ViewHolder {
    TextView productName, productPrice;
    ImageView productimage;
    ProgressBar productProgressBar;
    String productPriceCurrency;

    Picasso picasso;

    public ProductsViewHolder(@NonNull View itemView) {
        super(itemView);

        this.picasso = Picasso.get();

        productProgressBar = (ProgressBar) itemView.findViewById(R.id.product_progressBar);
        productimage = (ImageView) itemView.findViewById(R.id.product_image);
        productName = (TextView) itemView.findViewById(R.id.product_name);
        productPrice = (TextView) itemView.findViewById(R.id.product_price);

        productPriceCurrency = itemView.getResources().getString(R.string.product_price_currency);
    }

    public void bind(Product product) {

        productName.setText(product.getName());
        productPrice.setText(String.valueOf(product.getPrice()) + " " + productPriceCurrency);

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
}
