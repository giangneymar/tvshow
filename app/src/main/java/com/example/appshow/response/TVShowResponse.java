package com.example.appshow.response;

import com.example.appshow.models.TVShow;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TVShowResponse {
    @SerializedName("page")
    private int page;

    @SerializedName("pages")
    private int totalPages;

    @SerializedName("tv_shows")
    private ArrayList<TVShow> tvShows;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<TVShow> getTVShows() {
        return tvShows;
    }
}
