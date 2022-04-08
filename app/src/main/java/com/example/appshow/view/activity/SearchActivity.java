package com.example.appshow.view.activity;

import static com.example.appshow.utils.KeyConstant.TV_SHOW;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appshow.R;
import com.example.appshow.databinding.ActivitySearchBinding;
import com.example.appshow.models.TVShow;
import com.example.appshow.view.adapter.TVShowAdapter;
import com.example.appshow.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements TVShowAdapter.TVShowListener {
    /*
    Area : variable
     */
    private ActivitySearchBinding binding;
    private SearchViewModel viewModel;
    private List<TVShow> tvShows;
    private TVShowAdapter tvShowAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;
    private Timer timer;

    /*
    Area : function
     */
    private void initAll() {
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        tvShows = new ArrayList<>();
        tvShowAdapter = new TVShowAdapter(tvShows, this);
    }

    private void setRecyclerViewSearchTVShow() {
        binding.listTvShowSearch.setHasFixedSize(true);
        binding.listTvShowSearch.setAdapter(tvShowAdapter);
        binding.listTvShowSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.listTvShowSearch.canScrollVertically(1)) {
                    if (!binding.inputSearch.getText().toString().isEmpty()) {
                        if (currentPage < totalAvailablePages) {
                            currentPage++;
                            searchTVShow(binding.inputSearch.getText().toString());
                        }
                    }
                }
            }
        });
        binding.inputSearch.requestFocus();
    }

    private void searchTVShow(String query) {
        toggleLoading();
        viewModel.searchTVShow(query, currentPage).observe(this, tvShowResponse -> {
            toggleLoading();
            if (tvShowResponse != null) {
                totalAvailablePages = tvShowResponse.getTotalPages();
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
        binding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().isEmpty()) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                currentPage = 1;
                                totalAvailablePages = 1;
                                searchTVShow(editable.toString());
                            });
                        }
                    }, 800);
                } else {
                    tvShows.clear();
                    tvShowAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /*
    Area : override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initAll();
        setRecyclerViewSearchTVShow();
        onClick();
    }

    @Override
    public void onTVShowClick(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        intent.putExtra(TV_SHOW, tvShow);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}