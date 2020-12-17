package com.oim.icepouring.module.batteryModule.vectorBatteryModel;

import android.widget.ImageView;

import androidx.databinding.Observable;

public interface VectorUpdater {
    public void setVisible(boolean isTrue, ImageView... imageView) ;

    void setOnAction(Observable baseObservableField, Observable.OnPropertyChangedCallback callback);
}
