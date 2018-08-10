package vn.com.example.soundclound.service;

import java.util.List;

import vn.com.example.soundclound.data.model.entity.Song;

public interface ServiceCallback {
    void postName(String songName,String author);

    void postTotalTime(long totalTime);

    void postCurentTime(long currentTime);

    void postPauseButon();

    void postStartButton();

    void postShuffle(boolean isShuffle);

    void postLoop(boolean isLoop);

    void showError(String error);

    void postAvatar(String url);
}
