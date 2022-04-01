package com.example.appshow.view.activity;

import static com.example.appshow.utils.KeyConstants.TV_SHOW;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appshow.R;
import com.example.appshow.databinding.ActivityTvshowBinding;
import com.example.appshow.models.TVShow;
import com.example.appshow.view.adapter.TVShowAdapter;
import com.example.appshow.viewmodel.MostPopularTVShowsViewModel;

import java.util.ArrayList;

public class TVShowActivity extends AppCompatActivity implements TVShowAdapter.TVShowListener {
    private ActivityTvshowBinding binding;
    private MostPopularTVShowsViewModel viewModel;
    private ArrayList<TVShow> tvShows;
    private TVShowAdapter tvShowAdapter;
    private int currentPage = 1;
    private int totalPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tvshow);
        initViewModel();
        initRecyclerViewTVShow();
        onClick();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
    }

    private void initRecyclerViewTVShow() {
        binding.recyclerViewTVShow.setHasFixedSize(true);
        tvShows = new ArrayList<>();
        tvShowAdapter = new TVShowAdapter(tvShows, this);
        binding.recyclerViewTVShow.setAdapter(tvShowAdapter);
        binding.recyclerViewTVShow.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.recyclerViewTVShow.canScrollVertically(1)) {
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
        viewModel.getMostPopularTVShows(currentPage).observe(this, response -> {
            toggleLoading();
            if (response != null) {
                totalPages = response.getTotalPages();
                if (response.getTVShows() != null) {
                    int oldCount = tvShows.size();
                    tvShows.addAll(response.getTVShows());
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
        onClickBack();
    }

    private void onClickBack() {
        binding.imgBack.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    @Override
    public void onTVShowClick(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        intent.putExtra(TV_SHOW, tvShow);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}