package com.oim.icepouring.vectorModel;

import android.widget.ImageView;

import androidx.databinding.Observable;

public interface VectorUpdater {
    public void setVisible(boolean isTrue, ImageView... imageView) ;

    void setOnAction(Observable baseObservableField, Observable.OnPropertyChangedCallback callback);
}
