package com.example.appshow.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appshow.R;
import com.example.appshow.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {
    /*
    Area : variable
     */
    private ActivityWelcomeBinding binding;

    /*
    Area : function
     */
    private void setWelcomeDelay() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 5000);
    }

    private void setAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_hide_show);
        binding.welcome.setAnimation(animation);
    }

    /*
    Area : override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setWelcomeDelay();
        setAnimation();
    }
}