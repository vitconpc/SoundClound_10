package vn.com.example.soundclound.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import vn.com.example.soundclound.R;
import vn.com.example.soundclound.data.model.common.Constants;
import vn.com.example.soundclound.data.model.common.utils.Utils;
import vn.com.example.soundclound.data.model.entity.Song;

import static vn.com.example.soundclound.data.model.common.Constants.ACTION_NEXT;
import static vn.com.example.soundclound.data.model.common.Constants.ACTION_PLAY;
import static vn.com.example.soundclound.data.model.common.Constants.ACTION_PREVIOUS;

public class MusicService extends Service implements BaseMediaPlayer
        , MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener
        , MediaPlayer.OnErrorListener {
    private MyBinder mMyBinder = new MyBinder();
    private MediaPlayer mMediaPlayer;
    private List<Song> mSongs;
    private int mCurrentPossition;
    private boolean mIsShuffle;
    private boolean mIsLoop;
    private Random mRandom;
    private int mProgess;
    private ServiceCallback mServiceCallback;
    private Handler mHandler = new Handler();
    private Runnable mTimeRunnable = new Runnable() {
        @Override
        public void run() {
            mServiceCallback.postCurentTime(getCurrentPosition());
            if (isPlay()) {
                mHandler.postDelayed(this, Constants.DELAY_MILLIS);
            }
        }
    };

    public void setListener(ServiceCallback listener) {
        this.mServiceCallback = listener;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRandom = new Random();
        mCurrentPossition = 0;
        mProgess = 0;
        mMediaPlayer = new MediaPlayer();
        initMediaPlayer();
    }

    private void initMediaPlayer() {
        mMediaPlayer.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (intent != null) {
            handlerIntent(intent);
        }
        return mMyBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            handlerIntent(intent);
        }
        return START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        release();
        stopSelf();
        super.onDestroy();
    }

    @Override
    public void playSong() {
        Song song = mSongs.get(mCurrentPossition);
        String uri;
        if (song.getId() == 0) {
            uri = song.getUri();
        } else {
            uri = Utils.getUrlDownload(song.getUri());
        }
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(uri);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            mServiceCallback.showError(getString(R.string.message_error_path));
        }
    }

    @Override
    public void startSong() {
        mMediaPlayer.start();
    }

    @Override
    public void stopSong() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mServiceCallback.postPauseButon();
        }
    }

    @Override
    public void pauseSong() {
        if (isPlay()) {
            mProgess = mMediaPlayer.getCurrentPosition();
            mMediaPlayer.pause();
            mServiceCallback.postPauseButon();
        }
    }

    @Override
    public void release() {
        mMediaPlayer.release();
    }

    @Override
    public void seekTo(int progess) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(progess);
        }
    }

    @Override
    public void next() {
        if (mCurrentPossition == mSongs.size() - 1) {
            mCurrentPossition = 0;
        } else {
            mCurrentPossition++;
        }
        playSong();
    }

    @Override
    public void previous() {
        if (mCurrentPossition == 0) {
            mCurrentPossition = mSongs.size() - 1;
        } else {
            mCurrentPossition--;
        }
        playSong();
    }

    @Override
    public long getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public long getDuration() {
        return mMediaPlayer.getDuration();
    }

    @Override
    public boolean isPlay() {
        return mMediaPlayer.isPlaying();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        postTitle();
        mServiceCallback.postTotalTime(getDuration());
        mHandler.postDelayed(mTimeRunnable, Constants.DELAY_MILLIS);
        mServiceCallback.postStartButton();
        mMediaPlayer.start();
        postNotification();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mIsLoop) {
            playSong();
        } else if (mIsShuffle) {
            mCurrentPossition = mRandom.nextInt(mSongs.size() - 1);
            playSong();
        } else {
            next();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return true;
    }

    public void setLoop(boolean loop) {
        this.mIsLoop = loop;
    }

    public void setShuffle(boolean shuffle) {
        this.mIsShuffle = shuffle;
    }

    public void setSongs(List<Song> songs) {
        this.mSongs = songs;
    }

    public void setCurrentSong(int currentSong) {
        this.mCurrentPossition = currentSong;
    }

    public void continuesSong() {
        mMediaPlayer.seekTo(getProgess());
        mMediaPlayer.start();
        mServiceCallback.postStartButton();
    }

    public int getProgess() {
        return mProgess;
    }

    public class MyBinder extends Binder {
        public MusicService getMusicService() {
            return MusicService.this;
        }
    }

    public void changeLoop() {
        mIsLoop = !mIsLoop;
    }

    public void changeShuffle() {
        mIsShuffle = !mIsLoop;
    }

    private void postNotification() {

    }

    private void handlerIntent(Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case ACTION_PLAY:
                if (isPlay()) {
                    pauseSong();
                } else {
                    startSong();
                }
                postNotification();
                break;
            case ACTION_NEXT:
                next();
                postNotification();
                break;
            case ACTION_PREVIOUS:
                previous();
                postNotification();
                break;
        }
    }

    private void postTitle() {
        Song song = mSongs.get(mCurrentPossition);
        mServiceCallback.postName(song.getTitle(), song.getUserName());
    }
}
