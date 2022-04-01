package com.example.appshow.view.activity;

import static com.example.appshow.utils.KeyConstants.TV_SHOW;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appshow.R;
import com.example.appshow.databinding.ActivityTvshowDetailsBinding;
import com.example.appshow.databinding.LayoutEpisodesBottomSheetBinding;
import com.example.appshow.models.TVShow;
import com.example.appshow.response.TVShowDetailsResponse;
import com.example.appshow.utils.CheckConstants;
import com.example.appshow.view.adapter.EpisodesAdapter;
import com.example.appshow.view.adapter.ImageSliderAdapter;
import com.example.appshow.viewmodel.TVShowDetailsViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TVShowDetailsActivity extends AppCompatActivity {
    private ActivityTvshowDetailsBinding bindingActivity;
    private TVShowDetailsViewModel viewModel;
    private BottomSheetDialog episodesBottomSheetDialog;
    private LayoutEpisodesBottomSheetBinding bindingBottomSheet;
    private TVShow tvShow;
    private Boolean isTVShowAvailableInWatchList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingActivity = DataBindingUtil.setContentView(this, R.layout.activity_tvshow_details);
        initViewModel();
        getInfoFromTVShow();
        checkTVShowInWatchList();
        getTVShowDetails();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(TVShowDetailsViewModel.class);
    }

    private void getInfoFromTVShow() {
        tvShow = (TVShow) getIntent().getSerializableExtra(TV_SHOW);
    }

    private void checkTVShowInWatchList() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModel.getTVShowWatchList(String.valueOf(tvShow.getId()))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShow -> {
                    isTVShowAvailableInWatchList = true;
                    bindingActivity.imgWatchList.setImageResource(R.drawable.ic_added);
                    compositeDisposable.dispose();
                }));
    }

    private void getTVShowDetails() {
        bindingActivity.setIsLoading(true);
        String tvShowId = String.valueOf(tvShow.getId());
        viewModel.getTVShowDetails(tvShowId).observe(this, response -> {
            bindingActivity.setIsLoading(false);
            if (response.getTvShowDetails() != null) {
                getComponentTVShowDetails(response);
                setVisibleOnActivity();
                onClick(response);
                loadBasicTVShowDetails();
            }
        });
    }

    private void getComponentTVShowDetails(TVShowDetailsResponse response) {
        if (response.getTvShowDetails().getPictures() != null) {
            loadImageSlider(response.getTvShowDetails().getPictures());
        }
        bindingActivity.setTvShowImageUrl(response.getTvShowDetails().getImagePath());
        bindingActivity.setDesc(response.getTvShowDetails().getDescription());
        setLengthDescription();
        bindingActivity.setRating(String.format(Locale.getDefault(), getString(R.string.rating_format), Double.parseDouble(response.getTvShowDetails().getRating())));
        if (response.getTvShowDetails().getGenres() != null) {
            bindingActivity.setGenre(response.getTvShowDetails().getGenres()[0]);
        } else {
            bindingActivity.setGenre(getString(R.string.n_a));
        }
        bindingActivity.setRuntime(response.getTvShowDetails().getRuntime() + getString(R.string.minutes));
    }

    private void loadImageSlider(String[] sliderImages) {
        bindingActivity.sliderViewPager.setOffscreenPageLimit(1);
        bindingActivity.sliderViewPager.setAdapter(new ImageSliderAdapter(sliderImages));
        setupSliderIndicators(sliderImages.length);
        bindingActivity.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });
    }

    private void setupSliderIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive));
            indicators[i].setLayoutParams(layoutParams);
            bindingActivity.layoutSliderIndicators.addView(indicators[i]);
        }
        setCurrentSliderIndicator(0);
    }

    private void setCurrentSliderIndicator(int position) {
        int childCount = bindingActivity.layoutSliderIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) bindingActivity.layoutSliderIndicators.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slider_indicator_inactive));
            }
        }
    }

    private void setLengthDescription() {
        bindingActivity.textReadMore.setOnClickListener(view -> {
            if (bindingActivity.textReadMore.getText().toString().equals(getString(R.string.read_more))) {
                bindingActivity.textDesc.setMaxLines(Integer.MAX_VALUE);
                bindingActivity.textDesc.setEllipsize(null);
                bindingActivity.textReadMore.setText(R.string.read_less);
            } else {
                bindingActivity.textDesc.setMaxLines(4);
                bindingActivity.textDesc.setEllipsize(TextUtils.TruncateAt.END);
                bindingActivity.textReadMore.setText(R.string.read_more);
            }
        });
    }

    private void setVisibleOnActivity() {
        bindingActivity.textReadMore.setVisibility(View.VISIBLE);
        bindingActivity.imgRating.setVisibility(View.VISIBLE);
        bindingActivity.textRating.setVisibility(View.VISIBLE);
        bindingActivity.viewDivider1.setVisibility(View.VISIBLE);
        bindingActivity.viewDivider2.setVisibility(View.VISIBLE);
        bindingActivity.btnWeb.setVisibility(View.VISIBLE);
        bindingActivity.btnEpisode.setVisibility(View.VISIBLE);
        bindingActivity.imgWatchList.setVisibility(View.VISIBLE);
    }

    private void onClick(TVShowDetailsResponse response) {
        onClickBack();
        onClickWeb(response);
        onClickEpisode(response);
        onClickWatchList();
    }

    private void onClickBack() {
        bindingActivity.imgBack.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    private void onClickWeb(TVShowDetailsResponse response) {
        bindingActivity.btnWeb.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(response.getTvShowDetails().getUrl()));
            startActivity(intent);
        });
    }

    private void onClickEpisode(TVShowDetailsResponse response) {
        bindingActivity.btnEpisode.setOnClickListener(view -> {
            if (episodesBottomSheetDialog == null) {
                episodesBottomSheetDialog = new BottomSheetDialog(TVShowDetailsActivity.this);
                bindingBottomSheet = DataBindingUtil.inflate(
                        LayoutInflater.from(TVShowDetailsActivity.this),
                        R.layout.layout_episodes_bottom_sheet,
                        findViewById(R.id.episodeContainer),
                        false
                );
                episodesBottomSheetDialog.setContentView(bindingBottomSheet.getRoot());
                bindingBottomSheet.recyclerviewEpisodes.setAdapter(new EpisodesAdapter(response.getTvShowDetails().getEpisodes()));
                bindingBottomSheet.textTitle.setText(String.format(getString(R.string.episode_format), tvShow.getName()));
                bindingBottomSheet.imgClose.setOnClickListener(view1 -> episodesBottomSheetDialog.dismiss());
            }
            episodesBottomSheetDialog.show();
        });
    }

    private void onClickWatchList() {
        bindingActivity.imgWatchList.setOnClickListener(view -> {
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            if (isTVShowAvailableInWatchList) {
                compositeDisposable.add(viewModel.removeTVShowFromWatchList(tvShow)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            isTVShowAvailableInWatchList = false;
                            CheckConstants.IS_WATCHLIST_UPDATED = true;
                            bindingActivity.imgWatchList.setImageResource(R.drawable.ic_watch_list);
                            setSnackBar(getString(R.string.delete_from_watch_list));
                            compositeDisposable.dispose();
                        }));
            } else {
                compositeDisposable.add(viewModel.addToWatchList(tvShow)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            isTVShowAvailableInWatchList = true;
                            CheckConstants.IS_WATCHLIST_UPDATED = true;
                            bindingActivity.imgWatchList.setImageResource(R.drawable.ic_added);
                            setSnackBar(getString(R.string.add_into_watch_list));
                            compositeDisposable.dispose();
                        }));
            }
        });
    }

    private void setSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(bindingActivity.layoutContainerTVShowDetails, message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.YELLOW);
        TextView textView = sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.BLUE);
        snackbar.show();
    }

    private void loadBasicTVShowDetails() {
        bindingActivity.setTvShowName(tvShow.getName());
        bindingActivity.setNetworkCountry(tvShow.getNetwork() + " - " + tvShow.getCountry());
        bindingActivity.setStatus(tvShow.getStatus());
        bindingActivity.setStartDate(tvShow.getStartDate());
    }
}