package com.example.appshow.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TVShowDetails {
    /*
    Area : variable
     */
    @SerializedName("url")
    private String url;

    @SerializedName("description")
    private String description;

    @SerializedName("runtime")
    private String runtime;

    @SerializedName("image_path")
    private String imagePath;

    @SerializedName("rating")
    private String rating;

    @SerializedName("genres")
    private String[] genres;

    @SerializedName("pictures")
    private String[] pictures;

    @SerializedName("episodes")
    private ArrayList<Episode> episodes;

    /*
    Area : function
     */
    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getRating() {
        return rating;
    }

    public String[] getGenres() {
        return genres;
    }

    public String[] getPictures() {
        return pictures;
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }
}
