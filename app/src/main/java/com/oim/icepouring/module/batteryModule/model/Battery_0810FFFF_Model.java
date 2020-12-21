package com.oim.icepouring.module.batteryModule.model;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.databinding.Observable;
import androidx.databinding.ObservableFloat;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableShort;

import com.oim.icepouring.module.batteryModule.ErrorChecker;
import com.oim.icepouring.module.batteryModule.tx.Battery_0810FFFF;
import com.oim.icepouring.can.candata.DataFromDevice;
import com.oim.icepouring.can.candata.DataFromDeviceModel;
import com.oim.icepouring.databinding.ActivityMainBinding;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Battery_0810FFFF_Model implements DataFromDeviceModel, ErrorChecker {
    private Battery_0810FFFF battery_0810FFFF;
    private ObservableInt current;
    private ObservableShort soc;
    private ObservableShort maxTemp;
    private ObservableShort minTemp;
    private ObservableFloat totalBatteryVoltage;
    private List<String> errors;

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

    public Battery_0810FFFF_Model() {
        battery_0810FFFF = new Battery_0810FFFF();
        current = new ObservableInt(0);
        soc = new ObservableShort((short) 0);
        maxTemp = new ObservableShort((short) 0);
        minTemp = new ObservableShort((short) 0);
        totalBatteryVoltage = new ObservableFloat(0.0f);
        errors = new CopyOnWriteArrayList<>();

    }

    @Override
    public void updateModel() {
        current.set(battery_0810FFFF.getCurrent());
        soc.set(battery_0810FFFF.getSoc());
        maxTemp.set(battery_0810FFFF.getMaxTemp());
        minTemp.set(battery_0810FFFF.getMinTemp());
        totalBatteryVoltage.set(battery_0810FFFF.getTotalBatteryVoltage());
        registerError(battery_0810FFFF.getSoc());
    }

    @Override
    public DataFromDevice getDataFromDevice() {
        return battery_0810FFFF;
    }

    @Override
    public void registerError(Object object) {
    int value = (Integer) object;
        if(value == 255)
        {
            errors.add("Battery undefined");
        }
        else
        {
            errors.remove("Battery undefined");
        }
        if(value <40 && value > 10)
        {
            errors.add("Battery half charged");
        }
        else
        {
            errors.remove("Battery half charged");

        }
        if(value <= 10)
        {
            errors.add("Low Battery");

        }
        else
        {
            errors.remove("Low Battery");
        }
    }

    @Override
    public boolean checkErrorExistence() {
        return errors.isEmpty();
    }

    @Override
    public List<String> getErrorList() {
        return errors;
    }
}
