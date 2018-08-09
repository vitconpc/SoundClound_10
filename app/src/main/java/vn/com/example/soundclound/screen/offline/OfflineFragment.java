package vn.com.example.soundclound.screen.offline;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import vn.com.example.soundclound.R;
import vn.com.example.soundclound.data.model.common.SpinnerType;
import vn.com.example.soundclound.data.model.common.adapter.SongAdapter;
import vn.com.example.soundclound.data.model.entity.Song;
import vn.com.example.soundclound.data.repository.SongReponsitory;
import vn.com.example.soundclound.screen.base.fragment.BaseFragment;
import vn.com.example.soundclound.screen.online.CallbackSongAdapter;
import vn.com.example.soundclound.screen.online.OnlinePresenter;

public class OfflineFragment extends BaseFragment<OfflinePresenter> implements OfflineContract.View,
        CallbackSongAdapter {

    private RecyclerView mRecyclerviewSong;
    private List<Song> mSongs;
    private SongAdapter mSongAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_offline;
    }

    @Override
    protected OfflinePresenter initPresenter() {
        return new OfflinePresenter(getContext(), SongReponsitory.getInstance());
    }

    @Override
    protected void initView(Bundle savedInstanceState, View rootView) {
        getPresenter().setView(this);
        initRecycler(rootView);
    }

    private void initRecycler(View rootView) {
        mSongs = new ArrayList<>();
        mSongAdapter = new SongAdapter(getActivity(), mSongs, this, true);
        mRecyclerviewSong = rootView.findViewById(R.id.recycler_song);
        mRecyclerviewSong.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerviewSong.setLayoutManager(layoutManager);
        mRecyclerviewSong.setAdapter(mSongAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getPresenter().loadSongs();
    }

    @Override
    public void getSongsSuccess(List<Song> songs) {
        mSongAdapter.addData(songs);
    }

    @Override
    public void getSongsFailure(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handlerItemClick(Song song) {

    }

    @Override
    public void handlerItemSongDownload(int position) {

    }

    @Override
    public void handlerItemSongSelection(int position) {

    }
}
