package com.oim.icepouring.batteryModule.model;

import androidx.databinding.ObservableFloat;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableShort;

import com.oim.icepouring.batteryModule.tx.Battery_0810FFFF;
import com.oim.icepouring.can.candata.DataFromDevice;
import com.oim.icepouring.can.candata.DataFromDeviceModel;

public class Battery_0810FFFF_Model implements DataFromDeviceModel
{
    private Battery_0810FFFF battery_0810FFFF;
    private ObservableInt current ;
    private ObservableShort soc;
    private ObservableShort maxTemp;
    private ObservableShort minTemp;
    private ObservableFloat totalBatteryVoltage;

    public Battery_0810FFFF_Model()
    {
        battery_0810FFFF = new Battery_0810FFFF();
        current = new ObservableInt(0);
        soc = new ObservableShort((short) 0);
        maxTemp = new ObservableShort((short) 0);
        minTemp = new ObservableShort((short) 0);
        totalBatteryVoltage  = new ObservableFloat(0.0f);

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
}
