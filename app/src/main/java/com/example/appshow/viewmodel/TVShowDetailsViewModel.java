package com.example.appshow.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appshow.database.TVShowsDatabase;
import com.example.appshow.models.TVShow;
import com.example.appshow.repositories.TVShowDetailsRepository;
import com.example.appshow.response.TVShowDetailsResponse;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowDetailsViewModel extends AndroidViewModel {
    /*
    Area : variable
     */
    private final TVShowDetailsRepository tvShowDetailsRepository;

    /*
    Area : function
     */
    public TVShowDetailsViewModel(@NonNull Application application) {
        super(application);
        tvShowDetailsRepository = new TVShowDetailsRepository(application);
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(String tvShowId) {
        MutableLiveData<TVShowDetailsResponse> data = new MutableLiveData<>();
        tvShowDetailsRepository.getTVShowDetails(tvShowId).enqueue(new Callback<TVShowDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowDetailsResponse> call, @NonNull Response<TVShowDetailsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVShowDetailsResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public Completable addToWatchList(TVShow tvShow) {
        return tvShowDetailsRepository.addToWatchList(tvShow);
    }

    public Flowable<TVShow> getTVShowWatchList(String tvShowId) {
        return tvShowDetailsRepository.getTVShowWatchList(tvShowId);
    }

    public Completable removeTVShowFromWatchList(TVShow tvShow) {
        return tvShowDetailsRepository.removeTVShowFromWatchList(tvShow);
    }
}
