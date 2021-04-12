package com.iprokopyuk.shopcatalog.di;

import com.iprokopyuk.shopcatalog.app.Application;
import com.iprokopyuk.shopcatalog.di.scopes.AppScoped;
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
