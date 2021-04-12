package com.iprokopyuk.shopcatalog.di;

import com.iprokopyuk.shopcatalog.di.scopes.AppScoped;
import com.iprokopyuk.shopcatalog.repository.remote.QueryLoadProducts;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.iprokopyuk.shopcatalog.common.Constants.CATALOG_API_BASE_URL;

@Module
public class ProductsRemoteModule {

    @AppScoped
    @Provides
    QueryLoadProducts provideQueryLoadProducts(Retrofit retrofit) {
        return retrofit.create(QueryLoadProducts.class);
    }

    @AppScoped
    @Provides
    Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(CATALOG_API_BASE_URL)
                .build();
    }

    @AppScoped
    @Provides
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }
}
