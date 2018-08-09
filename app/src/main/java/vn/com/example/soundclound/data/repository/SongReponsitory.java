package vn.com.example.soundclound.data.repository;

import android.content.Context;

import vn.com.example.soundclound.data.model.entity.Song;
import vn.com.example.soundclound.data.source.remote.DataSource;
import vn.com.example.soundclound.data.source.remote.SongRemoteDataSource;

public class SongReponsitory implements DataSource.RemoteDataSource {

    private static SongReponsitory sSongReopository;
    private DataSource.RemoteDataSource mRemoteDataSource;

    public SongReponsitory(DataSource.RemoteDataSource remoteDataSource) {
        this.mRemoteDataSource = remoteDataSource;
    }

    public static SongReponsitory getInstance() {
        if (sSongReopository == null) {
            sSongReopository = new SongReponsitory(SongRemoteDataSource.getInstance());
        }
        return sSongReopository;
    }

    @Override
    public void getSongRemote(DataSource.OnFetchDataListener<Song> listener, String url) {
        mRemoteDataSource.getSongRemote(listener, url);
    }

    @Override
    public void getSongRemoteOffline(Context context, DataSource.OnFetchDataListener<Song> listener) {
        mRemoteDataSource.getSongRemoteOffline(context, listener);
    }
}
