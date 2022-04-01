package com.example.appshow.listeners;

import com.example.appshow.models.TVShow;

public interface WatchListListener {
    void onTVShowClicked(TVShow tvShow);

    void removeTVShowFromWatchList(TVShow tvShow, int position);
}
