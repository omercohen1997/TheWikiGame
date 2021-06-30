package com.oog.thewikigame.models;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.oog.thewikigame.BR;

abstract public class TwoLineIconButtonModel extends TwoLineModel  {



    @Bindable
    private Drawable icon;


    public TwoLineIconButtonModel(String primaryTitle, String secondaryTitle,Drawable icon) {
        super(primaryTitle, secondaryTitle);
        setIcon(icon);
    }

    public TwoLineIconButtonModel(Context context, @StringRes int primaryTitleRes, @StringRes int secondaryTitleRes, @DrawableRes int iconRes){
        super(context,primaryTitleRes,secondaryTitleRes);
        setIcon(ResourcesCompat.getDrawable(context.getResources(),iconRes,null));
    }
    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
        notifyPropertyChanged(BR.icon);
    }

    abstract public void onClickIcon();
}
