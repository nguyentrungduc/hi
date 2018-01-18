package com.framgia.soundclound.screen.moretrack;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framgia.soundclound.R;
import com.framgia.soundclound.data.model.Track;
import com.framgia.soundclound.databinding.FragmentMoreTrackLocalBinding;
import com.framgia.soundclound.util.Constant;

/**
 * Created by Sony on 1/18/2018.
 */

public class MoreTrackLocalFragment extends BottomSheetDialogFragment {
    public static MoreTrackLocalFragment newInstance(Track track) {
        MoreTrackLocalFragment fragment = new MoreTrackLocalFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constant.ARGUMENT_TRACK, track);
        fragment.setArguments(args);
        return fragment;
    }

    public MoreTrackLocalFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentMoreTrackLocalBinding moreTrackBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_more_track_local, container, false);
        moreTrackBinding.setViewModel(new MoreTrackViewModel(getContext(),
                (Track) getArguments().getParcelable(Constant.ARGUMENT_TRACK)));
        return moreTrackBinding.getRoot();
    }
}
