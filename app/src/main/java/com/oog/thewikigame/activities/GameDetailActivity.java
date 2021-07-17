package com.oog.thewikigame.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.oog.thewikigame.R;
import com.oog.thewikigame.databinding.ActivityGameDetailBinding;
import com.oog.thewikigame.models.GameDetailModel;

public class GameDetailActivity extends AppCompatActivity {

    private GameDetailModel gameDetailModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGameDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_game_detail);
        gameDetailModel = new GameDetailModel("0",
                "111",
                "22",
                "02",
                "1",
                "3",
                "t",
                "y");

        binding.setGameDetailModel(gameDetailModel);

        //TODO: build gameDetailModel using the database

    }

}