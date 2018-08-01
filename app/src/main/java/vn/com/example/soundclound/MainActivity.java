package vn.com.example.soundclound;

import android.os.Bundle;
import android.widget.Toast;

import vn.com.example.soundclound.presenter.base.BasePresenter;
import vn.com.example.soundclound.view.base.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

}
