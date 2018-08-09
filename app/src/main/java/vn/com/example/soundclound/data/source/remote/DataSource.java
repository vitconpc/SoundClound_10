package vn.com.example.soundclound.data.source.remote;

import android.content.Context;

import java.util.List;

import vn.com.example.soundclound.data.model.entity.Song;

public interface DataSource {
    interface RemoteDataSource {
        void getSongRemote(OnFetchDataListener<Song> listener, String url);
        void getSongRemoteOffline(Context context, OnFetchDataListener<Song> listener);
    }

    interface OnFetchDataListener<T> {
        void onFetchDataSuccess(List<T> data);

        void onFetchDataFailure(String error);
    }
}
