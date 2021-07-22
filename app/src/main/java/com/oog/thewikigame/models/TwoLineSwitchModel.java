package com.oog.thewikigame.models;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.Bindable;

import com.oog.thewikigame.BR;

public class TwoLineSwitchModel extends TwoLineModel {
    private boolean checked;
    private OnToggleListener onToggleListener;

    public interface OnToggleListener {
        void onToggle(boolean checked);
    }

    public TwoLineSwitchModel(String primaryTitle, String secondaryTitle) {
        super(primaryTitle, secondaryTitle);
    }

    public TwoLineSwitchModel(Context context, @StringRes int primaryTitleRes, @StringRes int secondaryTitleRes) {
        super(context, primaryTitleRes, secondaryTitleRes);
    }

    @Bindable
    public boolean getChecked() {
        return checked;
    }

    public void setOnToggleListener(@Nullable OnToggleListener onToggleListener) {
        this.onToggleListener = onToggleListener;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        notifyPropertyChanged(BR.checked);
        if (onToggleListener != null)
            onToggleListener.onToggle(this.checked);
    }

}
