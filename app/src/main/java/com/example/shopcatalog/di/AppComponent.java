package com.example.shopcatalog.di;


import com.example.shopcatalog.app.Application;
import com.example.shopcatalog.di.scopes.AppScoped;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@AppScoped
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ActivityBindingModule.class,
        ProductsRepositoryModule.class,
        UtilityModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<Application> {

    @Component.Builder
    interface Builder {
        AppComponent build();

        @BindsInstance
        AppComponent.Builder application(Application application);
    }
}
