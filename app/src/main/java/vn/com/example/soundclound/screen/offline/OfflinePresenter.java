package vn.com.example.soundclound.screen.offline;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.List;

import vn.com.example.soundclound.data.model.entity.Song;
import vn.com.example.soundclound.data.repository.SongReponsitory;
import vn.com.example.soundclound.data.source.remote.DataSource;
import vn.com.example.soundclound.screen.base.presenter.BasePresenterImpl;

public class OfflinePresenter extends BasePresenterImpl<OfflineContract.View> implements OfflineContract.Presenter
        , DataSource.OnFetchDataListener<Song> {

    private SongReponsitory mSongReponsitory;
    private Context mContext;

    public OfflinePresenter(Context context, SongReponsitory songReponsitory){
        this.mSongReponsitory = songReponsitory;
        this.mContext = context;
    }

    @Override
    public void onFetchDataSuccess(List<Song> data) {
        if (data != null) {
            getView().getSongsSuccess(data);
        }
    }

    @Override
    public void onFetchDataFailure(String error) {
        getView().getSongsFailure(error);
    }

    @Override
    public void loadSongs() {
        mSongReponsitory.getSongRemoteOffline(mContext, this);
    }

    @Override
    public void insertSong(Song song) {

    }
}
