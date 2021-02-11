package com.example.shopcatalog.contract.base;

public interface IPresenter<T extends IView> {

    void attachView(T iview);

    void detachView();

    void destroy();
}
