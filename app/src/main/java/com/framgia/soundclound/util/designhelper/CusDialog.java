package com.framgia.soundclound.util.designhelper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.framgia.soundclound.R;
import com.framgia.soundclound.data.model.Album;

import java.util.List;

/**
 * Created by Bui Danh Nam on 18/1/2018.
 */

public class CusDialog {
    private Calback mCalback;

    public void setCalback(Calback calback) {
        mCalback = calback;
    }

    public void showDialog(final Context context, List<Album> albums) {
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);//,android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        builderSingle.setIcon(R.drawable.icon_music_all);
        builderSingle.setTitle("Select Album");
        final AdapterAlbum albumAdapterAlbum = new AdapterAlbum(context, albums);
        LayoutInflater li = LayoutInflater.from(context);
        View view = li.inflate(R.layout.dialog_add_track_album, null);
        View view1 = li.inflate(R.layout.item_heard_add_track_album, null);
        ListView lv2 = (ListView) view.findViewById(R.id.lv_album_add_track);
        lv2.addHeaderView(view1);
        lv2.setAdapter(albumAdapterAlbum);

        builderSingle.setView(view);
        final AlertDialog ad = builderSingle.show();
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mCalback.onAddNewAlbum(true);
                } else {
                    position -= position;
                    Album album = albumAdapterAlbum.getItem(position);
                    mCalback.onResult(album);
                }
                ad.cancel();
            }
        });
        builderSingle.setNeutralButton(R.string.action_cancel,null);


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
            ViewHolder viewHolder;
            Album album = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_album_add, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.tvHome = convertView.findViewById(R.id.tv_namealbum);
                viewHolder.tvName = convertView.findViewById(R.id.text_number_track);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvName.setText(album.getName());
            viewHolder.tvHome.setText(album.getNumberSong() + " songs");
            return convertView;
        }
    }

    public class ViewHolder {
        TextView tvName;
        TextView tvHome;
    }
}
