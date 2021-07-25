package com.oog.thewikigame.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.oog.thewikigame.BR;

public class AuthorModel extends BaseObservable {
    private final String name;
    private final String email;
    private final String linkedin;
    private boolean visible;
    public AuthorModel(String name, String email, String linkedin) {
        this.name = name;
        this.email = email;
        this.linkedin = linkedin;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLinkedin() {
        return linkedin;
    }

    @Bindable
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        notifyPropertyChanged(BR.visible);
    }
    public void toggle(){
        setVisible(!visible);
    }
}
