package vn.com.example.soundclound.screen.offline;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import vn.com.example.soundclound.R;

public class OfflineFragment extends Fragment{

    private RecyclerView mRecyclerviewSong;
    private Spinner mSpinnerTypeSong;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_offline, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initSpinner();
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.type_song, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        mSpinnerTypeSong.setAdapter(adapter);
        mSpinnerTypeSong.setOnItemSelectedListener(new SpinnerItemSelected());
    }

    private void initViews(View view) {
        mRecyclerviewSong = view.findViewById(R.id.recycler_song);
        mSpinnerTypeSong = view.findViewById(R.id.spinner_type);
    }
}
