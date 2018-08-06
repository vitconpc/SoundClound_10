package vn.com.example.soundclound.screen.online;

import android.os.Bundle;
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
import vn.com.example.soundclound.data.model.common.SpinnerType;
import vn.com.example.soundclound.data.model.common.adapter.SongAdapter;
import vn.com.example.soundclound.data.model.entity.Song;
import vn.com.example.soundclound.data.repository.SongReponsitory;
import vn.com.example.soundclound.screen.base.fragment.BaseFragment;

public class OnlineFragment extends BaseFragment<OnlinePresenter> implements CallbackSongAdapter
        , AdapterView.OnItemSelectedListener, OnlineContract.View {
    private Spinner mSpinerGenres;
    private RecyclerView mRecyclerViewSongs;
    private SongAdapter mSongAdapter;
    private String[] mListSpinner = {SpinnerType.GENRE_ALL_MUSIC, SpinnerType.GENRE_ALL_AUDIO
            , SpinnerType.GENRE_ALTERNATIVEROCK, SpinnerType.GENRE_AMBIENT
            , SpinnerType.GENRE_CLASSICAL, SpinnerType.GENRE_COUNTRY};

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
        getPresenter().loadSongs(SpinnerType.GENRE_ALL_MUSIC);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        getPresenter().loadSongs(parent.getAdapter().getItem(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void getSongsSuccess(List<Song> songs) {
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
