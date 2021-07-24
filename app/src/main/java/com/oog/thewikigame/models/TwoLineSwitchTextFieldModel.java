package com.oog.thewikigame.models;

import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
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
    private String data;
    private String error;

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
        editText.setInputType(inputType);
    }


    @BindingConversion
    public static int booleanToVisibility(boolean visible){
        return visible?View.VISIBLE:View.GONE;
    }

    @Bindable
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        notifyPropertyChanged(BR.data);
    }

    @Bindable
    @Nullable
    public String getError() {
        return error;
    }


    public void setError(@Nullable String error) {
        this.error = error;
        notifyPropertyChanged(BR.error);
    }
}
