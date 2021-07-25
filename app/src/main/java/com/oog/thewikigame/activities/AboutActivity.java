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
import com.oog.thewikigame.models.AuthorModel;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAboutBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_about);


        binding.aboutToolbarId.setNavigationOnClickListener(v -> finish());

        binding.setAuthor1Model(new AuthorModel("Omer Gez", "omergez72@gmail.com", "https://www.linkedin.com/in/omer-gez-004b931b0/"));
        binding.setAuthor2Model(new AuthorModel("Guy Tsitsiashvili", "guygool4@gmail.com", "https://www.linkedin.com/in/guygool5/"));
        binding.setAuthor3Model(new AuthorModel("Omer Cohen", "omerc1997@gmail.com", "https://www.linkedin.com/in/omer-cohen-400583201/"));
    }
}