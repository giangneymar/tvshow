package com.example.appshow.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appshow.database.TVShowsDatabase;
import com.example.appshow.models.TVShow;
import com.example.appshow.network.ApiClient;
import com.example.appshow.network.ApiService;
import com.example.appshow.response.TVShowDetailsResponse;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowDetailsRepository {
    /*
    Area : variable
     */
    private final ApiService apiService;
    private final TVShowsDatabase tvShowsDatabase;

    /*
    Area : function
     */
    public TVShowDetailsRepository(Application application){
        apiService = ApiClient.getRetrofit().create(ApiService.class);
        tvShowsDatabase = TVShowsDatabase.getTvShowsDatabase(application);
    }

    public Call<TVShowDetailsResponse> getTVShowDetails(String tvShowId){
        return apiService.getTVShowDetails(tvShowId);
    }

    public Completable addToWatchList(TVShow tvShow) {
        return tvShowsDatabase.tvShowDAO().addToWatchlist(tvShow);
    }

    public Flowable<TVShow> getTVShowWatchList(String tvShowId) {
        return tvShowsDatabase.tvShowDAO().getTVShowFromWatchList(tvShowId);
    }

    public Completable removeTVShowFromWatchList(TVShow tvShow) {
        return tvShowsDatabase.tvShowDAO().removeFromWatchlist(tvShow);
    }
}
