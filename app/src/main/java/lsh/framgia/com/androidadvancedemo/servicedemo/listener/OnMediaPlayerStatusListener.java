package lsh.framgia.com.androidadvancedemo.servicedemo.listener;

import lsh.framgia.com.androidadvancedemo.servicedemo.model.Track;

public interface OnMediaPlayerStatusListener {
    void onSongPrepared(Track track);

    void onPaused();

    void onResumed();
}
