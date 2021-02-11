package com.example.shopcatalog.di;

import com.example.shopcatalog.app.Application;
import com.example.shopcatalog.di.scopes.AppScoped;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilityModule {

    @AppScoped
    @Provides
    Picasso providePicasso(Application application) {
        return Picasso.get();
    }
}
