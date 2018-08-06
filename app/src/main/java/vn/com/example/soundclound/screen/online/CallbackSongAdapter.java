package vn.com.example.soundclound.screen.online;

import vn.com.example.soundclound.data.model.entity.Song;

public interface CallbackSongAdapter {
    void handlerItemClick(Song song);
    void handlerItemSongDownload(int position);
    void handlerItemSongSelection(int position);
}
