package vn.com.example.soundclound.data.model.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        return mSongs.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.handlerItemClick(getAdapterPosition());
                }
            });

            mImageDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.handlerItemSongDownload(getAdapterPosition());
                }
            });

            mImageSelection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.handlerItemSongSelection(getAdapterPosition());
                }
            });
        }

        public void bindData(Song song) {
            if (song != null) {
                Picasso.with(mContext).load(song.getAvatarUrl()).fit().centerCrop().into(mImageAvatar);
                mTextSongName.setText(song.getTitle());
                mTextArtist.setText(song.getUserName());
                mImageDownload.setVisibility(song.isDownloadable() ? View.VISIBLE : View.GONE);
            }
        }
    }
}
