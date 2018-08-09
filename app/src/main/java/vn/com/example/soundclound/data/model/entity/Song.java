package vn.com.example.soundclound.data.model.entity;

public class Song {
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
    private String mPath;

    public Song(String nameSong, String nameSinger, String path){
        this.mTitle = nameSong;
        this.mUserName = nameSinger;
        this.mPath = path;
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
}
