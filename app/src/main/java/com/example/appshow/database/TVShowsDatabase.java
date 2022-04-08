package com.example.appshow.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.appshow.dao.TVShowDAO;
import com.example.appshow.models.TVShow;

@Database(entities = TVShow.class, version = 1, exportSchema = false)
public abstract class TVShowsDatabase extends RoomDatabase {
    /*
    Area : variable
     */
    private static TVShowsDatabase tvShowsDatabase;
    private static final String DATABASE_NAME = "tvShow";

    /*
    Area : function
     */
    public static synchronized TVShowsDatabase getTvShowsDatabase(Context context) {
        if (tvShowsDatabase == null) {
            tvShowsDatabase = Room.databaseBuilder(
                    context,
                    TVShowsDatabase.class, DATABASE_NAME
            ).build();
        }
        return tvShowsDatabase;
    }

    public abstract TVShowDAO tvShowDAO();
}
