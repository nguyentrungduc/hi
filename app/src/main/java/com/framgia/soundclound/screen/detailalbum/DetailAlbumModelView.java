package com.framgia.soundclound.screen.detailalbum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.framgia.soundclound.data.model.Track;
import com.framgia.soundclound.data.source.local.SharePreferences;
import com.framgia.soundclound.data.source.repository.AlbumRepository;
import com.framgia.soundclound.screen.BaseOnItemClick;
import com.framgia.soundclound.screen.addtracktoalbum.AddTrackActivity;
import com.framgia.soundclound.screen.moretrack.Calback;
import com.framgia.soundclound.screen.moretrack.MoreTrackPlayListFragment;
import com.framgia.soundclound.screen.playtrack.PlayTrackActivity;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Bui Danh Nam on 11/1/2018.
 */

public class DetailAlbumModelView extends BaseObservable implements BaseOnItemClick<Track> , Calback{
    private DetailAlbumAdapter mDetailAlbumAdapter;
    private Context mContext;
    private List<Track> mTracks;
    private int mIdAlbum;

    public DetailAlbumModelView(Context context, int idAlbum) {
        mContext = context;
        mTracks = AlbumRepository.getInstance(mContext).getAllTrack(idAlbum);
        mDetailAlbumAdapter = new DetailAlbumAdapter(mTracks);
        mIdAlbum = idAlbum;
        mDetailAlbumAdapter.setOnItemClick(this);
    }

    @Bindable
    public DetailAlbumAdapter getDetailAlbumAdapter() {
        return mDetailAlbumAdapter;
    }

    public void setDetailAlbumAdapter(DetailAlbumAdapter detailAlbumAdapter) {
        mDetailAlbumAdapter = detailAlbumAdapter;
        notifyPropertyChanged(0);
    }

    public void onUpdate() {
        mTracks = AlbumRepository.getInstance(mContext).getAllTrack(mIdAlbum);
        mDetailAlbumAdapter.updateData(mTracks);
    }

    @Override
    public void onItemClick(Track track, int pos) {
        SharePreferences.getInstance().putListTrack(new Gson().toJson(mTracks));
        SharePreferences.getInstance().putTrack(new Gson().toJson(track));
        SharePreferences.getInstance().putIndex(pos);
        mContext.startActivity(PlayTrackActivity.getInstance(mContext));
    }

    public void onFabAddAlbumClick() {
        Intent intent = AddTrackActivity.getInstance(mContext, mIdAlbum, AddTrackActivity.KEY_ADD_TRACK_ALBUM);
        ((Activity) mContext).startActivityForResult(intent, DetailAlbumActivity.REQUEST_UPDATE_DATA);
    }

    @Override
    public void onIconMoreClick(ImageView imageView, final Track track) {
        MoreTrackPlayListFragment.newInstance(track, mIdAlbum , this)
                .show(((AppCompatActivity) mContext).getSupportFragmentManager(), null);

//        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//        PopupMenu popupMenu = new PopupMenu(mContext, imageView);
//        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_track_local, popupMenu.getMenu());
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.remove_track:
//                        handleRemoveTrack(mIdAlbum, track, builder);
//                        builder.show();
//                        break;
//                    case R.id.add_favorite:
//                        handleAddFavorite(track);
//                        break;
//                }
//                return true;
//            }
//        });
//        popupMenu.show();
    }

    @Override
    public void onRemoveTrack(boolean result) {
        if(result){
            onUpdate();
        }
    }

//    private void handleAddFavorite(Track track) {
//        if (AlbumRepository.getInstance(mContext).addTrack(Constant.TRACKS_FAVORITE, track)) {
////            AlbumRepository.getInstance(mContext)
////                    .addTrack(Constant.TRACKS_FAVORITE, mTrack)
//            Toast.makeText(mContext, R.string.msg_notifi_favorite_track_suggest, Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(mContext, R.string.msg_notifi_favorite_track_fail, Toast.LENGTH_SHORT).show();
//
//        }
//    }
//
//    private void handleRemoveTrack(final int idAlbum, final Track track,
//                                   AlertDialog.Builder builder) {
//        builder.setTitle(R.string.msg_remove_track_album);
//        builder.setPositiveButton(R.string.action_ok,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        boolean resultRemoveTrack = AlbumRepository.getInstance(mContext)
//                                .removeTrack(idAlbum, track);
//                        if (resultRemoveTrack) {
//                            mDetailAlbumAdapter.updateData(
//                                    AlbumRepository.getInstance(mContext).getAllTrack(idAlbum));
//                        }
//                    }
//                });
//        builder.setNegativeButton(R.string.action_cancel, null);
//    }
}
