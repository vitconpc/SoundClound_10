package vn.com.example.soundclound.screen.detail_play;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

import vn.com.example.soundclound.R;
import vn.com.example.soundclound.data.model.common.Constants;
import vn.com.example.soundclound.data.model.entity.Song;
import vn.com.example.soundclound.screen.base.activity.BaseActivity;
import vn.com.example.soundclound.service.MusicService;
import vn.com.example.soundclound.service.ServiceCallback;

public class DetailPlayMusicActivity extends BaseActivity<DetailPlayContract.Presenter>
        implements DetailPlayContract.View, ServiceCallback, SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private static final String ACTION_BIND_SERVICE = "ACTION_BIND_SERVICE";
    private ServiceConnection mSCon;
    private MusicService mMusicService;
    private List<Song> mSongs;
    private int mCurentSong;
    private boolean mIsBound;

    private ImageView mImageLove;
    private ImageView mImageDownLoad;
    private ImageView mImageOClock;
    private ImageView mImageShare;
    private ImageView mImageLoop;
    private ImageView mImagePrevious;
    private ImageView mImagePlay;
    private ImageView mImageNext;
    private ImageView mImageShuffle;
    private TextView mTextCurrentTime;
    private TextView mTextTotalTime;
    private SeekBar mSeekBarSong;
    private SimpleDateFormat mDateFormat;
    private boolean mTrackingSeekBar = false;
    private ImageView mImageDisk;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail_play_music;
    }

    @Override
    protected DetailPlayPresenter initPresenter() {
        return new DetailPlayPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getPresenter().setView(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mIsBound = false;
        mDateFormat = new SimpleDateFormat(getString(R.string.date_time));
        connectService();
        bindView();
        registerListener();
        mImageDisk.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_rotate));
    }
    @Override
    public void postName(String songName, String author) {
        //todo set name and author
    }

    @Override
    public void postTotalTime(long totalTime) {
        mTextTotalTime.setText(mDateFormat.format(totalTime));
        mSeekBarSong.setMax((int) totalTime);
    }

    @Override
    public void postCurentTime(long currentTime) {
        mTextCurrentTime.setText(mDateFormat.format(currentTime));
        if (!mTrackingSeekBar) {
            mSeekBarSong.setProgress((int) currentTime);
        }
    }

    @Override
    public void postPauseButon() {
        mImagePlay.setImageResource(R.drawable.play_button);
    }

    @Override
    public void postStartButton() {
        mImagePlay.setImageResource(R.drawable.pause);
    }

    @Override
    public void postShuffle(boolean isShuffle) {
        //todo set icon Shuffle
    }

    @Override
    public void postLoop(boolean isLoop) {
        //todo set icon loop
    }

    @Override
    public void showError(String error) {
        Toast.makeText(mMusicService, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mTrackingSeekBar = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mTrackingSeekBar = false;
        mMusicService.seekTo(seekBar.getProgress());
    }

    @Override
    public void onClick(View v) {
        if (mIsBound) {
            switch (v.getId()) {
                case R.id.image_love:
                    //todo insert song to favorite
                    break;
                case R.id.image_download:
                    //todo download
                    break;
                case R.id.image_oclock:
                    //todo locate alert time
                    break;
                case R.id.image_share:
                    //todo share ???
                    break;
                case R.id.image_loop:
                    mMusicService.changeLoop();
                    break;
                case R.id.image_previous:
                    mMusicService.previous();
                    break;
                case R.id.image_play:
                    if (mMusicService.isPlay()) {
                        mMusicService.pauseSong();
                    } else {
                        mMusicService.continuesSong();
                    }
                    break;
                case R.id.image_next:
                    mMusicService.next();
                    break;
                case R.id.image_shuffle:
                    mMusicService.changeShuffle();
                    break;
            }
        }
    }

    public void getDataInIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.KEY_BUNDLE);
        mSongs = bundle.getParcelableArrayList(Constants.KEY_SONGS);
        mCurentSong = bundle.getInt(Constants.KEY_POSITION, 0);
        mMusicService.setSongs(mSongs);
        mMusicService.setCurrentSong(mCurentSong);
        mMusicService.playSong();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mMusicService.isPlay()) {
            Intent intent = new Intent(this, MusicService.class);
            intent.setAction(ACTION_BIND_SERVICE);
            stopService(intent);
        }
    }

    private void registerListener() {
        mSeekBarSong.setOnSeekBarChangeListener(this);
        mImageLove.setOnClickListener(this);
        mImageDownLoad.setOnClickListener(this);
        mImageOClock.setOnClickListener(this);
        mImageShare.setOnClickListener(this);
        mImageLoop.setOnClickListener(this);
        mImagePrevious.setOnClickListener(this);
        mImagePlay.setOnClickListener(this);
        mImageNext.setOnClickListener(this);
        mImageShuffle.setOnClickListener(this);
    }

    private void connectService() {
        mSCon = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder iBinder) {
                mMusicService = ((MusicService.MyBinder) iBinder).getMusicService();
                mMusicService.setListener(DetailPlayMusicActivity.this);
                mMusicService.setLoop(false);
                mMusicService.setShuffle(false);
                mIsBound = true;
                getDataInIntent();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(ACTION_BIND_SERVICE);
        startService(intent);
        bindService(intent, mSCon, BIND_AUTO_CREATE);
    }

    private void bindView() {
        mImageLove = findViewById(R.id.image_love);
        mImageDownLoad = findViewById(R.id.image_download);
        mImageOClock = findViewById(R.id.image_oclock);
        mImageShare = findViewById(R.id.image_share);
        mImageLoop = findViewById(R.id.image_loop);
        mImagePrevious = findViewById(R.id.image_previous);
        mImagePlay = findViewById(R.id.image_play);
        mImageNext = findViewById(R.id.image_next);
        mImageShuffle = findViewById(R.id.image_shuffle);
        mTextTotalTime = findViewById(R.id.text_total_time);
        mTextCurrentTime = findViewById(R.id.text_current_time);
        mSeekBarSong = findViewById(R.id.seekbar_song);
        mImageDisk = findViewById(R.id.image_disk_rotate);
    }
}
