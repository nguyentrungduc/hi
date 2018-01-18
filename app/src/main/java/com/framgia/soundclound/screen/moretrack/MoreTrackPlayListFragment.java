package com.framgia.soundclound.screen.moretrack;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.soundclound.R;
import com.framgia.soundclound.data.model.Track;
import com.framgia.soundclound.databinding.FragmentMoreTrackPlaylistBinding;
import com.framgia.soundclound.util.Constant;

/**
 * Created by Sony on 1/18/2018.
 */

public class MoreTrackPlayListFragment extends BottomSheetDialogFragment {
    private static Calback mCalback;

    public static MoreTrackPlayListFragment newInstance(Track track, int idAlbum, Calback calback) {
        MoreTrackPlayListFragment fragment = new MoreTrackPlayListFragment();
        setCallback(calback);
        Bundle args = new Bundle();
        args.putParcelable(Constant.ARGUMENT_TRACK, track);
        args.putInt(Constant.ARGUMENT_ID_ALBUM, idAlbum);
        fragment.setArguments(args);
        return fragment;
    }

    public MoreTrackPlayListFragment() {

    }

    public static void setCallback(Calback callback) {
        mCalback = callback;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentMoreTrackPlaylistBinding moreTrackBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_more_track_playlist, container, false);
        MoreTrackViewModel moreTrackViewModel = new MoreTrackViewModel(getContext(),
                (Track) getArguments().getParcelable(Constant.ARGUMENT_TRACK),
                (int) getArguments().getInt(Constant.ARGUMENT_ID_ALBUM));
        moreTrackBinding.setViewModel(moreTrackViewModel);
        moreTrackViewModel.setCalback(mCalback);
        return moreTrackBinding.getRoot();
    }
}
