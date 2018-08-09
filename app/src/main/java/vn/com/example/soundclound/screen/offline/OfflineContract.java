package vn.com.example.soundclound.screen.offline;

import java.util.List;

import vn.com.example.soundclound.data.model.entity.Song;
import vn.com.example.soundclound.screen.base.presenter.BasePresenter;
import vn.com.example.soundclound.screen.base.view.BaseView;

public interface OfflineContract {
    interface View extends BaseView {
        void getSongsSuccess(List<Song> songs);

        void getSongsFailure(String error);
    }

    interface Presenter extends BasePresenter<View> {
        void loadSongs();

        void insertSong(Song song);
    }
}
