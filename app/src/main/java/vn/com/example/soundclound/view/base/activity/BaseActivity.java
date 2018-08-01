package vn.com.example.soundclound.view.base.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import vn.com.example.soundclound.presenter.base.BasePresenter;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    private T mPresenter;

    protected abstract int getLayoutResource();

    protected abstract T initPresenter();

    protected T getPresenter() {
        return this.mPresenter;
    }

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void initView(Bundle savedInstanceState);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(getLayoutResource());
        this.mPresenter = initPresenter();
        initView(savedInstanceState);
        initData(savedInstanceState);
    }

}
