package vn.com.example.soundclound.data.source.remote;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import vn.com.example.soundclound.data.model.common.Constants;
import vn.com.example.soundclound.data.model.entity.Song;
import vn.com.example.soundclound.screen.main.MainActivity;

public class GetDataOffline extends AsyncTask<String, Void, List<Song>> {

    private DataSource.OnFetchDataListener<Song> mOnFetchDataListener;
    public static final String NOT_ZERO = " != 0";
    public static final int POSITION_ONE = 1;
    public static final int POSITION_TWO = 2;
    public static final int POSITION_THREE = 3;
    private Context mContext;
    private List<Song> mSongs = new ArrayList<>();

    public GetDataOffline(Context context, DataSource.OnFetchDataListener<Song> onFetchDataListener) {
        this.mOnFetchDataListener = onFetchDataListener;
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Song> doInBackground(String... strings) {
        String selection = MediaStore.Audio.Media.IS_MUSIC + NOT_ZERO;
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION
        };
        Cursor cursor = mContext.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null);
        while(cursor.moveToNext()) {
            mSongs.add(new Song(cursor.getString(POSITION_TWO), cursor.getString(POSITION_ONE)
                    , cursor.getString(POSITION_THREE)));
        }
        return mSongs;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<Song> songs) {
        super.onPostExecute(songs);
        if (!songs.equals(Constants.ERROR_MESSAGE)) {
            mOnFetchDataListener.onFetchDataSuccess(mSongs);
            return;
        }
        mOnFetchDataListener.onFetchDataFailure(Constants.ERROR_MESSAGE);
    }
}