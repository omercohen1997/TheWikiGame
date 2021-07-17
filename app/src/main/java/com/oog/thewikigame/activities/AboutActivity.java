package com.oog.thewikigame.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oog.thewikigame.R;
import com.oog.thewikigame.databinding.ActivityAboutBinding;
import com.oog.thewikigame.databinding.ActivityGameDetailBinding;

public class AboutActivity extends AppCompatActivity {

    LinearLayout expandableOmerCohen, expandableOmerGez, expandableGuy;
    TextView textViewOmerCohen, textViewOmerGez, textViewGuy;
    CardView cardViewOmerCohen, cardViewOmerGez, cardViewGuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAboutBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_about);
        binding.omerGez.setOnClickListener((v)->{
            if (expandableOmerGez.getVisibility() == View.GONE){
                TransitionManager.beginDelayedTransition(cardViewOmerGez, new AutoTransition());
                expandableOmerGez.setVisibility(View.VISIBLE);
            }
            else{
                TransitionManager.beginDelayedTransition(cardViewOmerGez, new AutoTransition());
                expandableOmerGez.setVisibility(View.GONE);
            }
        });
        expandableOmerGez = findViewById(R.id.expandable_view_for_gez);
        textViewOmerGez = findViewById(R.id.omerGez);
        cardViewOmerGez = findViewById(R.id.cardview_expandable_for_gez);

        binding.guy.setOnClickListener((v)->{
            if (expandableGuy.getVisibility() == View.GONE){
                TransitionManager.beginDelayedTransition(cardViewGuy, new AutoTransition());
                expandableGuy.setVisibility(View.VISIBLE);
            }
            else{
                TransitionManager.beginDelayedTransition(cardViewGuy, new AutoTransition());
                expandableGuy.setVisibility(View.GONE);
            }
        });
        expandableGuy = findViewById(R.id.expandable_view_for_guy);
        textViewGuy = findViewById(R.id.guy);
        cardViewGuy = findViewById(R.id.cardview_expandable_for_guy);

        binding.omerCohen.setOnClickListener((v)->{
            if (expandableOmerCohen.getVisibility() == View.GONE){
                TransitionManager.beginDelayedTransition(cardViewOmerCohen, new AutoTransition());
                expandableOmerCohen.setVisibility(View.VISIBLE);
            }
            else{
                TransitionManager.beginDelayedTransition(cardViewOmerCohen, new AutoTransition());
                expandableOmerCohen.setVisibility(View.GONE);
            }
        });
        expandableOmerCohen = findViewById(R.id.expandable_view_for_cohen);
        textViewOmerCohen = findViewById(R.id.omerCohen);
        cardViewOmerCohen = findViewById(R.id.cardview_expandable_for_cohen);


    }
}