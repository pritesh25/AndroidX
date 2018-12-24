package com.example.user.androidx;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class ModelData extends BaseObservable {

    private String celsius;

    @Bindable
    public String getCelsius() {
        return celsius;
    }

    public void setCelsius(String celsius) {
        this.celsius = celsius;
        notifyPropertyChanged(com.example.user.androidx.BR.celsius);
    }
}
