package com.example.shopcatalog.data.model;

import com.google.gson.GsonBuilder;

import java.util.List;

public class ProductList {

    public List<Product> products;

//    public ProductList() {
//        products = new ArrayList<Product>();
//    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}


//    GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
//                Gson gson = gsonBuilder.create();
//
//
//                Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .baseUrl(CATALOG_API_BASE_URL)
//                .build();
//
//                QueryLoadProducts queryLoadProducts1 = retrofit.create(QueryLoadProducts.class);