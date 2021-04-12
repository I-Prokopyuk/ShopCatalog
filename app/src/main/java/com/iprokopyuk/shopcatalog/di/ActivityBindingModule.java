package com.iprokopyuk.shopcatalog.di;

import com.iprokopyuk.shopcatalog.di.scopes.ActivityScoped;
import com.iprokopyuk.shopcatalog.ui.CatalogActivity;
import com.iprokopyuk.shopcatalog.ui.WebViewActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = {ActivityProductsModule.class, FragmentBindingModule.class})
    abstract CatalogActivity catalogActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract WebViewActivity webViewActivity();
}
