package com.iprokopyuk.shopcatalog.di;

import android.content.Context;

import com.iprokopyuk.shopcatalog.app.Application;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AppModule {
    @Binds
    abstract Context bindContext(Application application);
}
