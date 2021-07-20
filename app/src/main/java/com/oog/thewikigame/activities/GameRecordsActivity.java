package com.oog.thewikigame.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.oog.thewikigame.R;
import com.oog.thewikigame.databinding.ActivityGameRecordsBinding;

public class GameRecordsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGameRecordsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_game_records);
        binding.cardviewDetails.setOnClickListener(v-> startActivityIntent(GameDetailActivity.class));

        //TODO: add functionality to recycler view
        //TODO: inject gameRecordsModel with start and end articles from database


    }

    private void startActivityIntent(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }
}

