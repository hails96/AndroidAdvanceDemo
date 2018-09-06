package lsh.framgia.com.androidadvancedemo.servicedemo.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lsh.framgia.com.androidadvancedemo.MainActivity;
import lsh.framgia.com.androidadvancedemo.R;
import lsh.framgia.com.androidadvancedemo.servicedemo.listener.OnMediaPlayerStatusListener;
import lsh.framgia.com.androidadvancedemo.servicedemo.model.Track;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private static final String ACTION_STATE_CHANGE = "lsh.framgia.com.androidadvancedemo.STATE_CHANGE";
    private static final String ACTION_NEXT = "lsh.framgia.com.androidadvancedemo.NEXT";
    private static final String ACTION_PREVIOUS = "lsh.framgia.com.androidadvancedemo.PREVIOUS";
    private static final int NOTIFICATION_ID = 1001;
    private static final int DEFAULT_REQUEST_CODE = 0;
    private static final int DEFAULT_FLAG = 0;
    private static final int[] sSongIds = {
            R.raw.nguoi_am_phu_osad,
            R.raw.cham_day_noi_dau_erik,
            R.raw.toimuonyeumotnguoi_khoimy
    };

    private final IBinder mBinder = new MusicBinder();
    private NotificationManagerCompat mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private OnMediaPlayerStatusListener mOnMediaPlayerStatusListener;
    private MediaPlayer mMediaPlayer;
    private List<Track> mPlaylist = new ArrayList<>();
    private int mCurrentSongPos;

    @Override
    public void onCreate() {
        super.onCreate();
        preparePlaylist();
        mNotificationManager = NotificationManagerCompat.from(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return START_NOT_STICKY;
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
        showNotification();
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
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnCompletionListener(this);
    }

    private void showNotification() {
        Track track = mPlaylist.get(mCurrentSongPos);
        mBuilder = new NotificationCompat.Builder(this, getPackageName())
                .setSmallIcon(R.drawable.ic_music)
                .setContentTitle(track.getName())
                .setContentText(track.getArtist())
                .setContentIntent(PendingIntent.getActivity(this, 0,
                        new Intent(this, MainActivity.class), 0))
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1))
                .addAction(R.drawable.ic_skip_previous, getString(R.string.action_previous),
                        createNewPendingIntent(ACTION_PREVIOUS));
        if (mMediaPlayer.isPlaying()) {
            mBuilder.addAction(R.drawable.ic_pause, getString(R.string.action_change_state),
                    createNewPendingIntent(ACTION_STATE_CHANGE));
        } else {
            mBuilder.addAction(R.drawable.ic_play, getString(R.string.action_change_state),
                    createNewPendingIntent(ACTION_STATE_CHANGE));
        }
        mBuilder.addAction(R.drawable.ic_skip_next, getString(R.string.action_next),
                createNewPendingIntent(ACTION_NEXT));
        Notification notification = mBuilder.build();
        mNotificationManager.notify(NOTIFICATION_ID, notification);
        startForeground(NOTIFICATION_ID, notification);
    }

    private PendingIntent createNewPendingIntent(String action) {
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(action);
        return PendingIntent.getService(this, DEFAULT_REQUEST_CODE, intent, DEFAULT_FLAG);
    }

    private void handleIntent(Intent intent) {
        if (intent.getAction() == null) return;
        switch (intent.getAction()) {
            case ACTION_NEXT:
                playNextSong();
                break;
            case ACTION_PREVIOUS:
                playPreviousSong();
                break;
            case ACTION_STATE_CHANGE:
                if (mMediaPlayer.isPlaying()) stopPlayingMusic();
                else resumePlayingMusic();
                break;
        }
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

    public Track getPlayingTrack() {
        return mPlaylist.get(mCurrentSongPos);
    }

    public void stopPlayingMusic() {
        mMediaPlayer.pause();
        showNotification();
        stopForeground(false);
        if (mOnMediaPlayerStatusListener != null) mOnMediaPlayerStatusListener.onPaused();
    }

    public void resumePlayingMusic() {
        mMediaPlayer.start();
        showNotification();
        if (mOnMediaPlayerStatusListener != null) mOnMediaPlayerStatusListener.onResumed();
    }

    public boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
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
