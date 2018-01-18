package com.framgia.soundclound.screen.detailgenre;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.framgia.soundclound.R;
import com.framgia.soundclound.data.model.Genre;
import com.framgia.soundclound.databinding.ActivityGenreDetailBinding;
import com.framgia.soundclound.util.Constant;

/**
 * Created by Sony on 1/5/2018.
 */

public class GenreDetailActivity extends AppCompatActivity {

    public static Intent getInstance(Context context, Genre genre) {
        Intent intent = new Intent(context, GenreDetailActivity.class);
        intent.putExtra(Constant.EXTRA_GENRE, genre);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_detail);
        ActivityGenreDetailBinding activityGenreDetailBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_genre_detail);
        GenreDetailViewModel mViewModel = new GenreDetailViewModel(this,
                (Genre) getIntent().getSerializableExtra(Constant.EXTRA_GENRE));
        activityGenreDetailBinding.setViewModel(mViewModel);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
