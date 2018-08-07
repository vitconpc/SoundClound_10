package vn.com.example.soundclound.screen.detail_play;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vn.com.example.soundclound.R;
import vn.com.example.soundclound.screen.base.activity.BaseActivity;
import vn.com.example.soundclound.screen.base.presenter.BasePresenter;

public class DetailPlayMusicActivity extends BaseActivity<DetailPlayContract.Presenter>
        implements DetailPlayContract.View {

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

    }
}
