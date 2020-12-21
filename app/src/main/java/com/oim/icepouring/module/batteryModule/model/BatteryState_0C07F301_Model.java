package com.oim.icepouring.module.batteryModule.model;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import com.oim.icepouring.module.batteryModule.ErrorChecker;
import com.oim.icepouring.module.batteryModule.tx.BatteryState_0C07F301;
import com.oim.icepouring.can.candata.DataFromDevice;
import com.oim.icepouring.can.candata.DataFromDeviceModel;
import com.oim.icepouring.databinding.ActivityMainBinding;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BatteryState_0C07F301_Model implements DataFromDeviceModel, ErrorChecker {
    private BatteryState_0C07F301 batteryState_0C07F301;
    private ObservableField<String> batteryStatus ;
    private List<String> errors;
    public ObservableField<String> getBatteryStatus() {
        return batteryStatus;
    }
    public void setBatteryStatus(ObservableField<String> batteryStatus) {
        this.batteryStatus = batteryStatus;
    }

    public BatteryState_0C07F301_Model(  )
    {
        errors = new CopyOnWriteArrayList<>();
        batteryState_0C07F301 = new BatteryState_0C07F301();
        batteryStatus = new ObservableField<>("");
    }
    @Override
    public void updateModel() {

        batteryStatus.set(batteryState_0C07F301.getBmsStatus());
        registerError(batteryState_0C07F301.getBmsStatus());
    }

    @Override
    public DataFromDevice getDataFromDevice() {
        return batteryState_0C07F301;
    }

    @Override
    public void registerError(Object object) {
        String error = (String) object;
        if(error.equals("Battery On With Error"))
        {
            errors.clear();
            errors.add(error);
        }
        else if(error.equals("Battery Off With Error"))
        {
            errors.clear();
            errors.add(error);
        }
        else
            errors.clear();
    }

    @Override
    public boolean checkErrorExistence() {
        return  errors.isEmpty();
    }

    @Override
    public List <String>getErrorList() {
        return errors;
    }
}
