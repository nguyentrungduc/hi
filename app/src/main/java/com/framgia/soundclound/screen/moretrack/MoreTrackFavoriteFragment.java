package com.framgia.soundclound.screen.moretrack;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.soundclound.R;
import com.framgia.soundclound.data.model.Track;
import com.framgia.soundclound.databinding.FragmentMoreTrackFavoriteBinding;
import com.framgia.soundclound.databinding.FragmentMoreTrackPlaylistBinding;
import com.framgia.soundclound.util.Constant;

/**
 * Created by Sony on 1/18/2018.
 */

public class MoreTrackFavoriteFragment extends BottomSheetDialogFragment {
    private static Calback mCalback;

    public static MoreTrackFavoriteFragment newInstance(Track track, Calback calback) {
        MoreTrackFavoriteFragment fragment = new MoreTrackFavoriteFragment();
        setCallback(calback);
        Bundle args = new Bundle();
        args.putParcelable(Constant.ARGUMENT_TRACK, track);
        fragment.setArguments(args);
        return fragment;
    }

    public MoreTrackFavoriteFragment() {

    }

    public static void setCallback(Calback callback) {
        mCalback = callback;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentMoreTrackFavoriteBinding moreTrackBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_more_track_favorite, container, false);
        MoreTrackViewModel moreTrackViewModel = new MoreTrackViewModel(getContext(),
                (Track) getArguments().getParcelable(Constant.ARGUMENT_TRACK));
        moreTrackBinding.setViewModel(moreTrackViewModel);
        moreTrackViewModel.setCalback(mCalback);
        return moreTrackBinding.getRoot();
    }
}
