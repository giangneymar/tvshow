package com.example.appshow.repositories;

import com.example.appshow.network.ApiClient;
import com.example.appshow.network.ApiService;
import com.example.appshow.response.TVShowResponse;

import retrofit2.Call;

public class SearchTVShowRepository {
    /*
    Area : variable
     */
    private final ApiService apiService;

    /*
    Area : function
     */
    public SearchTVShowRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public Call<TVShowResponse> searchTVShow(String query, int page) {
        return apiService.searchTVShow(query, page);
    }
}
