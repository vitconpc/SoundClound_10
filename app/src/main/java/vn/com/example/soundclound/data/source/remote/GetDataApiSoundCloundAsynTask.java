package vn.com.example.soundclound.data.source.remote;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vn.com.example.soundclound.data.model.common.ConfigApi;
import vn.com.example.soundclound.data.model.common.Constants;
import vn.com.example.soundclound.data.model.common.utils.TrackEntry;
import vn.com.example.soundclound.data.model.entity.Song;

public class GetDataApiSoundCloundAsynTask extends AsyncTask<String, Void, String> {

    private DataSource.OnFetchDataListener<Song> mOnFetchDataListener;

    public GetDataApiSoundCloundAsynTask(DataSource.OnFetchDataListener<Song> onFetchDataListener) {
        this.mOnFetchDataListener = onFetchDataListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(ConfigApi.REQUEST_METHOD);
            connection.setReadTimeout(ConfigApi.READ_TIMEOUT);
            connection.setConnectTimeout(ConfigApi.CONNECT_TIMEOUT);
            connection.setDoOutput(true);
            connection.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            br.close();
            connection.disconnect();
            return sb.toString();
        } catch (MalformedURLException e) {
            return Constants.ERROR_URL;
        } catch (IOException e) {
            return Constants.ERROR_READFILE;
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);
        if (!data.equals(Constants.ERROR_URL) && !data.equals(Constants.ERROR_READFILE)) {
            mOnFetchDataListener.onFetchDataSuccess(parseToListSong(data));
            return;
        }
        mOnFetchDataListener.onFetchDataFailure(data);
    }

    private List<Song> parseToListSong(String data) {
        List<Song> songList = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(data);
            String collection = object.getString(TrackEntry.Api.COLLECTION);
            JSONArray jsonArrayCollection = new JSONArray(collection);
            for (int i = 0; i < jsonArrayCollection.length(); i++) {
                JSONObject jsonObject = jsonArrayCollection.getJSONObject(i);
                JSONObject jsonObjectSong = new JSONObject(jsonObject.getString(TrackEntry.Api.TRACK));
                songList.add(parseToSong(jsonObjectSong));
            }
        } catch (JSONException e) {
            mOnFetchDataListener.onFetchDataFailure(Constants.ERROR_MESSAGE);
        }
        return songList;
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
}
