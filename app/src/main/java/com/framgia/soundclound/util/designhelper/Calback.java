package com.framgia.soundclound.util.designhelper;

import com.framgia.soundclound.data.model.Album;

/**
 * Created by Bui Danh Nam on 18/1/2018.
 */

public interface Calback {
    void onResult(Album album);
    void onAddNewAlbum(boolean b);
}
