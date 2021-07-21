package com.oog.thewikigame.models;

import android.annotation.SuppressLint;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.databinding.BindingAdapter;

import com.oog.thewikigame.R;
import com.oog.thewikigame.utilities.LogTag;
import com.oog.thewikigame.utilities.Logger;

import java.util.List;

public class BindingAdapters {

    @BindingAdapter("values")
    public static void setArrayAdapter(AutoCompleteTextView view, List<String> values) {
        try {
            view.setAdapter(new ArrayAdapter<>(view.getContext(),
                    android.R.layout.simple_list_item_1,
                    values));
        } catch (Exception e) {
            //TODO: Delete this logger.
            Logger.log(LogTag.EXCEPTION, "At BindingAdapters::setArrayAdapter", e);
        }
    }

}
