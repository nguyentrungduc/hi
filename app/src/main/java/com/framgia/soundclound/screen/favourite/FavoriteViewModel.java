package com.framgia.soundclound.screen.favourite;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.framgia.soundclound.data.model.Track;
import com.framgia.soundclound.data.source.local.SharePreferences;
import com.framgia.soundclound.data.source.repository.AlbumRepository;
import com.framgia.soundclound.screen.moretrack.Calback;
import com.framgia.soundclound.screen.moretrack.MoreTrackFavoriteFragment;
import com.framgia.soundclound.screen.playtrack.PlayTrackActivity;
import com.framgia.soundclound.util.Constant;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Sony on 1/15/2018.
 */

public class FavoriteViewModel extends BaseObservable implements ItemClickListener,
        FavoriteClickListener {
    private FavoriteTrackAdapter mFavoriteTrackAdapter;
    private Context mContext;
    private List<Track> mTracksFav;

    public FavoriteViewModel(Context context) {
        mContext = context;
        mFavoriteTrackAdapter = new FavoriteTrackAdapter();
        mTracksFav = AlbumRepository.getInstance(mContext).getAllTrack(Constant.TRACKS_FAVORITE);
        mFavoriteTrackAdapter.addData(mTracksFav);
        mFavoriteTrackAdapter.setItemClickListener(this);
        mFavoriteTrackAdapter.setFavoriteClickListener(this);
    }

    @Bindable
    public FavoriteTrackAdapter getFavoriteTrackAdapter() {
        return mFavoriteTrackAdapter;
    }

    public void setFavoriteTrackAdapter(FavoriteTrackAdapter favoriteTrackAdapter) {
        mFavoriteTrackAdapter = favoriteTrackAdapter;
        notifyPropertyChanged(com.framgia.soundclound.BR.favoriteTrackAdapter);
    }

    @Override
    public void onItemClick(Track track, int pos) {
        SharePreferences.getInstance().putListTrack(new Gson().toJson(
                mTracksFav));
        SharePreferences.getInstance().putTrack(new Gson().toJson(track));
        SharePreferences.getInstance().putIndex(pos);
        mContext.startActivity(PlayTrackActivity.getInstance(mContext));

    }

    @Override
    public void onTrackClick(Track track, final int posChange, View view) {
        MoreTrackFavoriteFragment.newInstance(track,
                new Calback() {
                    @Override
                    public void onRemoveTrack(boolean result) {
                        if (result) {
                            mFavoriteTrackAdapter.updateData(AlbumRepository
                                    .getInstance(mContext)
                                    .getAllTrack(Constant.TRACKS_FAVORITE));
                        }
                    }
                })
                .show(((AppCompatActivity) mContext).
                        getSupportFragmentManager(), null);


    }

}
