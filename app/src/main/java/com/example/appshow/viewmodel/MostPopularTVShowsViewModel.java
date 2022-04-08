package com.example.appshow.viewmodel;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appshow.repositories.MostPopularTVShowsRepository;
import com.example.appshow.response.TVShowResponse;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTVShowsViewModel extends ViewModel {
    /*
    Area : variable
     */
    private final MostPopularTVShowsRepository mostPopularTVShowsRepository;

    /*
    Area : function
     */
    public MostPopularTVShowsViewModel() {
        mostPopularTVShowsRepository = new MostPopularTVShowsRepository();
    }

    public LiveData<TVShowResponse> getMostPopularTVShows(int page) {
        MutableLiveData<TVShowResponse> data = new MutableLiveData<>();
        mostPopularTVShowsRepository.getMostPopularTVShows(page).enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(Call<TVShowResponse> call, Response<TVShowResponse> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<TVShowResponse> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }
}
