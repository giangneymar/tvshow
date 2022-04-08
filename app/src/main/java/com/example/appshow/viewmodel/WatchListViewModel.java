package com.example.appshow.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.appshow.database.TVShowsDatabase;
import com.example.appshow.models.TVShow;
import com.example.appshow.repositories.TVShowDetailsRepository;
import com.example.appshow.repositories.WatchListRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class WatchListViewModel extends AndroidViewModel {
    /*
    Area : variable
     */
    private final WatchListRepository watchListRepository;

    /*
    Area : function
     */
    public WatchListViewModel(@NonNull Application application) {
        super(application);
        watchListRepository = new WatchListRepository(application);
    }

    public Flowable<List<TVShow>> loadWatchList() {
        return watchListRepository.loadWatchList();
    }

    public Completable removeTVShowFromWatchList(TVShow tvShow) {
        return watchListRepository.removeTVShowFromWatchList(tvShow);
    }
}
