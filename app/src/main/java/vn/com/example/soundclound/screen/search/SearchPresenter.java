package vn.com.example.soundclound.screen.search;

import java.util.List;

import vn.com.example.soundclound.BuildConfig;
import vn.com.example.soundclound.data.model.common.ConfigApi;
import vn.com.example.soundclound.data.model.entity.Song;
import vn.com.example.soundclound.data.repository.SongReponsitory;
import vn.com.example.soundclound.data.source.remote.DataSource;
import vn.com.example.soundclound.screen.base.presenter.BasePresenterImpl;

public class SearchPresenter extends BasePresenterImpl<SearchContract.View> implements SearchContract.Presenter {

    private SongReponsitory mRepository;
    private static final String BASE_URL = "http://api.soundcloud.com/";
    private static final String PARA_MUSIC_GENRE = "tracks";
    private static final String CLIENT_ID = "?client_id=";
    private static final String PARAM_SEARCH = "&q=";

    public SearchPresenter(SongReponsitory trackRepository) {
        mRepository = trackRepository;
    }

    @Override
    public void searchSongOnline(String textSearch) {
        mRepository.searchTracksByTitle(textSearch, new DataSource.OnFetchDataListener<Song>() {
            @Override
            public void onFetchDataSuccess(List<Song> data) {
                if (data != null){
                    getView().showResult(data);
                }
            }

            @Override
            public void onFetchDataFailure(String error) {

            }
        }, getUrl(textSearch));
    }

    public String getUrl(String textSearch){
        String url = BASE_URL
                + PARA_MUSIC_GENRE
                + CLIENT_ID
                + BuildConfig.CLIENT_ID
                + PARAM_SEARCH
                + textSearch;
        return url;
    }
}