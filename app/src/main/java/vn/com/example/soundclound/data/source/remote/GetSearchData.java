package vn.com.example.soundclound.data.source.remote;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vn.com.example.soundclound.data.model.common.Constants;
import vn.com.example.soundclound.data.model.common.utils.TrackEntry;
import vn.com.example.soundclound.data.model.entity.Song;

public class GetSearchData extends AsyncTask<String, Void, List<Song>>{

    private DataSource.OnFetchDataListener<Song> mOnFetchDataListener;
    public static final String GET = "GET";

    public GetSearchData(DataSource.OnFetchDataListener<Song> onFetchDataListener){
        this.mOnFetchDataListener = onFetchDataListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Song> doInBackground(String... strings) {
        String urlRequest =strings[0];
        String response = request(urlRequest);
        List<Song> songs = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(response);
            for (int i = 0; i < array.length(); i++) {
                songs.add(parseToSong(array.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return songs;
    }

    private String request(String urlRequest) {
        String result = null;
        try {
            URL url = new URL(urlRequest);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod(GET);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(httpConnection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            result = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Song parseToSong(JSONObject jsonObjectSong) throws JSONException {
        if (jsonObjectSong != null) {
            Song song = new Song();
            song.setArtworkUrl(jsonObjectSong.getString(TrackEntry.Api.ARTWORK_URL));
            song.setDescription(jsonObjectSong.getString(TrackEntry.Api.DESCRIPTION));
            song.setDownloadable(jsonObjectSong.getBoolean(TrackEntry.Api.DOWNLOADABLE));
            song.setDownloadUrl(jsonObjectSong.getString(TrackEntry.Api.DOWNLOAD_URL));
            song.setDuration(jsonObjectSong.getLong(TrackEntry.Api.DURATION));
            song.setId(jsonObjectSong.getInt(TrackEntry.Api.ID));
            song.setLikesCount(jsonObjectSong.getInt(TrackEntry.Api.LIKES_COUNT));
            song.setPlaybackCount(jsonObjectSong.getInt(TrackEntry.Api.PLAYBACK_COUNT));
            song.setTitle(jsonObjectSong.getString(TrackEntry.Api.TITLE));
            song.setUri(jsonObjectSong.getString(TrackEntry.Api.URI));
            JSONObject jsonObjectUser = new JSONObject(jsonObjectSong.getString(TrackEntry.Api.USER));
            song.setUserName(jsonObjectUser.getString(TrackEntry.Api.USERNAME));
            song.setAvatarUrl(jsonObjectUser.getString(TrackEntry.Api.AVATAR_URL));
            return song;
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Song> songs) {
        super.onPostExecute(songs);
        if (!songs.equals(Constants.ERROR_MESSAGE)) {
            mOnFetchDataListener.onFetchDataSuccess(songs);
            return;
        }
        mOnFetchDataListener.onFetchDataFailure(Constants.ERROR_MESSAGE);
    }
}
