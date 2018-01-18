package com.framgia.soundclound.screen.local;

import android.databinding.BaseObservable;
import android.view.View;

import com.framgia.soundclound.data.model.Track;
/**
 * Created by ADMIN on 1/7/2018.
 */

public class ItemTrackLocalViewModel extends BaseObservable {

    private Track mTrack;
    private TrackClickListener mTrackClickListener;
    private OptionClickListener mOptionClickListener;
    private int mPosition;

    public ItemTrackLocalViewModel(Track track, TrackClickListener trackClickListener,
                                   OptionClickListener optionClickListener,
                                   int position) {
        mTrack = track;
        mTrackClickListener = trackClickListener;
        mOptionClickListener = optionClickListener;
        mPosition = position;
    }

    public void onClickTrack() {
        if (mTrackClickListener == null) {
            return;
        }
        mTrackClickListener.onItemTrackClick(mTrack, mPosition);
    }

    public String getTitle() {
        return mTrack != null ? mTrack.getTitle() : "";
    }

    public String getArtist() {
        if (mTrack != null) {
            return mTrack.getPublisherMetadata() != null ? mTrack.getPublisherMetadata().getArtist()
                    : "";
        }
        return "";
    }

    public void onClickTrack(View view) {
        if (mTrackClickListener == null) {
            return;
        }
        mTrackClickListener.onItemTrackClick(mTrack, mPosition);
    }

    public void onClickOption(View view) {
        if(mOptionClickListener == null) {
            return;
        }
        mOptionClickListener.onOptionClick(mTrack);
    }
}
