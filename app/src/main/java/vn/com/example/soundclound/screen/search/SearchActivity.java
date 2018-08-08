package vn.com.example.soundclound.screen.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vn.com.example.soundclound.R;
import vn.com.example.soundclound.screen.base.activity.BaseActivity;

public class SearchActivity extends BaseActivity<SearchContract.Presenter> implements SearchContract.View {


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_search;
    }

    @Override
    protected SearchContract.Presenter initPresenter() {
        return new SearchPresenter();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getPresenter().setView(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }
}
