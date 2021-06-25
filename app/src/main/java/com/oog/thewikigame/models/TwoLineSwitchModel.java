package com.oog.thewikigame.models;

abstract public class TwoLineSwitchModel extends TwoLineModel {
    public boolean checked;
    public TwoLineSwitchModel(String primaryTitle, String secondaryTitle) {
        super(primaryTitle, secondaryTitle);
    }

    abstract public void onToggle(boolean checked);

}
