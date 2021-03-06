package com.example.appshow.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appshow.R;
import com.example.appshow.databinding.ItemContainerEpisodeBinding;
import com.example.appshow.models.Episode;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.ItemHolder> {
    /*
    Area : variable
     */
    private List<Episode> episodes;
    private LayoutInflater layoutInflater;

    /*
    Area : function
     */
    public EpisodesAdapter(List<Episode> episodes) {
        this.episodes = episodes;
    }

    /*
    Area : inner class
     */
    public class ItemHolder extends RecyclerView.ViewHolder {
        /*
        Area : variable
         */
        private ItemContainerEpisodeBinding binding;

        /*
        Area : function
         */
        public ItemHolder(ItemContainerEpisodeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindEpisode(Episode episode) {
            String title = "S";
            String season = episode.getSeason();
            if (season.length() == 1) {
                season = "0".concat(season);
            }
            String episodeNumber = episode.getEpisode();
            if (episodeNumber.length() == 1) {
                episodeNumber = "0".concat(episodeNumber);
            }
            episodeNumber = "E".concat(episodeNumber);
            title = title.concat(season).concat(episodeNumber);
            binding.setTitle(title);
            binding.setName(episode.getName());
            binding.setAirDate(episode.getAirDate());
        }
    }

    /*
    Area ; override
     */
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerEpisodeBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_container_episode, parent, false);
        return new ItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.bindEpisode(episodes.get(position));
    }

    @Override
    public int getItemCount() {
        if (episodes == null) {
            return 0;
        }
        return episodes.size();
    }
}
