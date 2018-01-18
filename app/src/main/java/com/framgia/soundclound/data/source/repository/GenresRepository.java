package com.framgia.soundclound.data.source.repository;

import android.support.annotation.StringDef;

import com.framgia.soundclound.R;
import com.framgia.soundclound.data.model.Genre;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import static com.framgia.soundclound.data.source.repository.GenresRepository.ServiceName.GENRES_ALL;
import static com.framgia.soundclound.data.source.repository.GenresRepository.ServiceName.GENRES_CLASSIC;
import static com.framgia.soundclound.data.source.repository.GenresRepository.ServiceName.GENRES_COUNTRY;
import static com.framgia.soundclound.data.source.repository.GenresRepository.ServiceName.GENRES_DEEP_HOUSE;
import static com.framgia.soundclound.data.source.repository.GenresRepository.ServiceName.GENRES_EDM;
import static com.framgia.soundclound.data.source.repository.GenresRepository.ServiceName.GENRES_ELECTRONIC;
import static com.framgia.soundclound.data.source.repository.GenresRepository.ServiceName.GENRES_POP;
import static com.framgia.soundclound.data.source.repository.GenresRepository.ServiceName.GENRES_POP_BLAS;
import static com.framgia.soundclound.data.source.repository.GenresRepository.ServiceName.GENRES_ROCK;

/**
 * Created by Bui Danh Nam on 5/1/2018.
 */
public class GenresRepository {
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            GENRES_ALL, GENRES_POP, GENRES_CLASSIC,
            GENRES_POP_BLAS, GENRES_DEEP_HOUSE, GENRES_COUNTRY,
            GENRES_ROCK, GENRES_EDM, GENRES_ELECTRONIC
    })
    public @interface ServiceName {
        String GENRES_ALL = "all-music";
        String GENRES_POP = "pop";
        String GENRES_COUNTRY = "country";
        String GENRES_POP_BLAS = "jazzblues";
        String GENRES_DEEP_HOUSE = "bg_deephouse";
        String GENRES_ROCK = "bg_rock";
        String GENRES_EDM = "danceedm";
        String GENRES_CLASSIC = "classical";
        String GENRES_ELECTRONIC = "electronic";
    }

    public GenresRepository() {
    }

    public static List<Genre> getListGenres() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre(R.string.genres_all, R.drawable.bg_all,
                GENRES_ALL,
                R.drawable.icon_music_all));
        genres.add(new Genre(R.string.genres_pop, R.drawable.bg_pop,
                GENRES_POP,
                R.drawable.icon_music_pop));
        genres.add(new Genre(R.string.genres_country, R.drawable.bg_country,
                GENRES_COUNTRY,
                R.drawable.icon_music_country));
        genres.add(new Genre(R.string.genres_classic, R.drawable.bg_deephouse,
                GENRES_CLASSIC,
                R.drawable.icon_music_classic));
        genres.add(new Genre(R.string.genres_depp_house, R.drawable.bg_deephouse,
                GENRES_DEEP_HOUSE,
                R.drawable.icon_music_deephouse));
        genres.add(new Genre(R.string.genres_popblas, R.drawable.bg_bl,
                GENRES_POP_BLAS,
                R.drawable.icon_music_popblast));
        genres.add(new Genre(R.string.genres_rock, R.drawable.bg_rock,
                GENRES_ROCK,
                R.drawable.icon_music_roock));
        genres.add(new Genre(R.string.genres_edm, R.drawable.bg_edm,
                GENRES_EDM,
                R.drawable.icon_musicedm));
        genres.add(new Genre(R.string.genres_electronic, R.drawable.bg_ele,
                GENRES_ELECTRONIC,
                R.drawable.icon_musicedm));
        return genres;
    }
}
