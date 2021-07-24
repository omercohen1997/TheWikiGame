package com.oog.thewikigame.models;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.databinding.Bindable;

import com.oog.thewikigame.BR;

public class TwoLinePopupModel extends TwoLineModel{

    private ArrayAdapter<String> adapter;

    public TwoLinePopupModel(String primaryTitle, String secondaryTitle) {
        super(primaryTitle, secondaryTitle);
    }

    public TwoLinePopupModel(Context context, int primaryTitleRes, int secondaryTitleRes) {
        super(context, primaryTitleRes, secondaryTitleRes);
    }



    @Bindable
    public ArrayAdapter<String> getAdapter() {
        return adapter;
    }


    public void setAdapter(ArrayAdapter<String> adapter) {
        this.adapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

}


