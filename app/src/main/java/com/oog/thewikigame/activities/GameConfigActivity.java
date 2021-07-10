package com.oog.thewikigame.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oog.thewikigame.R;
import com.oog.thewikigame.databinding.ActivityGameConfigBinding;
import com.oog.thewikigame.models.IconButtonModel;
import com.oog.thewikigame.models.TwoLineSwitchModel;

import java.util.concurrent.CompletableFuture;

public class GameConfigActivity extends AppCompatActivity {

    LinearLayout expandableView;
    TextView textView;
    CardView cardView;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActivityGameConfigBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_game_config);
        binding.showconfig.setOnClickListener((v)->{
            if (expandableView.getVisibility() == View.GONE){
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                expandableView.setVisibility(View.VISIBLE);
            }
            else{
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                expandableView.setVisibility(View.GONE);
            }
        });

        expandableView = findViewById(R.id.expandable_view);
        textView = findViewById(R.id.showconfig);
        cardView = findViewById(R.id.cardview_expandable);


        binding.setStartArticleString("Son Goku");
        binding.setStartArticleIconButtonModel(new IconButtonModel(this, R.drawable.ic_baseline_refresh_24, v -> {

            binding.setStartArticleString(this.getString(R.string.text_loading));
            //TODO: Add web view integration.
            //TODO: delete this simulation.
            CompletableFuture.runAsync(() -> {
                binding.setStartArticleString(this.getString(R.string.text_loading));
                try {
                    Thread.sleep(3000);
                    binding.setStartArticleString("Makita");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }));

        binding.setTimeLimitTwoLineSwitchModel(new TwoLineSwitchModel("Time Limit", "This will set a time limit to finish the game.") {
            @Override
            public void onToggle(boolean checked) {
                //TODO: Add time limit functionality.
            }
        });
    }
}