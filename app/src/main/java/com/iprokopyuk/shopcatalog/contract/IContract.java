package com.iprokopyuk.shopcatalog.contract;

import com.iprokopyuk.shopcatalog.contract.base.IView;

public interface IContract {

    interface View extends IView {
    }

    interface Presenter {

        void loadProducts(String category);

        void invalidateDataSource();

        void clearCompositeDisposable();
    }
}
