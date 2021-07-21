package com.oog.thewikigame.models;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.databinding.Bindable;

import com.oog.thewikigame.BR;

import java.util.List;

public class TwoLinePopupModel extends TwoLineModel{

    private List<String> values;

    public TwoLinePopupModel(String primaryTitle, String secondaryTitle) {
        super(primaryTitle, secondaryTitle);
    }

    public TwoLinePopupModel(Context context, int primaryTitleRes, int secondaryTitleRes) {
        super(context, primaryTitleRes, secondaryTitleRes);
    }

    @Bindable
    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
        notifyPropertyChanged(BR.values);
    }
}


