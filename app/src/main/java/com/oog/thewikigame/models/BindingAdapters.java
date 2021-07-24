package com.oog.thewikigame.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.BindingAdapter;

import com.google.android.material.textfield.TextInputLayout;
import com.oog.thewikigame.utilities.LogTag;
import com.oog.thewikigame.utilities.Logger;

import java.util.List;

public class BindingAdapters {

    @BindingAdapter("adapter")
    public static void setAdapter(AutoCompleteTextView view, ArrayAdapter adapter) {
        view.setAdapter(adapter);
    }

    @BindingAdapter("android:text")
    public static void setText(AutoCompleteTextView textView, @StringRes int resId) {
        String resString = textView.getContext().getString(resId);
        textView.setText(resString);
    }

    @BindingAdapter("android:text")
    public static void setText(AutoCompleteTextView textView, String resId) {
        try {
            resId = textView.getContext().getString(Integer.parseInt(resId));
        } catch (Exception ignored) {
        }
        textView.setText(resId);
    }

    @BindingAdapter("error")
    public static void setError(TextInputLayout textInputLayout, String error){
        textInputLayout.setError(error);
    }

}
