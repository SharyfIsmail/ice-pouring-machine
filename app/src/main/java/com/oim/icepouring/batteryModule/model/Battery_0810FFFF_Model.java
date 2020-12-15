package com.oim.icepouring.batteryModule.model;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.databinding.Observable;
import androidx.databinding.ObservableFloat;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableShort;

import com.oim.icepouring.R;
import com.oim.icepouring.batteryModule.tx.Battery_0810FFFF;
import com.oim.icepouring.can.candata.DataFromDevice;
import com.oim.icepouring.can.candata.DataFromDeviceModel;
import com.oim.icepouring.databinding.ActivityMainBinding;


public class Battery_0810FFFF_Model implements DataFromDeviceModel {
    private Battery_0810FFFF battery_0810FFFF;
    private ObservableInt current;
    private ObservableShort soc;
    private ObservableShort maxTemp;
    private ObservableShort minTemp;
    private ObservableFloat totalBatteryVoltage;
    private ActivityMainBinding activityMainBinding;


    public ObservableInt getCurrent() {
        return current;
    }

    public void setCurrent(ObservableInt current) {
        this.current = current;
    }

    public ObservableShort getSoc() {
        return soc;
    }

    public void setSoc(ObservableShort soc) {
        this.soc = soc;
    }

    public ObservableShort getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(ObservableShort maxTemp) {
        this.maxTemp = maxTemp;
    }

    public ObservableShort getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(ObservableShort minTemp) {
        this.minTemp = minTemp;
    }

    public ObservableFloat getTotalBatteryVoltage() {
        return totalBatteryVoltage;
    }

    public void setTotalBatteryVoltage(ObservableFloat totalBatteryVoltage) {
        this.totalBatteryVoltage = totalBatteryVoltage;
    }

    public Battery_0810FFFF_Model(ActivityMainBinding activityMainBinding) {
        this.activityMainBinding = activityMainBinding;
        battery_0810FFFF = new Battery_0810FFFF();
        current = new ObservableInt(0);
        soc = new ObservableShort((short) 0);
        maxTemp = new ObservableShort((short) 0);
        minTemp = new ObservableShort((short) 0);
        totalBatteryVoltage = new ObservableFloat(0.0f);
        soc.addOnPropertyChangedCallback(new PropertyChangedCallbackSoc());
    }

    @Override
    public void updateModel() {
        current.set(battery_0810FFFF.getCurrent());
        soc.set(battery_0810FFFF.getSoc());
        maxTemp.set(battery_0810FFFF.getMaxTemp());
        minTemp.set(battery_0810FFFF.getMinTemp());
        totalBatteryVoltage.set(battery_0810FFFF.getTotalBatteryVoltage());
    }

    @Override
    public DataFromDevice getDataFromDevice() {
        return battery_0810FFFF;
    }

    private class PropertyChangedCallbackSoc extends Observable.OnPropertyChangedCallback
    {
        private Handler threadHandler;
       public  PropertyChangedCallbackSoc()
       {
           threadHandler = new Handler(Looper.getMainLooper());
       }
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
        threadHandler.post(new Runnable() {
            @Override
            public void run() {
               short socValue = getSoc().get();
                activityMainBinding.batteySocProgressBar.setFinishedColor(socValue >= 40 ?
                        Color.parseColor("#00CC00") : socValue < 20 ?
                        Color.parseColor("#FF0000") : Color.parseColor("#FF6600"));
                activityMainBinding.uncharging.setColorFilter(socValue >= 40 ?
                        Color.parseColor("#00CC00") : socValue < 20 ?
                        Color.parseColor("#FF0000") : Color.parseColor("#FF6600"));
                activityMainBinding.batterySocError.setVisibility(socValue == 255 ? View.VISIBLE : View.GONE);
                activityMainBinding.batteySocProgressBar.setProgress(socValue != 255 ? socValue : 0);

            }
        });
        }
    }

}
