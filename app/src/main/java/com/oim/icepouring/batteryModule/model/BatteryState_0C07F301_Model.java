package com.oim.icepouring.batteryModule.model;

import androidx.databinding.ObservableField;

import com.oim.icepouring.batteryModule.tx.BatteryState_0C07F301;
import com.oim.icepouring.can.candata.DataFromDevice;
import com.oim.icepouring.can.candata.DataFromDeviceModel;

public class BatteryState_0C07F301_Model implements DataFromDeviceModel {
    private BatteryState_0C07F301 batteryState_0C07F301;
    private ObservableField<String> batteryStatus ;
    public BatteryState_0C07F301_Model()
    {
        batteryState_0C07F301 = new BatteryState_0C07F301();
        batteryStatus = new ObservableField<>();
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
