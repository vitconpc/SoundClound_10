package vn.com.example.soundclound.screen.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import vn.com.example.soundclound.screen.main.MainActivity;
import vn.com.example.soundclound.R;
import vn.com.example.soundclound.presenter.base.BasePresenter;
import vn.com.example.soundclound.view.base.activity.BaseActivity;

public class SplashActivity extends BaseActivity {

    private Handler mHandler;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mHandler = new Handler();
        createDelay();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void createDelay() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToMainActivity();
            }
        },3000);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }
}
