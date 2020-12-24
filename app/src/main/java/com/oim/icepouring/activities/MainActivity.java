package com.oim.icepouring.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.github.lzyzsd.circleprogress.CircleProgress;
import com.oim.icepouring.R;
import com.oim.icepouring.errorModel.ErrorViewerModel;
import com.oim.icepouring.module.batteryModule.BatteryDataMonitor;
import com.oim.icepouring.databinding.ActivityMainBinding;
import com.oim.icepouring.thread.ErrorViewer;
import com.oim.icepouring.vectorModel.vectorBatteryModel.BatteryVectorUpdater;
import com.oim.icepouring.thread.ReceiveThread;
import com.oim.icepouring.usb.UsbConnector;

import java.io.IOException;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private UsbConnector usbConnector;
    private BatteryDataMonitor batteryDataMonitor;
    private ReceiveThread receiveThread;
    private ActivityMainBinding activityMainBinding;
    private BatteryVectorUpdater batteryVectorUpdater;

    private ErrorViewer errorViewer;
    private ErrorViewerModel errorViewerModel;

    private SpeedPanelFragment speedPanelFragment;
    private  ChargeFragment chargeFragment;

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        speedPanelFragment = new SpeedPanelFragment();
        chargeFragment = new ChargeFragment();

        batteryDataMonitor = new BatteryDataMonitor();

        errorViewerModel = new ErrorViewerModel(batteryDataMonitor);
        errorViewer = new ErrorViewer(errorViewerModel);

        activityMainBinding.setBattery0810FFFFModel(batteryDataMonitor.getBattery_0810FFFF_model());
        activityMainBinding.setBatteryState0C07F301Model(batteryDataMonitor.getBatteryState_0C07F301_model());
        activityMainBinding.setContactors0CFEF301Model(batteryDataMonitor.getContactors_0CFEF301_model());
        activityMainBinding.setErrorViewerModel(errorViewerModel);

        setSocBarProperties(activityMainBinding.batteySocProgressBar);

        batteryVectorUpdater = new BatteryVectorUpdater(batteryDataMonitor, activityMainBinding,  getSupportFragmentManager());
        batteryVectorUpdater.setChargeFragment(chargeFragment);
        batteryVectorUpdater.setSpeedPanelFragment(speedPanelFragment);

        getSupportFragmentManager().beginTransaction().add(activityMainBinding.flFragment.getId(), speedPanelFragment).commit();

        receiveThread = new ReceiveThread();
        timer = new Timer();
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
        receiveThread.setUsbConnector(usbConnector);
        timer.schedule(errorViewer, 1000, 1500);
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
    private void setSocBarProperties(CircleProgress batteySocProgressBar)
    {
        batteySocProgressBar.setTextSize(30);
        batteySocProgressBar.setUnfinishedColor(Color.parseColor("#616161"));
        batteySocProgressBar.setBackgroundColor(Color.parseColor("#322F2F"));
    }
}