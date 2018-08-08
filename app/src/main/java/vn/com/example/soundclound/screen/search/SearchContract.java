package vn.com.example.soundclound.screen.search;

import vn.com.example.soundclound.screen.base.presenter.BasePresenter;
import vn.com.example.soundclound.screen.base.view.BaseView;

public interface SearchContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<SearchContract.View> {

    }
}
