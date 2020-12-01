package com.oim.icepouring.batteryModul.model;

import com.oim.icepouring.batteryModul.tx.BatteryState_0C07F301;
import com.oim.icepouring.can.candata.DataFromDevice;
import com.oim.icepouring.can.candata.DataFromDeviceModel;

public class BatteryState_0C07F301_Model implements DataFromDeviceModel {
    private BatteryState_0C07F301 batteryState_0C07F301;

    public BatteryState_0C07F301_Model()
    {
        batteryState_0C07F301 = new BatteryState_0C07F301();
    }
    @Override
    public void updateModel() {

    }

    @Override
    public DataFromDevice getDataFromDevice() {
        return batteryState_0C07F301;
    }
}
