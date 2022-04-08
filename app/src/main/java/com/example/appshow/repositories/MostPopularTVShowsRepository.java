package com.example.appshow.repositories;

import com.example.appshow.network.ApiClient;
import com.example.appshow.network.ApiService;
import com.example.appshow.response.TVShowResponse;

import retrofit2.Call;

public class MostPopularTVShowsRepository {
    /*
    Area : variable
     */
    private final ApiService apiService;

    /*
    Area : function
     */
    public MostPopularTVShowsRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public Call<TVShowResponse> getMostPopularTVShows(int page) {
        return apiService.getMostPopularTVShows(page);
    }
}
