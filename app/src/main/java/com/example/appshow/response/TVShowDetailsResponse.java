package com.example.appshow.response;

import com.example.appshow.models.TVShowDetails;
import com.google.gson.annotations.SerializedName;

public class TVShowDetailsResponse {
    /*
    Area : variable
     */
    @SerializedName("tvShow")
    private TVShowDetails tvShowDetails;

    /*
    Area : function
     */
    public TVShowDetails getTvShowDetails() {
        return tvShowDetails;
    }
}
