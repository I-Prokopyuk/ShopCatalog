package com.iprokopyuk.shopcatalog.di;

import com.iprokopyuk.shopcatalog.app.Application;
import com.iprokopyuk.shopcatalog.di.scopes.AppScoped;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@AppScoped
@Component(modules = {
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
