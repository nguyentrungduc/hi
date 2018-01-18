package com.framgia.soundclound.screen.detailalbum;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.framgia.soundclound.R;
import com.framgia.soundclound.databinding.ActivityAlbumDetailBinding;
import com.framgia.soundclound.util.Constant;

/**
 * Created by Bui Danh Nam on 11/1/2018.
 */

public class DetailAlbumActivity extends AppCompatActivity {
    public static final int REQUEST_UPDATE_DATA = 10001;
    public DetailAlbumModelView mDetailAlbumModelView;

    public static Intent getInstance(Context context, int idAlbum) {
        Intent intent = new Intent(context, DetailAlbumActivity.class);
        intent.putExtra(Constant.EXTRA_ID_ALBUM, idAlbum);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActivityAlbumDetailBinding activityAlbumDetailBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_album_detail);
        Intent intent = getIntent();
        int idAlbum = intent.getExtras().getInt(
                Constant.EXTRA_ID_ALBUM,
                Constant.VALUE_ID_ALBUM_NULL);
        mDetailAlbumModelView = new DetailAlbumModelView(this, idAlbum);
        activityAlbumDetailBinding.setViewModelAlbum(mDetailAlbumModelView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_UPDATE_DATA) {
            mDetailAlbumModelView.onUpdate();
        }
    }
}
