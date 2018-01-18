package com.framgia.soundclound.screen;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Sony on 1/16/2018.
 */

public abstract class EndSrcollListenner extends RecyclerView.OnScrollListener {
    private int visibleThreshold = 10;
    private int offSet = 0;
    private int previousTotalItemCount = 0;
    private boolean loading = true;
    private int startingOffSet = 0;

    public EndSrcollListenner() {
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int lastVisibleItemPosition = 0;
        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
        int totalItemCount = layoutManager.getItemCount();
        
        lastVisibleItemPosition = ((LinearLayoutManager)
                layoutManager).findLastVisibleItemPosition();

        if (totalItemCount < previousTotalItemCount) {
            this.offSet = this.startingOffSet;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            offSet = offSet + 20;
            onLoadMore(offSet, totalItemCount);
            loading = true;
        }
    }

    public void resetState() {
        this.offSet = this.startingOffSet;
        this.previousTotalItemCount = 0;
        this.loading = true;
    }

    public abstract void onLoadMore(int offSet, int totalItemsCount);

}
