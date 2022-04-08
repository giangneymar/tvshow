package com.example.appshow.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appshow.R;
import com.example.appshow.databinding.ItemContainerSliderImageBinding;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ItemHolder> {
    /*
    Area : variable
     */
    private String[] sliderImages;
    private LayoutInflater layoutInflater;

    /*
    Area : function
     */
    public ImageSliderAdapter(String[] sliderImages) {
        this.sliderImages = sliderImages;
    }

    /*
    Area : inner class
     */
    public class ItemHolder extends RecyclerView.ViewHolder {
        /*
        Area : variable
         */
        private ItemContainerSliderImageBinding binding;

        /*
        Area : function
         */
        public ItemHolder(ItemContainerSliderImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindSliderImage(String imageUrl) {
            binding.setImageUrl(imageUrl);
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
        ItemContainerSliderImageBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_container_slider_image, parent, false);
        return new ItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.bindSliderImage(sliderImages[position]);
    }

    @Override
    public int getItemCount() {
        if (sliderImages == null) {
            return 0;
        }
        return sliderImages.length;
    }
}
