package com.example.appshow.repositories;

import android.app.Application;

import com.example.appshow.database.TVShowsDatabase;
import com.example.appshow.models.TVShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class WatchListRepository {
    /*
    Area : variable
     */
    private final TVShowsDatabase tvShowsDatabase;

    /*
    Area : function
     */
    public WatchListRepository(Application application) {
        tvShowsDatabase = TVShowsDatabase.getTvShowsDatabase(application);
    }

    public Flowable<List<TVShow>> loadWatchList() {
        return tvShowsDatabase.tvShowDAO().getWatchlist();
    }

    public Completable removeTVShowFromWatchList(TVShow tvShow) {
        return tvShowsDatabase.tvShowDAO().removeFromWatchlist(tvShow);
    }
}
