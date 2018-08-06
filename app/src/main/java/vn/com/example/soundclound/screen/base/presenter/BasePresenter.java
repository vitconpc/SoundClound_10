package vn.com.example.soundclound.screen.base.presenter;

import vn.com.example.soundclound.screen.base.view.BaseView;

public interface BasePresenter<V extends BaseView> {
    void setView(V view);

    void onStart();

    void onStop();
}
