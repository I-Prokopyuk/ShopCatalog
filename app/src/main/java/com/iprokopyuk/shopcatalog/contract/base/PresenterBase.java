package com.iprokopyuk.shopcatalog.contract.base;

public abstract class PresenterBase<T extends IView> implements IPresenter<T> {

    private T view;

    @Override
    public void attachView(T iview) {
        view = iview;
    }

    @Override
    public void detachView() {
        view = null;
    }

    public T getView() {
        return view;
    }
}
