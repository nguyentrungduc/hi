package com.framgia.soundclound.screen.moretrack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.framgia.soundclound.R;
import com.framgia.soundclound.data.model.Album;
import com.framgia.soundclound.data.model.Track;
import com.framgia.soundclound.data.source.local.SharePreferences;
import com.framgia.soundclound.data.source.remote.TrackDownloadManager;
import com.framgia.soundclound.data.source.repository.AlbumRepository;
import com.framgia.soundclound.util.Constant;
import com.framgia.soundclound.util.designhelper.Calback;
import com.framgia.soundclound.util.designhelper.CusDialog;

import java.util.List;

/**
 * Created by Sony on 1/10/2018.
 */

public class MoreTrackViewModel extends BaseObservable {
    private Track mTrack;
    private Context mContext;
    private boolean mFavorite;
    private int mIdAlbum;
    private static final String TAG = MoreTrackViewModel.class.toString();
    private com.framgia.soundclound.screen.moretrack.Calback mCalback;

    public MoreTrackViewModel(Context context, Track track) {
        mContext = context;
        mTrack = track;
        mFavorite = AlbumRepository.getInstance(mContext)
                .checkTrackExistAlbum(Constant.TRACKS_FAVORITE, mTrack);
    }

    public MoreTrackViewModel(Context context, Track track, int idAlbum) {
        mContext = context;
        mTrack = track;
        mFavorite = AlbumRepository.getInstance(mContext)
                .checkTrackExistAlbum(Constant.TRACKS_FAVORITE, mTrack);
        mIdAlbum = idAlbum;
    }

    public void setCalback(com.framgia.soundclound.screen.moretrack.Calback calback) {
        mCalback = calback;
    }

    public String getTitle() {
        return mTrack != null ? mTrack.getTitle() : "";
    }

    public String getArtist() {
        if (mTrack != null) {
            return mTrack.getPublisherMetadata() != null ? mTrack.getPublisherMetadata()
                    .getArtist() : "";
        }
        return "";
    }

    @BindingAdapter("setImage")
    public static void setImage(ImageView view, boolean favorite) {
        if (favorite) {
            view.setImageResource(R.drawable.ic_favorite_red_24dp);
        } else {
            view.setImageResource(R.drawable.ic_favorite_white_24dp);
        }
    }


    @BindingAdapter("setText")
    public static void setText(TextView view, boolean favorite) {
        if (favorite) {
            view.setText(R.string.title_exist_favorite);
            view.setClickable(false);
        } else {
            view.setText(R.string.title_add_favorite);
        }
    }

    @Bindable
    public boolean isFavorite() {
        return mFavorite;
    }

    public void setFavorite(boolean favorite) {
        mFavorite = favorite;
        notifyPropertyChanged(0);
    }

    public String getUrl() {
        return mTrack != null ? mTrack.getArtworkUrl() : Constant.LINK_DEFAULT;
    }

    public String getCanDownLoad() {
        if (mTrack != null) {
            return mTrack.isDownloadable() ? mContext.getString(R.string.title_candownload)
                    : mContext.getString(R.string.title_cantdownload);
        }
        return mContext.getString(R.string.title_cantdownload);
    }

    public void onClickLike(View view) {
        mFavorite = AlbumRepository.getInstance(mContext)
                .checkTrackExistAlbum(Constant.TRACKS_FAVORITE, mTrack);
        if (mFavorite) {
            handleUnFavorite();
        } else {
            handleFavorite();
        }


    }

    public void onClickUnFavorite(View view) {
        boolean resultRemoveTrack = AlbumRepository.getInstance(mContext)
                .removeTrack(Constant.TRACKS_FAVORITE, mTrack);
        mCalback.onRemoveTrack(resultRemoveTrack);
    }

    public void onClickRemovePlayLIst(View view) {
        boolean resultRemoveTrack = AlbumRepository.getInstance(mContext)
                .removeTrack(mIdAlbum, mTrack);
        mCalback.onRemoveTrack(resultRemoveTrack);
        // TODO: 18/1/2018
    }

    private void handleFavorite() {
        boolean resultAddFavorite = AlbumRepository.getInstance(mContext)
                .addTrack(Constant.TRACKS_FAVORITE, mTrack);
        setFavorite(resultAddFavorite);
    }

    private void handleUnFavorite() {
        boolean resultRemoveTrack = AlbumRepository.getInstance(mContext)
                .removeTrack(Constant.TRACKS_FAVORITE, mTrack);
        setFavorite(!resultRemoveTrack);
    }

//    private void showNotification(int notifi) {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(
//                mContext, R.style.Theme_AppCompat_Light_Dialog_Alert);
//        builder.setTitle(R.string.msg_notifi_favorite_track);
//        builder.setMessage(notifi);
//        builder.setNeutralButton(R.string.action_ok, null);
//        builder.show();
//    }

    public void onClickDownload(View view) {
        if (mTrack.isDownloadable()) {
            TrackDownloadManager trackDownloadManager = new TrackDownloadManager(mContext, mTrack);
            trackDownloadManager.download();
        }
    }

    public void onClickAddToList(View view) {
        boolean result = SharePreferences.getInstance().addTrack(mTrack);
        if (result) {
            Toast.makeText(mContext, R.string.add_playing_su, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, R.string.add_playing_fl, Toast.LENGTH_SHORT).show();

        }
    }

    public void onClickAddToAlbum(View view) {
        handleAddPlaylisy(mTrack);
    }

    private void handleAddPlaylisy(final Track track) {
        List<Album> albums = AlbumRepository.getInstance(mContext).getAllAlbum();
        CusDialog cusDialog = new CusDialog();
        cusDialog.showDialog(mContext, albums);
        cusDialog.setCalback(new Calback() {
            @Override
            public void onResult(Album album) {
                boolean result = AlbumRepository.getInstance(mContext).addTrack(album.getId(), track);
                if (result) {
                    Toast.makeText(mContext, "Thêm Thành Công !! ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Bài hát đã có trong playlist !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onAddNewAlbum(boolean b) {
                if (b) {
                    handleAddNewAlbum(track);
                }
            }
        });


    }

    private void handleAddNewAlbum(final Track track) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.title_add_album);
        builder.setMessage(R.string.msg_name_album);
        final EditText input = new EditText(builder.getContext());
        builder.setView(input);
        builder.setPositiveButton(R.string.action_add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nameAlbum = input.getText().toString().trim();
                if (nameAlbum.isEmpty()) {
                    Toast.makeText(
                            mContext, R.string.msg_err_name_null, Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
                Album albumTemp = new Album();
                albumTemp.setName(nameAlbum);
                boolean result = AlbumRepository.getInstance(mContext).addAlbum(albumTemp);
                if (result) {
                    boolean resultAddAbum = AlbumRepository.getInstance(mContext).addTrack(nameAlbum, track);
                    if (resultAddAbum) {
                        Toast.makeText(mContext, "Thêm Thành Công !! ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "Thêm Thất Bại !! ", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(
                            mContext, R.string.msg_err_name_exist, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton(R.string.action_cancel, null);
        builder.show();
    }

}
