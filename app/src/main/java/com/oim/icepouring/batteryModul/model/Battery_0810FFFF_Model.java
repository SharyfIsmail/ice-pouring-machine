package com.oim.icepouring.batteryModul.model;

import com.oim.icepouring.batteryModul.tx.Battery_0810FFFF;
import com.oim.icepouring.can.candata.DataFromDevice;
import com.oim.icepouring.can.candata.DataFromDeviceModel;

public class Battery_0810FFFF_Model implements DataFromDeviceModel
{
    private Battery_0810FFFF battery_0810FFFF;

    public Battery_0810FFFF_Model()
    {
        battery_0810FFFF = new Battery_0810FFFF();
    }

    @Override
    public void updateModel() {

    }

    @Override
    public DataFromDevice getDataFromDevice() {
        return battery_0810FFFF;
    }
}
