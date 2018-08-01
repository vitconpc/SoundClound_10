package vn.com.example.soundclound.view.splash;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NavigationRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import vn.com.example.soundclound.MainActivity;
import vn.com.example.soundclound.R;
import vn.com.example.soundclound.data.model.common.Constants;
import vn.com.example.soundclound.data.model.common.utils.Utils;
import vn.com.example.soundclound.presenter.base.BasePresenter;
import vn.com.example.soundclound.view.base.activity.BaseActivity;

public class SplashActivity extends BaseActivity {

    private boolean mIsPermission = false;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData(Bundle savedInstanceState) {
        checkPermis();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.savePermission(this, mIsPermission);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermis() {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                mIsPermission = true;
                gotoMainActivity(3000);
                return;
            }
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.REQUEST_CODE_PERMISSION);
    }

    private void gotoMainActivity(int time) {
        try {
            Thread.sleep(time);
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.REQUEST_CODE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mIsPermission = true;
            gotoMainActivity(3000);
            return;
        }
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.REQUEST_CODE_PERMISSION);
        gotoMainActivity(1000);
    }
}
