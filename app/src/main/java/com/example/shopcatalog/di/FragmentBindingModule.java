package com.example.shopcatalog.di;

import com.example.shopcatalog.di.scopes.FragmentScoped;
import com.example.shopcatalog.ui.CatalogFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBindingModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = FragmentProductsModule.class)
    abstract CatalogFragment catalogFragment();
}
