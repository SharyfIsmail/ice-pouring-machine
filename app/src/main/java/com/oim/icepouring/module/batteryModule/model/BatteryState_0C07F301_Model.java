package com.oim.icepouring.module.batteryModule.model;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import com.oim.icepouring.module.batteryModule.tx.BatteryState_0C07F301;
import com.oim.icepouring.can.candata.DataFromDevice;
import com.oim.icepouring.can.candata.DataFromDeviceModel;
import com.oim.icepouring.databinding.ActivityMainBinding;

public class BatteryState_0C07F301_Model implements DataFromDeviceModel {
    private BatteryState_0C07F301 batteryState_0C07F301;
    private ObservableField<String> batteryStatus ;

    public ObservableField<String> getBatteryStatus() {
        return batteryStatus;
    }
    public void setBatteryStatus(ObservableField<String> batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public BatteryState_0C07F301_Model(  )
    {
        batteryState_0C07F301 = new BatteryState_0C07F301();
        batteryStatus = new ObservableField<>("");
    }
    @Override
    public void updateModel() {
        batteryStatus.set(batteryState_0C07F301.getBmsStatus());
    }

    @Override
    public DataFromDevice getDataFromDevice() {
        return batteryState_0C07F301;
    }

}
