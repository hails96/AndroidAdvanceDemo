package lsh.framgia.com.androidadvancedemo.servicedemo.model;

import android.net.Uri;

public class Track {

    private String mName;
    private String mArtist;
    private Uri mUri;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public Uri getUri() {
        return mUri;
    }

    public void setUri(Uri uri) {
        mUri = uri;
    }
}
