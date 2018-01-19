package com.framgia.soundclound.screen.playtrack;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.os.IBinder;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.framgia.soundclound.R;
import com.framgia.soundclound.data.model.Track;
import com.framgia.soundclound.data.source.local.SharePreferences;
import com.framgia.soundclound.service.MusicService;
import com.framgia.soundclound.util.StringUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Sony on 1/11/2018.
 */

public class PlayTrackViewModel extends BaseObservable implements SeekBar.OnSeekBarChangeListener {
    private String mTotalDuration;
    private String mCurentDuration;
    private int mProgressSeekBar;
    private List<Track> mTracks;
    private int mIndexCurrentTrack;
    private boolean mPlay;
    private Track mTrack;
    private static MusicService mMusicService = null;
    private boolean mMusicBound = false;
    private OnClickTrackListener mOnClickTrackListener;
    private boolean mRuning = true;
    private boolean mSuff;
    private boolean mRepaet;

    public void setOnClickTrackListener(OnClickTrackListener onClickTrackListener) {
        mOnClickTrackListener = onClickTrackListener;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder musicServiceBinder = (MusicService.MusicBinder) service;
            mMusicService = musicServiceBinder.getService();
            mMusicBound = true;
            boolean x = mMusicService.checkTrackPlay(mTracks.get(mIndexCurrentTrack));
            if (!mMusicService.isPlay()
                    || !mMusicService.checkTrackPlay(mTracks.get(mIndexCurrentTrack))) {
                startPlayMusic();
            } else if (mMusicService.checkTrackPlay(mTracks.get(mIndexCurrentTrack))) {
                updateProcess();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMusicBound = false;
        }
    };

    public PlayTrackViewModel(List<Track> tracks, int indexCurentTrack) {
        mTracks = tracks;
        mIndexCurrentTrack = indexCurentTrack;
        setTrack(mTracks.get(indexCurentTrack));
        setRepaet(SharePreferences.getInstance().getREpeat());
        setSuff(SharePreferences.getInstance().getSuff());
    }

    @Bindable
    public boolean isSuff() {
        return mSuff;
    }

    public void setSuff(boolean suff) {
        mSuff = suff;
        notifyPropertyChanged(0);
    }

    @Bindable
    public boolean isRepaet() {
        return mRepaet;

    }

    public void setRepaet(boolean repaet) {
        mRepaet = repaet;
        notifyPropertyChanged(0);

    }

    @Bindable
    public boolean isPlay() {
        return mPlay;
    }

    public void setPlay(boolean play) {
        mPlay = play;
        notifyPropertyChanged(0);
    }

    @Bindable
    public Track getTrack() {
        return mTrack;
    }

    public void setTrack(Track track) {
        mTrack = track;
        notifyPropertyChanged(0);
    }

    public String getArtist() {
        if (mTrack != null) {
            return mTrack.getPublisherMetadata() != null ? mTrack.getPublisherMetadata().getArtist()
                    : "";
        }
        return "";
    }

    public void setRuning(boolean runing) {
        mRuning = runing;
    }

    public String getImageTrack() {
        if (mTrack != null) {
            return mTrack.getArtworkUrl();
        }
        return null;
    }

    @BindingAdapter("setAni")
    public static void setImage(CircleImageView imageView, boolean animation) {
        Animation mAnimation = AnimationUtils.loadAnimation(imageView.getContext(), R.anim.rotation);
        if (animation) {
            imageView.startAnimation(mAnimation);
        } else {
            mAnimation.cancel();
        }
    }

    @BindingAdapter("setImageSuff")
    public static void setImage(ImageView view, boolean suff) {
        if (suff) {
            view.setImageResource(R.drawable.ic_shuffle_red_24dp);
        } else {
            view.setImageResource(R.drawable.ic_shuffle_black_24dp);
        }
    }

    @BindingAdapter("setImageRepeat")
    public static void setImagerepeat(ImageView view, boolean repeat) {
        if (repeat) {
            view.setImageResource(R.drawable.ic_loop_red_24dp);
        } else {
            view.setImageResource(R.drawable.ic_loop_black_24dp);
        }
    }

    public String getTitle() {
        return mTrack != null ? mTrack.getTitle() : "";
    }

    @Bindable
    public String getTotalDuration() {
        return mTotalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        mTotalDuration = totalDuration;
        notifyPropertyChanged(0);
    }

    @Bindable
    public String getCurentDuration() {
        return mCurentDuration;
    }

    public void setCurentDuration(String curentDuration) {
        mCurentDuration = curentDuration;
        notifyPropertyChanged(0);
    }

    @Bindable
    public int getProgressSeekBar() {
        return mProgressSeekBar;
    }

    public void setProgressSeekBar(int progressSeekBar) {
        mProgressSeekBar = progressSeekBar;
        notifyPropertyChanged(0);
    }

    public ServiceConnection getServiceConnection() {
        return mServiceConnection;
    }

    public boolean isMusicBound() {
        return mMusicBound;
    }

    public void startPlayMusic() {
        mMusicService.play();
        updateProcess();
        setPlay(true);
    }

    private void updateProcess() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mRuning) {
                    try {
                        Thread.sleep(1000);
                        long totalDuration = mMusicService.getDuration();
                        long currentDuration = mMusicService.getPosition();
                        setCurentDuration(StringUtil.milliSecondsToTimer(currentDuration));
                        setTotalDuration(StringUtil.milliSecondsToTimer(totalDuration));
                        int progress = (int) (StringUtil.getProgressPercentage(currentDuration,
                                totalDuration));
                        setProgressSeekBar(progress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        break;
                    }
                }
            }
        }).start();
    }

    public void onClickPause(View view) {
        ImageView imageView = (ImageView) view;
        if (mMusicService.isPlay()) {
            mMusicService.pause();
            imageView.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            setPlay(false);
        } else {
            mMusicService.start();
            imageView.setImageResource(R.drawable.ic_pause_black_24dp);
            setPlay(true);
        }
    }

    public void onClickNext(View view) {
        mMusicService.next();
        updateUI();
    }

    public void onClickShuffle(View view) {
        mMusicService.setShuffle();
        ImageView imageView = (ImageView) view;
        boolean suff = mMusicService.isShuffle();
        SharePreferences.getInstance().putSuff(suff);
        setSuff(suff);

    }

    public void onClickLoop(View view) {
        mMusicService.setRepeat();
        boolean repeat = mMusicService.isRepeat();
        SharePreferences.getInstance().putRepeat(repeat);
        setRepaet(repeat);
    }

    public void onClickPrevious(View view) {
        mMusicService.prev();
        updateUI();
    }

    public void updateUI() {
        List<Track> tracks = SharePreferences.getInstance().getListTrack();
        mIndexCurrentTrack = SharePreferences.getInstance().getIndex();
        if (tracks != null) {
            setTrack(tracks.get(mIndexCurrentTrack));
        }
        setPlay(mMusicService.isPlay());
    }

    public void onClickBack(View view) {
        mOnClickTrackListener.onClickBack(view);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int currentPosition = StringUtil.progressToTimer(
                seekBar.getProgress(), mMusicService.getDuration());
        mMusicService.seekto(currentPosition);
    }

}
