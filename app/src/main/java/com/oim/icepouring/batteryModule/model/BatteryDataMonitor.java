package com.oim.icepouring.batteryModule.model;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.databinding.Observable;

import com.oim.icepouring.batteryModule.tx.Contactors_0CFEF301;
import com.oim.icepouring.can.candata.DataFromDeviceModel;
import com.oim.icepouring.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;

public class BatteryDataMonitor
{
    private Map<Integer, DataFromDeviceModel> dataModel ;
    private  BatteryState_0C07F301_Model batteryState_0C07F301_model ;
    private Battery_0810FFFF_Model battery_0810FFFF_model;
    private Contactors_0CFEF301_Model contactors_0CFEF301_model;
    private ActivityMainBinding activityMainBinding;
    public BatteryDataMonitor(ActivityMainBinding activityMainBinding)
    {
        dataModel = new HashMap<>();
        this.activityMainBinding = activityMainBinding;
        batteryState_0C07F301_model = new BatteryState_0C07F301_Model(activityMainBinding);
        battery_0810FFFF_model = new Battery_0810FFFF_Model(activityMainBinding);
        contactors_0CFEF301_model = new Contactors_0CFEF301_Model();
        dataModel.put(0x0810FFFF, battery_0810FFFF_model);
        dataModel.put(0x0C07F301,batteryState_0C07F301_model);
        dataModel.put(0x0CFEF301, contactors_0CFEF301_model);
    }

    public Map<Integer, DataFromDeviceModel> getDataModel() {
        return dataModel;
    }

    public void setDataModel(Map<Integer, DataFromDeviceModel> dataModel) {
        this.dataModel = dataModel;
    }

    public BatteryState_0C07F301_Model getBatteryState_0C07F301_model() {
        return batteryState_0C07F301_model;
    }

    public void setBatteryState_0C07F301_model(BatteryState_0C07F301_Model batteryState_0C07F301_model) {
        this.batteryState_0C07F301_model = batteryState_0C07F301_model;
    }

    public Battery_0810FFFF_Model getBattery_0810FFFF_model() {
        return battery_0810FFFF_model;
    }

    public void setBattery_0810FFFF_model(Battery_0810FFFF_Model battery_0810FFFF_model) {
        this.battery_0810FFFF_model = battery_0810FFFF_model;
    }

    public Contactors_0CFEF301_Model getContactors_0CFEF301_model() {
        return contactors_0CFEF301_model;
    }

    public void setContactors_0CFEF301_model(Contactors_0CFEF301_Model contactors_0CFEF301_model) {
        this.contactors_0CFEF301_model = contactors_0CFEF301_model;
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
                    switch (batteryState_0C07F301_model.getBatteryStatus().get())
                    {
                        case "Battery Charging":
                            activityMainBinding.uncharging.setVisibility(View.GONE);
                            activityMainBinding.charging.setVisibility(View.VISIBLE);
                            activityMainBinding.stationCharing.setVisibility(View.VISIBLE);
                            break;
                        default:
                            activityMainBinding.charging.setVisibility(View.GONE);
                            activityMainBinding.uncharging.setVisibility(View.VISIBLE);
                            activityMainBinding.stationCharing.setVisibility(View.GONE);
                    }

                }
            });
        }
    }
}
