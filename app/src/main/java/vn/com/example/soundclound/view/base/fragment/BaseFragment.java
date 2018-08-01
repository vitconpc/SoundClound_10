package vn.com.example.soundclound.view.base.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.com.example.soundclound.presenter.base.BasePresenter;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    private T mPresenter;

    protected T getPresenter() {
        return mPresenter;
    }

    protected abstract int getLayoutResource();

    protected abstract T initPresenter();

    protected abstract void initView(Bundle savedInstanceState, View rootView);

    protected abstract void initData(Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutResource(), container, false);
        mPresenter = initPresenter();
        initView(savedInstanceState, rootView);
        initData(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
