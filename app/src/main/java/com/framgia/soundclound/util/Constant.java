package com.framgia.soundclound.util;


import android.Manifest;

/**
 * Created by Sony on 1/8/2018.
 */
public class Constant {

    public static final String BASE_URL = "https://api-v2.soundcloud.com/";
    public static final String PARA = "charts?kind=top&genre=soundcloud%3Agenres%3A";
    public static final String CLIENT_ID = "client_id";
    public static final String OFF_SET = "offset";
    public static final String LIMIT = "limit";
    public static final String ERROR_NULL = "null_data";
    public static final String[] PERMISSON =
            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    public static final String EXTRA_GENRE = "com.framgia.soundcloud.EXTRA_GENRE_NAME";
    public static final String ARGUMENT_TRACK = "com.framgia.soundcloud.ARGUMENT_TRACK";
    public static final String ARGUMENT_ID_ALBUM = "com.framgia.soundcloud.ARGUMENT_ID_ALBUM";
    public static final String EXTRA_ID_ALBUM = "com.framgia.soundcloud.EXTRA_ID_ALBUM";
    public static final int VALUE_ID_ALBUM_NULL = -1;

    public static final int LIMIT_DEFAULT = 20;
    public static final int OFF_SET_DEFAULT = 0;

    public static final String LINK_DEFAULT =
            "https://zmp3-static.zadn.vn/skins/zmp3-v5.1/images/logo200.png";
    public static final String ERROR_TEXT = "error";

    public static final int NUM_COLUMN_GRID = 3;
    public static final int NUM_ROW_GIRD = 3;

    public static final String TABLE_ALBUM = "album";
    public static final String DATABASE_NAME = "DB_SOUNDCLOUND";
    public static final String TRACKS_FAVORITE = "favorite_defaul";
    public static final int REQUEST_READ_STORAGE = 100;

    public static final String PARCE_LIST_TRACK = "com.framgia.soundcloud.PARCE_TRACK";
    public static final String EXTRA_TRACK = "com.frangia.soundcloud.EXTRA_TRACK";
    public static final int INDEX_DEFAULTE = 1;
    public static final String FILE_EXTENTION = ".mp3";

    public static final int ID_FOREGROUND_SERVICE = 101;
    public static final String ACTION_MAIN = "com.framgia.soundclound.action.main";
    public static final String ACTION_PREV = "com.framgia.soundclound.action.action.prev";
    public static final String ACTION_PLAY = "com.framgia.soundclound.action.action.play";
    public static final String ACTION_NEXT = "com.framgia.soundclound.action.action.next";
    public static final String ACTION_DISMIS = "com.framgia.soundclound.action.action.dismis";
    public static final String ACTION_NOTIFI_CHANGE =
            "com.framgia.soundclound.action.action.change";
    public static final String EXTRA_OPENT_ADDTRACT = "com.framgia.soundcloud.EXTRA_OPEN_ADD_TRACK";
}
