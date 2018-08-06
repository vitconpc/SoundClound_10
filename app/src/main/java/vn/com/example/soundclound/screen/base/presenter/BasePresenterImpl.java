package vn.com.example.soundclound.screen.base.presenter;

import vn.com.example.soundclound.screen.base.view.BaseView;

public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {
    protected V mView;

    @Override
    public void setView(V view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {

    }

    public V getView() {
        return this.mView;
    }
}
