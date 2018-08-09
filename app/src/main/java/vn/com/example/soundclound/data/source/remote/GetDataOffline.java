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
    public static final String EXTERNAL = "external";
    public static final String MP3 = "mp3";
    public static final String SPACE = "_";
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int ZERO = 0;
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

        Uri uri = MediaStore.Files.getContentUri(EXTERNAL);
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        mSongs.clear();
        int idxPath = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
        int idxSong = cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String path = cursor.getString(idxPath);
            if (path.contains(MP3)) {
                if (path.contains(SPACE)) {
                    String informSong = cursor.getString(idxSong);
                    String[] name = informSong.split(SPACE);
                    String nameSong = name[ZERO];
                    String nameSinger = Constants.STRING_EMPTY;
                    if (name.length == TWO) {
                        nameSinger += name[ONE];
                    }
                    mSongs.add(new Song(nameSong, nameSinger, path));
                }
            }
            cursor.moveToNext();
        }
        cursor.close();
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
