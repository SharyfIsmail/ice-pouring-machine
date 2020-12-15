package com.oim.icepouring.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.oim.icepouring.R;
import com.oim.icepouring.batteryModule.model.BatteryDataMonitor;
import com.oim.icepouring.batteryModule.tx.Battery_0810FFFF;
import com.oim.icepouring.databinding.ActivityMainBinding;
import com.oim.icepouring.thread.ReceiveThread;
import com.oim.icepouring.usb.UsbConnector;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private UsbConnector usbConnector;
    private BatteryDataMonitor batteryDataMonitor;
    private ReceiveThread receiveThread;
    private ActivityMainBinding activityMainBinding;
private static int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        batteryDataMonitor = new BatteryDataMonitor(activityMainBinding);

        receiveThread = new ReceiveThread(batteryDataMonitor);
        activityMainBinding.setBattery0810FFFFModel(batteryDataMonitor.getBattery_0810FFFF_model());
        activityMainBinding.setBatteryState0C07F301Model(batteryDataMonitor.getBatteryState_0C07F301_model());
        activityMainBinding.setContactors0CFEF301Model(batteryDataMonitor.getContactors_0CFEF301_model());

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        usbConnector = new UsbConnector(this);
        try {
            usbConnector.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        receiveThread.setDaemon(true);
        receiveThread.setUnitIdMapper(batteryDataMonitor.getDataModel());
        batteryDataMonitor.getContactors_0CFEF301_model().getGroundState().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

            }
        });
        batteryDataMonitor.getBatteryState_0C07F301_model().getBatteryStatus().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

            }
        });
        receiveThread.start();

    }
    @Override
    protected  void onPause()
    {
    super.onPause();
    }
    @Override
    protected void onDestroy(){
        try {
            usbConnector.closeConnection();
            super.onDestroy();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}