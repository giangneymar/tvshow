package com.example.appshow.view.activity;

import static com.example.appshow.utils.KeyConstants.TV_SHOW;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.appshow.R;
import com.example.appshow.databinding.ActivityWatchListBinding;
import com.example.appshow.listeners.WatchListListener;
import com.example.appshow.models.TVShow;
import com.example.appshow.utils.CheckConstants;
import com.example.appshow.view.adapter.WatchListAdapter;
import com.example.appshow.viewmodel.WatchListViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity implements WatchListListener {
    private ActivityWatchListBinding binding;
    private WatchListViewModel viewModel;
    private WatchListAdapter watchListAdapter;
    private List<TVShow> watchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_watch_list);
        initViewModel();
        loadWatchList();
        onCLick();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(WatchListViewModel.class);
    }

    private void loadWatchList() {
        watchList = new ArrayList<>();
        binding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModel.loadWatchList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShows -> {
                    binding.setIsLoading(false);
                    if (watchList.size() > 0) {
                        watchList.clear();
                    }
                    watchList.addAll(tvShows);
                    watchListAdapter = new WatchListAdapter(watchList, this);
                    binding.recyclerViewWatchList.setAdapter(watchListAdapter);
                    compositeDisposable.dispose();
                }));
    }

    private void onCLick() {
        onCLickBack();
    }

    private void onCLickBack() {
        binding.imgBack.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CheckConstants.IS_WATCHLIST_UPDATED) {
            loadWatchList();
            CheckConstants.IS_WATCHLIST_UPDATED = false;
        }
    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        intent.putExtra(TV_SHOW, tvShow);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void removeTVShowFromWatchList(TVShow tvShow, int position) {
        CompositeDisposable compositeDisposableForDelete = new CompositeDisposable();
        compositeDisposableForDelete.add(viewModel.removeTVShowFromWatchList(tvShow)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    watchList.remove(position);
                    watchListAdapter.notifyItemRemoved(position);
                    watchListAdapter.notifyItemChanged(position, watchListAdapter.getItemCount());
                    compositeDisposableForDelete.dispose();
                }));
    }
}