package com.example.appshow.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appshow.R;
import com.example.appshow.databinding.ItemContainerTvShowBinding;
import com.example.appshow.listeners.WatchListListener;
import com.example.appshow.models.TVShow;

import java.util.List;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.ItemHolder> {
    private final List<TVShow> tvShows;
    private final WatchListListener watchListListener;
    private LayoutInflater layoutInflater;

    public WatchListAdapter(List<TVShow> tvShows, WatchListListener watchListListener) {
        this.tvShows = tvShows;
        this.watchListListener = watchListListener;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private final ItemContainerTvShowBinding binding;

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
        }
    }

    //handle recyclerview
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerTvShowBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_container_tv_show,parent,false);
        return new ItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.bindTvShow(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }
}
