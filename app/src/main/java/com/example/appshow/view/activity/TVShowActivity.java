package com.example.appshow.view.activity;

import static com.example.appshow.utils.KeyConstant.TV_SHOW;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appshow.R;
import com.example.appshow.databinding.ActivityTvshowBinding;
import com.example.appshow.models.TVShow;
import com.example.appshow.view.adapter.TVShowAdapter;
import com.example.appshow.viewmodel.MostPopularTVShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class TVShowActivity extends AppCompatActivity implements TVShowAdapter.TVShowListener {
    /*
    Area : variable
     */
    private ActivityTvshowBinding binding;
    private MostPopularTVShowsViewModel viewModel;
    private List<TVShow> tvShows;
    private TVShowAdapter tvShowAdapter;
    private int currentPage = 1;
    private int totalPages;

    /*
    Area : function
     */
    private void initAll() {
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
        tvShows = new ArrayList<>();
        tvShowAdapter = new TVShowAdapter(tvShows, this);
    }

    private void setRecyclerViewTVShow() {
        binding.listTvShow.setHasFixedSize(true);
        binding.listTvShow.setAdapter(tvShowAdapter);
        binding.listTvShow.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.listTvShow.canScrollVertically(1)) {
                    if (currentPage <= totalPages) {
                        currentPage++;
                        getMostPopularTVShows();
                    }
                }
            }
        });
        getMostPopularTVShows();
    }

    private void getMostPopularTVShows() {
        toggleLoading();
        viewModel.getMostPopularTVShows(currentPage).observe(this, tvShowResponse -> {
            toggleLoading();
            if (tvShowResponse != null) {
                totalPages = tvShowResponse.getTotalPages();
                if (tvShowResponse.getTVShows() != null) {
                    int oldCount = tvShows.size();
                    tvShows.addAll(tvShowResponse.getTVShows());
                    tvShowAdapter.notifyItemRangeInserted(oldCount, tvShows.size());
                }
            }
        });
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            binding.setIsLoading(!binding.getIsLoading());
        } else {
            binding.setIsLoadingMore(!binding.getIsLoadingMore());
        }
    }

    private void onClick() {
        binding.imgBack.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    /*
    Area : override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTvshowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initAll();
        setRecyclerViewTVShow();
        onClick();
    }

    @Override
    public void onTVShowClick(TVShow tvShow) {
        Intent intent = new Intent(TVShowActivity.this, TVShowDetailsActivity.class);
        intent.putExtra(TV_SHOW, tvShow);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}