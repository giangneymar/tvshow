package com.example.appshow.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appshow.R;
import com.example.appshow.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    /*
    Area : variable
     */
    private ActivityMainBinding binding;

    /*
    Area : function
     */
    private void onClick() {
        binding.imgTvShow.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, TVShowActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
        binding.imgWatchList.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, WatchListActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
        binding.imgSearch.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
        binding.imgDev.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.facebook.com/giang.chelsea.10/"));
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    /*
    Area : override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        onClick();
    }
}