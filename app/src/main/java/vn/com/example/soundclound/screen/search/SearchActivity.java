package vn.com.example.soundclound.screen.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import vn.com.example.soundclound.R;
import vn.com.example.soundclound.data.model.common.Constants;
import vn.com.example.soundclound.data.model.common.adapter.SongAdapter;
import vn.com.example.soundclound.data.model.entity.Song;
import vn.com.example.soundclound.data.repository.SongReponsitory;
import vn.com.example.soundclound.screen.base.activity.BaseActivity;
import vn.com.example.soundclound.screen.detail_play.DetailPlayMusicActivity;
import vn.com.example.soundclound.screen.main.MainActivity;
import vn.com.example.soundclound.screen.online.CallbackSongAdapter;

public class SearchActivity extends BaseActivity<SearchContract.Presenter>
        implements SearchContract.View, TextWatcher, View.OnClickListener, CallbackSongAdapter {

    private RecyclerView mRecyclerViewResult;
    private String mStringSearch;
    private EditText mEditTextSearch;
    private SongAdapter mAdapterSongSearch;
    private List<Song> mSongs;
    private Button mButtonBackPress;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_search;
    }

    @Override
    protected SearchContract.Presenter initPresenter() {
        return new SearchPresenter(SongReponsitory.getInstance());
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        getPresenter().setView(this);
        mRecyclerViewResult = findViewById(R.id.recycler_result_search);
        mEditTextSearch = findViewById(R.id.edit_text_search);
        mButtonBackPress = findViewById(R.id.button_ation_back);
        initRecyclerView();
        mEditTextSearch.addTextChangedListener(this);
        mButtonBackPress.setOnClickListener(this);
    }

    private void initRecyclerView() {
        mSongs = new ArrayList<>();
        mAdapterSongSearch = new SongAdapter(this, mSongs, this);
        mRecyclerViewResult.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewResult.setLayoutManager(layoutManager);
        mRecyclerViewResult.setAdapter(mAdapterSongSearch);
    }

    @Override
    public void showResult(List<Song> songs) {
        mSongs.addAll(songs);
        mAdapterSongSearch.addData(songs);
        mAdapterSongSearch.notifyDataSetChanged();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mStringSearch = charSequence.toString();
        if (mStringSearch != null) {
            getPresenter().searchSongOnline(mStringSearch);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public void handlerItemClick(int possition) {
        gotoDetailActivity(possition);
    }

    private void gotoDetailActivity(int position) {
        Intent intent = new Intent(this, DetailPlayMusicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.KEY_SONGS, (ArrayList<? extends Parcelable>) mSongs);
        bundle.putInt(Constants.KEY_POSITION,position);
        intent.putExtra(Constants.KEY_BUNDLE,bundle);
        startActivity(intent);
    }

    @Override
    public void handlerItemSongDownload(int position) {

    }

    @Override
    public void handlerItemSongSelection(int position) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
