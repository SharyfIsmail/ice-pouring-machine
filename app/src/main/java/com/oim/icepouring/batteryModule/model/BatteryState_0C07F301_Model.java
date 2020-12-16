package com.oim.icepouring.batteryModule.model;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import com.oim.icepouring.batteryModule.tx.BatteryState_0C07F301;
import com.oim.icepouring.can.candata.DataFromDevice;
import com.oim.icepouring.can.candata.DataFromDeviceModel;
import com.oim.icepouring.databinding.ActivityMainBinding;

public class BatteryState_0C07F301_Model implements DataFromDeviceModel {
    private BatteryState_0C07F301 batteryState_0C07F301;
    private ObservableField<String> batteryStatus ;
    private ActivityMainBinding activityMainBinding;

    public ObservableField<String> getBatteryStatus() {
        return batteryStatus;
    }

    public void setBatteryStatus(ObservableField<String> batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public BatteryState_0C07F301_Model( ActivityMainBinding activityMainBinding)
    {
        batteryState_0C07F301 = new BatteryState_0C07F301();
        this.activityMainBinding = activityMainBinding;
        batteryStatus = new ObservableField<>("");
        batteryStatus.addOnPropertyChangedCallback(new PropertyChangedCallbackBatteryState());
    }
    @Override
    public void updateModel() {
        batteryStatus.set(batteryState_0C07F301.getBmsStatus());
    }

    @Override
    public DataFromDevice getDataFromDevice() {
        return batteryState_0C07F301;
    }

    private class PropertyChangedCallbackBatteryState extends Observable.OnPropertyChangedCallback
    {
        private Handler threadHandler;
        public  PropertyChangedCallbackBatteryState()
        {
            threadHandler = new Handler(Looper.getMainLooper());
        }
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            threadHandler.post(new Runnable() {
                @Override
                public void run() {
                    switch (batteryStatus.get())
                    {
                        case "Battery Charging":
                            activityMainBinding.uncharging.setVisibility(View.GONE);
                            activityMainBinding.charging.setVisibility(View.VISIBLE);
                            break;
                        default:
                            activityMainBinding.charging.setVisibility(View.GONE);
                            activityMainBinding.uncharging.setVisibility(View.VISIBLE);
                    }

                }
            });
        }
    }
}
