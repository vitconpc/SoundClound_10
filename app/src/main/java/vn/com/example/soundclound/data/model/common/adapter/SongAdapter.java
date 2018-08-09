package vn.com.example.soundclound.data.model.common.adapter;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vn.com.example.soundclound.R;
import vn.com.example.soundclound.data.model.entity.Song;
import vn.com.example.soundclound.screen.online.CallbackSongAdapter;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private Context mContext;
    private List<Song> mSongs;
    private CallbackSongAdapter mCallback;

    public SongAdapter(Context context, List<Song> songs, CallbackSongAdapter callbackSongAdapter) {
        this.mContext = context;
        this.mSongs = songs;
        this.mCallback = callbackSongAdapter;
        mSongs = new ArrayList<>();
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
            holder.bindData(mSongs.get(position));
    }


    @Override
    public int getItemCount() {
        return mSongs == null ? 0 : mSongs.size();
    }

    public void addData(List<Song> songs) {
        if (songs.size() > 0) {
            mSongs.addAll(songs);
            notifyDataSetChanged();
        }
    }

    public class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageAvatar;
        private ImageView mImageDownload;
        private ImageView mImageSelection;
        private TextView mTextSongName;
        private TextView mTextArtist;

        public SongViewHolder(View itemView) {
            super(itemView);
            mImageAvatar = itemView.findViewById(R.id.image_avatar_song);
            mImageDownload = itemView.findViewById(R.id.image_download_song);
            mImageSelection = itemView.findViewById(R.id.image_item_action);
            mTextSongName = itemView.findViewById(R.id.text_song_name);
            mTextArtist = itemView.findViewById(R.id.text_artist);
            itemView.setOnClickListener(this);
            mImageDownload.setOnClickListener(this);
            mImageSelection.setOnClickListener(this);
        }

        public void bindData(Song song) {
            if (song != null) {
                if (song.getAvatarUrl() != null){
                    Picasso.with(mContext).load(song.getAvatarUrl()).fit().centerCrop().into(mImageAvatar);
                }
                mTextSongName.setText(song.getTitle());
                mTextArtist.setText(song.getUserName());
                mImageDownload.setVisibility(song.isDownloadable() ? View.VISIBLE : View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_download_song:
                    mCallback.handlerItemSongDownload(getAdapterPosition());
                    break;
                case R.id.image_item_action:
                    mCallback.handlerItemSongSelection(getAdapterPosition());
                    break;
                default:
                    mCallback.handlerItemClick(getAdapterPosition());
                    break;
            }
        }
    }
}
