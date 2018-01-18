package com.framgia.soundclound.util.designhelper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.framgia.soundclound.R;
import com.framgia.soundclound.data.model.Album;

import java.util.List;

/**
 * Created by Bui Danh Nam on 18/1/2018.
 */

public class CusDialog {
    private Calback mCalback;
    private Album mAlbumSelect;

    public void setCalback(Calback calback) {
        mCalback = calback;
    }

    public void showDialog(final Context context, List<Album> albums) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setIcon(R.drawable.icon_music_all);
        builderSingle.setTitle("Select One Name:-");
        final AdapterAlbum albumAdapterAlbum = new AdapterAlbum(context, albums);

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(albumAdapterAlbum, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Album album = albumAdapterAlbum.getItem(which);
                mCalback.onResult(album);
            }
        });
        builderSingle.show();

    }

    public Album getAlbumSelect() {
        return mAlbumSelect;
    }

    public void setAlbumSelect(Album albumSelect) {
        mAlbumSelect = albumSelect;
    }

    public class AdapterAlbum extends ArrayAdapter<Album> {
        private List<Album> mAlbums;

        public AdapterAlbum(@NonNull Context context, List<Album> albums) {
            super(context, 0, albums);
            mAlbums = albums;
        }

        @Override
        public int getCount() {
            return mAlbums.size();
        }

        @Nullable
        @Override
        public Album getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Album album = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_album_add, parent, false);

            }
            TextView tvName = (TextView) convertView.findViewById(R.id.tv_namealbum);
            TextView tvHome = (TextView) convertView.findViewById(R.id.text_number_track);
            tvName.setText(album.getName());
            tvHome.setText(album.getNumberSong() + " songs");
            return convertView;
        }
    }
}
