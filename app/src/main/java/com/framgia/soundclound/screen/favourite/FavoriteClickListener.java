package com.framgia.soundclound.screen.favourite;

import android.view.View;

import com.framgia.soundclound.data.model.Track;

/**
 * Created by Sony on 1/15/2018.
 */

public interface FavoriteClickListener {

    void onTrackClick(Track track, int posChange, View view);
}
