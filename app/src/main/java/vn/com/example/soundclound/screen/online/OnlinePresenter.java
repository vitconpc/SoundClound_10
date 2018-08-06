package vn.com.example.soundclound.screen.online;

import java.util.List;

import vn.com.example.soundclound.BuildConfig;
import vn.com.example.soundclound.data.model.common.ConfigApi;
import vn.com.example.soundclound.data.model.entity.Song;
import vn.com.example.soundclound.data.repository.SongReponsitory;
import vn.com.example.soundclound.data.source.remote.DataSource;
import vn.com.example.soundclound.screen.base.presenter.BasePresenterImpl;


public class OnlinePresenter extends BasePresenterImpl<OnlineContract.View> implements OnlineContract.Presenter
        , DataSource.OnFetchDataListener<Song> {

    private static final String PARA_MUSIC_GENRE = "charts?kind=top&genre=soundcloud%3Agenres%3A";
    private static final String BASE_URL = "https://api-v2.soundcloud.com/";
    private static final String CLIENT_ID = "&client_id=";
    private static final String PARA_LIMIT = "&limit=";
    private static final String PARA_OFFSET = "&offset=";
    private SongReponsitory mSongReponsitory;

    public OnlinePresenter(SongReponsitory songReponsitory) {
        this.mSongReponsitory = songReponsitory;
    }

    @Override
    public void loadSongs(String genre) {
        String url = BASE_URL
                + PARA_MUSIC_GENRE
                + genre
                + CLIENT_ID
                + BuildConfig.CLIENT_ID
                + PARA_LIMIT
                + ConfigApi.LIMIT
                + PARA_OFFSET
                + ConfigApi.OFFSET;
        mSongReponsitory.getSongRemote(this, url);
    }

    @Override
    public void insertSong(Song song) {
        //todo insert song from reponsitory
    }

    @Override
    public void onFetchDataSuccess(List<Song> songs) {
        if (songs != null) {
            getView().getSongsSuccess(songs);
        }
    }

    @Override
    public void onFetchDataFailure(String error) {
        getView().getSongsFailure(error);
    }
}
