package com.example.appshow.view.activity;

import static com.example.appshow.utils.KeyConstant.TV_SHOW;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.appshow.R;
import com.example.appshow.databinding.ActivityWatchListBinding;
import com.example.appshow.models.TVShow;
import com.example.appshow.utils.CheckConstant;
import com.example.appshow.view.adapter.WatchListAdapter;
import com.example.appshow.viewmodel.WatchListViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListActivity extends AppCompatActivity implements WatchListAdapter.WatchListListener {
    /*
    Area : variable
     */
    private ActivityWatchListBinding binding;
    private WatchListViewModel viewModel;
    private WatchListAdapter watchListAdapter;
    private List<TVShow> watchList;

    /*
    Area : function
     */
    private void initAll() {
        viewModel = new ViewModelProvider(this).get(WatchListViewModel.class);
        watchList = new ArrayList<>();
        watchListAdapter = new WatchListAdapter(watchList, this);
    }

    private void loadWatchList() {
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
                    binding.listWatchList.setAdapter(watchListAdapter);
                    compositeDisposable.dispose();
                }));
    }

    private void onCLick() {
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_watch_list);
        initAll();
        loadWatchList();
        onCLick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CheckConstant.IS_WATCHLIST_UPDATED) {
            loadWatchList();
            CheckConstant.IS_WATCHLIST_UPDATED = false;
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