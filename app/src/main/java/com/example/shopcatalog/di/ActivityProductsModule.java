package com.example.shopcatalog.di;

import com.example.shopcatalog.contract.IContract;
import com.example.shopcatalog.di.scopes.ActivityScoped;
import com.example.shopcatalog.ui.CatalogPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module(includes = ActivityProductsModule.ProductsModule.class)
public class ActivityProductsModule {

    @ActivityScoped
    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Module
    public interface ProductsModule {

        @ActivityScoped
        @Binds
        IContract.Presenter provideCatalogPresenter(CatalogPresenter catalogPresenter);
    }
}
