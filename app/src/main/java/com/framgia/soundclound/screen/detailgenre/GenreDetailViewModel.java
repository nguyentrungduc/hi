package com.framgia.soundclound.screen.detailgenre;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.support.v7.app.AppCompatActivity;

import com.framgia.soundclound.BR;
import com.framgia.soundclound.data.model.Genre;
import com.framgia.soundclound.data.model.Track;
import com.framgia.soundclound.data.source.TrackDataSource;
import com.framgia.soundclound.data.source.TrackRepository;
import com.framgia.soundclound.data.source.local.SharePreferences;
import com.framgia.soundclound.data.source.remote.TrackRemoteDataSource;
import com.framgia.soundclound.screen.EndSrcollListenner;
import com.framgia.soundclound.screen.moretrack.MoreTrackFragment;
import com.framgia.soundclound.screen.playtrack.PlayTrackActivity;
import com.framgia.soundclound.util.Constant;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Sony on 1/5/2018.
 */

public class GenreDetailViewModel extends BaseObservable implements TrackClickListener,
        MoreInfoClickListener {

    private GenreDetailAdapter mGenreDetailAdapter;
    private Genre mGenre;
    private Context mContext;
    private TrackRepository mTrackRepository;
    public ObservableField<EndSrcollListenner> mEndSrcollListenner = new ObservableField<>();
    int offSet = Constant.OFF_SET_DEFAULT;

    public GenreDetailViewModel(Context context, Genre genre) {
        mContext = context;
        mGenre = genre;
        mTrackRepository = new TrackRepository(TrackRemoteDataSource.getInstance());
        mGenreDetailAdapter = new GenreDetailAdapter();
        mGenreDetailAdapter.setTrackClickListener(this);
        mGenreDetailAdapter.setMoreInfoClickListener(this);
        getData(Constant.OFF_SET_DEFAULT);
        mEndSrcollListenner.set(new EndSrcollListenner() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                getData(offSet + 20);
                checkListTrack();
            }
        });
    }

    public void checkListTrack() {
        if (mGenre.getKeyname().equals(SharePreferences.getInstance().getGenre())) {
            SharePreferences.getInstance().putListTrack(new Gson().toJson(
                    mGenreDetailAdapter.getData()));
        }
    }

    private void getData(int offSet) {
        mTrackRepository.getListTrack(Constant.BASE_URL + Constant.PARA,
                mGenre.getKeyname(), Constant.LIMIT_DEFAULT, offSet,
                new TrackDataSource.Callback<List<Track>>() {
                    @Override
                    public void onStartLoading() {

                    }

                    @Override
                    public void onGetSuccess(List<Track> data) {
                        mGenreDetailAdapter.addData(data);
                        mEndSrcollListenner.get().resetState();
                    }

                    @Override
                    public void onGetFailure(String message) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Bindable
    public GenreDetailAdapter getGenreDetailAdapter() {
        return mGenreDetailAdapter;
    }

    public void setmGenreDetailAdapter(GenreDetailAdapter genreDetailAdapter) {
        this.mGenreDetailAdapter = genreDetailAdapter;
        notifyPropertyChanged(BR.genreDetailAdapter);
    }

    @Bindable
    public Genre getGenre() {
        return mGenre;
    }

    public void setGenre(Genre genre) {
        mGenre = genre;
        notifyPropertyChanged(BR.genre);
    }

    @Override
    public void onItemTrackClick(Track track, int position) {
        // TODO: 1/10/2018 open playtrackactivity
        SharePreferences.getInstance().putListTrack(new Gson().toJson(
                mGenreDetailAdapter.getData()));
        SharePreferences.getInstance().putTrack(new Gson().toJson(track));
        SharePreferences.getInstance().putIndex(position);
        SharePreferences.getInstance().putGenre(getGenre().getKeyname());
        mContext.startActivity(PlayTrackActivity.getInstance(mContext));

    }

    @Override
    public void onClickMore(Track track) {
        MoreTrackFragment.newInstance(track)
                .show(((AppCompatActivity) mContext).getSupportFragmentManager(), null);
    }
}
