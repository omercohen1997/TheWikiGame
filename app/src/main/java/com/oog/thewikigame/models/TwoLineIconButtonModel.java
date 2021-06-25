package com.oog.thewikigame.models;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;

import androidx.annotation.DrawableRes;
import androidx.databinding.BindingAdapter;

abstract public class TwoLineIconButtonModel extends TwoLineModel{
    public final Drawable icon;
    public TwoLineIconButtonModel(String primaryTitle, String secondaryTitle,Drawable icon) {
        super(primaryTitle, secondaryTitle);
        this.icon = icon;
    }

    abstract public void onClickIcon();
}
