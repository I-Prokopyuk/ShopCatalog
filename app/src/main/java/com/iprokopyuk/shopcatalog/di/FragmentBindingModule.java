package com.iprokopyuk.shopcatalog.di;

import com.iprokopyuk.shopcatalog.di.scopes.FragmentScoped;
import com.iprokopyuk.shopcatalog.ui.CatalogFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBindingModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = FragmentProductsModule.class)
    abstract CatalogFragment catalogFragment();
}
