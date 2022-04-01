package com.example.appshow.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.appshow.R;
import com.example.appshow.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {
    private ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setWelcomeDelay();
        setAnimation();
    }

    private void setWelcomeDelay() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 5000);
    }

    private void setAnimation() {
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_hide_show);
        binding.textWelcome.setAnimation(animation);
    }
}