package com.oog.thewikigame.models;

import android.content.Context;


//TODO: Possibly irrelevant.
public class ListItemTwoLineTextSwitchModel extends TwoLineSwitchModel{

    public ListItemTwoLineTextSwitchModel(String primaryTitle, String secondaryTitle) {
        super(primaryTitle, secondaryTitle);
    }

    public ListItemTwoLineTextSwitchModel(Context context, int primaryTitleRes, int secondaryTitleRes) {
        super(context, primaryTitleRes, secondaryTitleRes);
    }

    @Override
    public void onToggle(boolean checked) {

    }
}
