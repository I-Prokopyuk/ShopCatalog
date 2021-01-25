package com.example.shopcatalog.di;

import android.content.Context;

import com.example.shopcatalog.app.Application;
import com.example.shopcatalog.di.scopes.ActivityScoped;
import com.example.shopcatalog.di.scopes.FragmentScoped;
import com.example.shopcatalog.ui.CatalogActivity;
import com.example.shopcatalog.ui.CatalogFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AppModule {
    @Binds
    abstract Context bindContext(Application application);
}
