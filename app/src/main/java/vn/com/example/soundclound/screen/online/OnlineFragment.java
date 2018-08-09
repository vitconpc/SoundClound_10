package vn.com.example.soundclound.screen.online;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.com.example.soundclound.R;
import vn.com.example.soundclound.data.model.common.Constants;
import vn.com.example.soundclound.data.model.common.StringType;
import vn.com.example.soundclound.data.model.common.adapter.SongAdapter;
import vn.com.example.soundclound.data.model.entity.Song;
import vn.com.example.soundclound.data.repository.SongReponsitory;
import vn.com.example.soundclound.screen.base.fragment.BaseFragment;
import vn.com.example.soundclound.screen.detail_play.DetailPlayMusicActivity;

public class OnlineFragment extends BaseFragment<OnlinePresenter> implements CallbackSongAdapter
        , AdapterView.OnItemSelectedListener, OnlineContract.View {
    private Spinner mSpinerGenres;
    private RecyclerView mRecyclerViewSongs;
    private SongAdapter mSongAdapter;
    private String[] mListSpinner = {StringType.GENRE_ALL_MUSIC, StringType.GENRE_ALL_AUDIO
            , StringType.GENRE_ALTERNATIVEROCK, StringType.GENRE_AMBIENT
            , StringType.GENRE_CLASSICAL, StringType.GENRE_COUNTRY};

    private List<Song> mSongs;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_online;
    }

    @Override
    protected OnlinePresenter initPresenter() {
        return new OnlinePresenter(SongReponsitory.getInstance());
    }

    @Override
    protected void initView(Bundle savedInstanceState, View rootView) {
        getPresenter().setView(this);
        initRecycler(rootView);
        initspinner(rootView);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getPresenter().loadSongs(StringType.GENRE_ALL_MUSIC);
    }

    @Override
    public void handlerItemClick(int currentPosition) {
        gotoDetailActivity(currentPosition);
    }

    private void gotoDetailActivity(int position) {
        Intent intent = new Intent(getActivity(), DetailPlayMusicActivity.class);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        getPresenter().loadSongs(parent.getAdapter().getItem(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void getSongsSuccess(List<Song> songs) {
        mSongs.addAll(songs);
        mSongAdapter.addData(songs);
    }

    @Override
    public void getSongsFailure(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    private void initRecycler(View rootView) {
        mSongs = new ArrayList<>();
        mSongAdapter = new SongAdapter(getActivity(), mSongs, this);
        mRecyclerViewSongs = rootView.findViewById(R.id.recycler_songs);
        mRecyclerViewSongs.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewSongs.setLayoutManager(layoutManager);
        mRecyclerViewSongs.setAdapter(mSongAdapter);
    }

    private void initspinner(View rootView) {
        mSpinerGenres = rootView.findViewById(R.id.spinner_genres);
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1
                , mListSpinner);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinerGenres.setAdapter(spinnerAdapter);
        mSpinerGenres.setOnItemSelectedListener(this);
    }
}
