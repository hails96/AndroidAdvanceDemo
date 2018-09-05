package lsh.framgia.com.androidadvancedemo.servicedemo.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lsh.framgia.com.androidadvancedemo.R;
import lsh.framgia.com.androidadvancedemo.servicedemo.listener.OnMediaPlayerStatusListener;
import lsh.framgia.com.androidadvancedemo.servicedemo.model.Track;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private static final int[] sSongIds = {
            R.raw.nguoi_am_phu_osad,
            R.raw.cham_day_noi_dau_erik,
            R.raw.toimuonyeumotnguoi_khoimy
    };
    private final IBinder mBinder = new MusicBinder();
    private OnMediaPlayerStatusListener mOnMediaPlayerStatusListener;
    private MediaPlayer mMediaPlayer;
    private List<Track> mPlaylist = new ArrayList<>();
    private int mCurrentSongPos;

    @Override
    public void onCreate() {
        super.onCreate();
        preparePlaylist();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) mMediaPlayer.release();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        if (mOnMediaPlayerStatusListener != null)
            mOnMediaPlayerStatusListener.onSongPrepared(mPlaylist.get(mCurrentSongPos));
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mMediaPlayer.reset();
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.reset();
        playNextSong();
    }

    private void preparePlaylist() {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        for (int songId : sSongIds) {
            Track track = new Track();
            track.setUri(Uri.parse(
                    getString(R.string.resources_path_place_holder, getPackageName(), songId)));
            mmr.setDataSource(this, track.getUri());
            track.setName(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
            track.setArtist(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
            mPlaylist.add(track);
        }
    }

    private void initMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnCompletionListener(this);
    }

    public void playSong(int position) {
        if (mMediaPlayer == null) initMediaPlayer();

        if (mMediaPlayer.isPlaying()
                || (!mMediaPlayer.isPlaying() && mMediaPlayer.getCurrentPosition() > 0)) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
        }
        try {
            mMediaPlayer.setDataSource(this, mPlaylist.get(position).getUri());
            mMediaPlayer.prepareAsync();
            mCurrentSongPos = position;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playNextSong() {
        if (mCurrentSongPos == mPlaylist.size() - 1) playSong(0);
        else playSong(++mCurrentSongPos);
    }

    public void playPreviousSong() {
        if (mCurrentSongPos == 0) playSong(mPlaylist.size() - 1);
        else playSong(--mCurrentSongPos);
    }

    public void stopPlayingMusic() {
        mMediaPlayer.pause();
        if (mOnMediaPlayerStatusListener != null) mOnMediaPlayerStatusListener.onPaused();
    }

    public void resumePlayingMusic() {
        mMediaPlayer.start();
        if (mOnMediaPlayerStatusListener != null) mOnMediaPlayerStatusListener.onResumed();
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    public boolean isInitiated() {
        return mMediaPlayer != null;
    }

    public void setOnMediaPlayerStatusListener(OnMediaPlayerStatusListener listener) {
        mOnMediaPlayerStatusListener = listener;
    }

    public void seekTo(int progress) {
        mMediaPlayer.seekTo(progress);
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
}
