package com.oog.thewikigame.models;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;


public class IconButtonModel extends BaseObservable {
    private Drawable icon;
    private View.OnClickListener onClickListener;

    public IconButtonModel(Drawable icon, @Nullable View.OnClickListener onClickListener) {
        setIcon(icon);
        setOnClickListener(onClickListener);
    }

    public IconButtonModel(Context context, @DrawableRes int iconRes, @Nullable View.OnClickListener onClickListener) {
        Drawable fetchedIcon = ResourcesCompat.getDrawable(context.getResources(), iconRes, null);
        setIcon(fetchedIcon);
        setOnClickListener(onClickListener);
    }

    @Bindable
    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
        notifyPropertyChanged(BR.icon);
    }

    @Bindable
    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        notifyPropertyChanged(BR.onClickListener);
    }
}
