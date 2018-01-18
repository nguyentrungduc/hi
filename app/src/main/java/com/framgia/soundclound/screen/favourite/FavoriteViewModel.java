package com.framgia.soundclound.screen.favourite;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.framgia.soundclound.R;
import com.framgia.soundclound.data.model.Album;
import com.framgia.soundclound.data.model.Track;
import com.framgia.soundclound.data.source.local.SharePreferences;
import com.framgia.soundclound.data.source.repository.AlbumRepository;
import com.framgia.soundclound.screen.playtrack.PlayTrackActivity;
import com.framgia.soundclound.util.Constant;
import com.framgia.soundclound.util.designhelper.Calback;
import com.framgia.soundclound.util.designhelper.CusDialog;
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
    public void onTrackClick(Track track, int posChange, View view) {
        showPopupMenu(track, posChange, view);
    }

    private void showPopupMenu(final Track track, final int posChange, View view) {
        final PopupMenu popupMenu = new PopupMenu(mContext, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_favorite, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.un_favorite:
                        handleUnFavorite(track, posChange);
                        break;
                    case R.id.add_playing:
                        handleAddPlaying(track);
                        break;
                    case R.id.add_playlist:
                        handleAddPlaylisy(track);
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void handleUnFavorite(Track track, int posChange) {
        boolean resultRemoveTrack = AlbumRepository.getInstance(mContext)
                .removeTrack(Constant.TRACKS_FAVORITE, track);
        if (resultRemoveTrack) {
            mFavoriteTrackAdapter.updateData(posChange);
            Toast.makeText(mContext, "Su", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleAddPlaying(Track track) {
        boolean result = SharePreferences.getInstance().addTrack(track);
        if (result) {
            Toast.makeText(mContext, R.string.add_playing_su, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, R.string.add_playing_fl, Toast.LENGTH_SHORT).show();

        }

    }

    private void handleAddPlaylisy(final Track track) {
        List<Album> albums = AlbumRepository.getInstance(mContext).getAllAlbum();
        CusDialog cusDialog = new CusDialog();
        cusDialog.setCalback(new Calback() {
            @Override
            public void onResult(Album album) {
                boolean result = AlbumRepository.getInstance(mContext).addTrack(album.getId(), track);
                if (result) {
                    Toast.makeText(mContext, "Thêm Thành Công !! ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Thêm Thất Bại !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cusDialog.showDialog(mContext, albums);

    }

}
