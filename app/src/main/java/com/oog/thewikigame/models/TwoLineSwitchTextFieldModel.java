package com.oog.thewikigame.models;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.StringRes;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;

import com.google.android.material.textfield.TextInputEditText;
import com.oog.thewikigame.BR;
import com.oog.thewikigame.utilities.LogTag;
import com.oog.thewikigame.utilities.Logger;

public class TwoLineSwitchTextFieldModel extends TwoLineSwitchModel {

    private int inputType;
    private CharSequence hint;

    public TwoLineSwitchTextFieldModel(String primaryTitle, String secondaryTitle) {
        super(primaryTitle, secondaryTitle);
    }

    public TwoLineSwitchTextFieldModel(Context context, @StringRes int primaryTitleRes, @StringRes int secondaryTitleRes) {
        super(context, primaryTitleRes, secondaryTitleRes);
    }

    @Bindable
    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
        notifyPropertyChanged(BR.inputType);
    }


    @Bindable
    public CharSequence getHint() {
        return hint;
    }

    public void setHint(CharSequence hint) {
        this.hint = hint;
        notifyPropertyChanged(BR.hint);
    }

    @BindingAdapter("inputType")
    public static void setInputType(TextInputEditText editText, int inputType) {
        Logger.log(LogTag.TEMP_DEBUG, "Trying to set input filter:", inputType);
        editText.setInputType(inputType);
    }


    @BindingConversion
    public static int booleanToVisibility(boolean visible){
        return visible?View.VISIBLE:View.GONE;
    }

}
