package lsh.framgia.com.androidadvancedemo.servicedemo.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import lsh.framgia.com.androidadvancedemo.R;
import lsh.framgia.com.androidadvancedemo.servicedemo.listener.OnMediaPlayerStatusListener;
import lsh.framgia.com.androidadvancedemo.servicedemo.model.Track;
import lsh.framgia.com.androidadvancedemo.servicedemo.service.MusicService;
import lsh.framgia.com.androidadvancedemo.utils.StringUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment implements OnMediaPlayerStatusListener {

    private static final int DELAY_TIME = 1000;

    private SeekBar mSeekBarProgress;
    private TextView mTextViewTitle;
    private TextView mTextViewProgress;
    private TextView mTextViewDuration;
    private ImageView mImageAction;
    private ImageView mImageNext;
    private ImageView mImagePrevious;

    private MusicService mMusicService;
    private ServiceConnection mServiceConnection;
    private boolean mBound;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mBound && mMusicService.isPlaying()) {
                int position = mMusicService.getCurrentPosition();
                mSeekBarProgress.setProgress(position);
                mTextViewProgress.setText(StringUtils.convertMillisToTimer(position));
            }
            mHandler.postDelayed(this, DELAY_TIME);
        }
    };

    public MusicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() == null) return;
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mMusicService = ((MusicService.MusicBinder) service).getService();
                mBound = true;
                mMusicService.setOnMediaPlayerStatusListener(MusicFragment.this);

                if (mMusicService.isPlaying()) {
                    onSongPrepared(mMusicService.getPlayingTrack());
                    mImageAction.setImageResource(R.drawable.ic_pause);
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mBound = false;
            }
        };
        Intent intent = new Intent(getActivity(), MusicService.class);
        getActivity().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        getActivity().startService(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupReferences(view);
        setupListeners();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity() == null) return;
        getActivity().unbindService(mServiceConnection);
        mBound = false;
    }

    @Override
    public void onSongPrepared(Track track) {
        mTextViewTitle.setText(StringUtils.formatTrackTitle(track.getName(), track.getArtist()));
        mSeekBarProgress.setMax(mMusicService.getDuration());
        mTextViewDuration.setText(StringUtils.convertMillisToTimer(mMusicService.getDuration()));
        mImageAction.setImageResource(R.drawable.ic_pause);
        getActivity().runOnUiThread(mRunnable);
    }

    @Override
    public void onPaused() {
        mImageAction.setImageResource(R.drawable.ic_play);
    }

    @Override
    public void onResumed() {
        mImageAction.setImageResource(R.drawable.ic_pause);
    }

    private void setupListeners() {
        mImageAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mMusicService.isInitiated()) {
                    mMusicService.playSong(0);
                    mImageAction.setImageResource(R.drawable.ic_pause);
                } else if (mMusicService.isPlaying()) {
                    mMusicService.stopPlayingMusic();
                } else {
                    mMusicService.resumePlayingMusic();
                }
            }
        });

        mImagePrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMusicService.playPreviousSong();
                mImageAction.setImageResource(R.drawable.ic_pause);
            }
        });

        mImageNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMusicService.playNextSong();
                mImageAction.setImageResource(R.drawable.ic_pause);
            }
        });

        mSeekBarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMusicService.seekTo(seekBar.getProgress());
                mHandler.postDelayed(mRunnable, DELAY_TIME);
            }
        });
    }

    private void setupReferences(View view) {
        mSeekBarProgress = view.findViewById(R.id.seek_bar_progress);
        mTextViewTitle = view.findViewById(R.id.text_view_title);
        mTextViewProgress = view.findViewById(R.id.text_view_progress);
        mTextViewDuration = view.findViewById(R.id.text_view_duration);
        mImageAction = view.findViewById(R.id.image_play);
        mImageNext = view.findViewById(R.id.image_next);
        mImagePrevious = view.findViewById(R.id.image_previous);
    }
}
