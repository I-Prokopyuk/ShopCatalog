package com.example.shopcatalog.contract;

import com.example.shopcatalog.contract.base.IView;

public interface IContract {

    interface View extends IView {
    }

    interface Presenter {

        void loadProducts(String category);

        void invalidateDataSource();

        void clearCompositeDisposable();
    }
}
