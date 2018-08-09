package vn.com.example.soundclound.data.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable{
    private String mArtworkUrl;
    private String mDescription;
    private boolean mDownloadable;
    private int mDownloadCount;
    private String mDownloadUrl;
    private long mDuration;
    private int mId;
    private String mKind;
    private int mLikesCount;
    private int mPlaybackCount;
    private String mTitle;
    private String mUri;
    private String mUserName;
    private String mAvatarUrl;

    protected Song(Parcel in) {
        mArtworkUrl = in.readString();
        mDescription = in.readString();
        mDownloadable = in.readByte() != 0;
        mDownloadCount = in.readInt();
        mDownloadUrl = in.readString();
        mDuration = in.readLong();
        mId = in.readInt();
        mKind = in.readString();
        mLikesCount = in.readInt();
        mPlaybackCount = in.readInt();
        mTitle = in.readString();
        mUri = in.readString();
        mUserName = in.readString();
        mAvatarUrl = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public Song(String nameSong, String nameSinger, String uri) {
        this.mTitle = nameSong;
        this.mUserName = nameSinger;
        this.mUri = uri;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.mAvatarUrl = avatarUrl;
    }

    public Song() {
    }

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        this.mArtworkUrl = artworkUrl;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public boolean isDownloadable() {
        return mDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        this.mDownloadable = downloadable;
    }

    public int getDownloadCount() {
        return mDownloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.mDownloadCount = downloadCount;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.mDownloadUrl = downloadUrl;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        this.mDuration = duration;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getKind() {
        return mKind;
    }

    public void setKind(String kind) {
        this.mKind = kind;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(int likesCount) {
        this.mLikesCount = likesCount;
    }

    public int getPlaybackCount() {
        return mPlaybackCount;
    }

    public void setPlaybackCount(int playbackCount) {
        this.mPlaybackCount = playbackCount;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        this.mUri = uri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mArtworkUrl);
        dest.writeString(mDescription);
        dest.writeByte((byte) (mDownloadable ? 1 : 0));
        dest.writeInt(mDownloadCount);
        dest.writeString(mDownloadUrl);
        dest.writeLong(mDuration);
        dest.writeInt(mId);
        dest.writeString(mKind);
        dest.writeInt(mLikesCount);
        dest.writeInt(mPlaybackCount);
        dest.writeString(mTitle);
        dest.writeString(mUri);
        dest.writeString(mUserName);
        dest.writeString(mAvatarUrl);
    }

    public void read(Parcel in) {
        mArtworkUrl = in.readString();
        mDescription = in.readString();
        mDownloadable = in.readByte() != 0;
        mDownloadCount = in.readInt();
        mDownloadUrl = in.readString();
        mDuration = in.readLong();
        mId = in.readInt();
        mKind = in.readString();
        mLikesCount = in.readInt();
        mPlaybackCount = in.readInt();
        mTitle = in.readString();
        mUri = in.readString();
        mUserName = in.readString();
        mAvatarUrl = in.readString();
    }
}
