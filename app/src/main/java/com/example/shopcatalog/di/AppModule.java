package com.example.shopcatalog.di;

import android.content.Context;

import com.example.shopcatalog.app.Application;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AppModule {
    @Binds
    abstract Context bindContext(Application application);
}
