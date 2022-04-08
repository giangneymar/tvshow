package com.example.appshow.response;

import com.example.appshow.models.TVShow;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TVShowResponse {
    /*
    Area : variable
     */
    @SerializedName("page")
    private int page;

    @SerializedName("pages")
    private int totalPages;

    @SerializedName("tv_shows")
    private ArrayList<TVShow> tvShows;

    /*
    Area : function
     */
    public int getTotalPages() {
        return totalPages;
    }

    public List<TVShow> getTVShows() {
        return tvShows;
    }
}
