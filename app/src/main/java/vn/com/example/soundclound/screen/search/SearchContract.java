package vn.com.example.soundclound.screen.search;

import java.util.List;

import vn.com.example.soundclound.data.model.entity.Song;
import vn.com.example.soundclound.screen.base.presenter.BasePresenter;
import vn.com.example.soundclound.screen.base.view.BaseView;

public interface SearchContract {

    interface View extends BaseView {
        void showResult(List<Song> songs);
    }

    interface Presenter extends BasePresenter<SearchContract.View> {
        void searchSongOnline(String textSearch);
    }
}