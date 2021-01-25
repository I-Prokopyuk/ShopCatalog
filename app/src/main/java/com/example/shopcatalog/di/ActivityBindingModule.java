package com.example.shopcatalog.di;

import com.example.shopcatalog.di.scopes.ActivityScoped;
import com.example.shopcatalog.di.scopes.FragmentScoped;
import com.example.shopcatalog.ui.CatalogActivity;
import com.example.shopcatalog.ui.CatalogFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = {ActivityProductsModule.class, FragmentBindingModule.class})
    abstract CatalogActivity catalogActivity();
}
