package com.example.appshow.view.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appshow.R;
import com.example.appshow.databinding.ItemContainerTvShowBinding;
import com.example.appshow.models.TVShow;

import java.util.List;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.ItemHolder> {
    /*
    Area : variable
     */
    private List<TVShow> tvShows;
    private WatchListListener watchListListener;
    private LayoutInflater layoutInflater;

    /*
    Area : function
     */
    public WatchListAdapter(List<TVShow> tvShows, WatchListListener watchListListener) {
        this.tvShows = tvShows;
        this.watchListListener = watchListListener;
    }

    /*
    Area : inner class
     */
    public class ItemHolder extends RecyclerView.ViewHolder {
        /*
        Area : variable
         */
        private final ItemContainerTvShowBinding binding;

        /*
        Area : function
         */
        public ItemHolder(ItemContainerTvShowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTvShow(TVShow tvShow) {
            binding.setTvShow(tvShow);
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(view -> watchListListener.onTVShowClicked(tvShow));
            binding.imgDelete.setOnClickListener(view -> watchListListener.removeTVShowFromWatchList(tvShow, getAdapterPosition()));
            binding.imgDelete.setVisibility(View.VISIBLE);
            binding.containerTvShow.setCardBackgroundColor(Color.rgb(229, 204, 255));
        }
    }

    /*
    Area : override
     */
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerTvShowBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_container_tv_show, parent, false);
        return new ItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.bindTvShow(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        if (tvShows == null) {
            return 0;
        }
        return tvShows.size();
    }

    /*
    Area : interface
     */
    public interface WatchListListener {
        void onTVShowClicked(TVShow tvShow);

        void removeTVShowFromWatchList(TVShow tvShow, int position);
    }
}
