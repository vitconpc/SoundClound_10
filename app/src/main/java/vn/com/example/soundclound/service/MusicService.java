package vn.com.example.soundclound.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.com.example.soundclound.R;
import vn.com.example.soundclound.data.model.common.Constants;
import vn.com.example.soundclound.data.model.common.utils.Utils;
import vn.com.example.soundclound.data.model.entity.Song;
import vn.com.example.soundclound.screen.detail_play.DetailPlayMusicActivity;

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
        mIsShuffle = Utils.getState(getApplicationContext(), Constants.KEY_SHUFFLE);
        mIsLoop = Utils.getState(getApplicationContext(), Constants.KEY_LOOP);
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
        mHandler.postDelayed(mTimeRunnable, Constants.DELAY_MILLIS);
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
            postNotification();
        }
    }

    @Override
    public void release() {
        mMediaPlayer.release();
    }

    @Override
    public void seekTo(int progess) {
        if (mMediaPlayer != null) {
            mProgess = progess;
            mMediaPlayer.seekTo(mProgess);
            startSong();
        }
    }

    @Override
    public void next() {
        if (mIsShuffle) {
            mCurrentPossition = mRandom.nextInt(mSongs.size());
        } else {
            if (mCurrentPossition == mSongs.size() - 1) {
                mCurrentPossition = 0;
            } else {
                mCurrentPossition++;
            }
        }
        playSong();
    }

    @Override
    public void previous() {
        if (mIsShuffle) {
            mCurrentPossition = mRandom.nextInt(mSongs.size());
        } else {
            if (mCurrentPossition == 0) {
                mCurrentPossition = mSongs.size() - 1;
            } else {
                mCurrentPossition--;
            }
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
        mServiceCallback.postTotalTime(getDuration());
        postCurrentTime();
        postTitle(mSongs.get(mCurrentPossition));
        mServiceCallback.postStartButton();
        if (mSongs.get(mCurrentPossition).getAvatarUrl()!=null){
            mServiceCallback.postAvatar(mSongs.get(mCurrentPossition).getAvatarUrl());
        }
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
        postCurrentTime();
        postNotification();
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
        mServiceCallback.postLoop(mIsLoop);
    }

    public void changeShuffle() {
        mIsShuffle = !mIsShuffle;
        mServiceCallback.postShuffle(mIsShuffle);
    }

    private void postNotification() {
        Intent intent = new Intent(this, DetailPlayMusicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.KEY_SONGS, (ArrayList<? extends Parcelable>) mSongs);
        bundle.putInt(Constants.KEY_POSITION,mCurrentPossition);
        intent.putExtra(Constants.KEY_BUNDLE,bundle);
        intent.putExtra(Constants.KEY_PROGESS,mMediaPlayer.getCurrentPosition());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent pauseStartIntent = new Intent(this, MusicService.class);
        pauseStartIntent.setAction(Constants.ACTION_PLAY);
        PendingIntent pplayIntent = PendingIntent.getService(getApplicationContext(),
                0, pauseStartIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent nextIntent = new Intent(this, MusicService.class);
        nextIntent.setAction(Constants.ACTION_NEXT);
        PendingIntent pnextIntent = PendingIntent.getService(getApplicationContext(),
                0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent previousIntent = new Intent(this, MusicService.class);
        previousIntent.setAction(Constants.ACTION_PREVIOUS);
        PendingIntent ppreviousIntent = PendingIntent.getService(getApplicationContext(),
                0, previousIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int iconPauseStart;
        if (mMediaPlayer.isPlaying()) {
            iconPauseStart = R.drawable.pause;
        } else {
            iconPauseStart = R.drawable.play_button;
        }
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
        Song song = mSongs.get(mCurrentPossition);
//        if (song.getAvatarUrl() != null) {
//            remoteViews.setImageViewUri(R.id.image_avatar_song_notifi, Uri.parse(Utils.getUrlDownload(song.getUri())));
//        }
        remoteViews.setTextViewText(R.id.text_song_name_notifi, song.getTitle());
        remoteViews.setTextViewText(R.id.text_artist_notifi, song.getUserName());
        remoteViews.setImageViewResource(R.id.image_play_notifi, iconPauseStart);

        remoteViews.setOnClickPendingIntent(R.id.image_previous_notifi, ppreviousIntent);
        remoteViews.setOnClickPendingIntent(R.id.image_play_notifi, pplayIntent);
        remoteViews.setOnClickPendingIntent(R.id.image_next_notifi, pnextIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext()
                , Constants.CHANNEL_ID);

        builder.setContentIntent(pendingIntent)
                .setContent(remoteViews)
                .setSmallIcon(R.drawable.ic_launch)
                .setPriority(1);

        Notification notification = builder.build();
        startForeground(Constants.ID_NOTIFICATION_SERVICE, notification);
    }

    private void handlerIntent(Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case ACTION_PLAY:
                if (isPlay()) {
                    pauseSong();
                } else {
                    continuesSong();
                }
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

    private void postTitle(Song song) {
        mServiceCallback.postName(song.getTitle(), song.getUserName());
    }

    private void postCurrentTime() {
        mHandler.postDelayed(mTimeRunnable, Constants.DELAY_MILLIS);
    }
}
