package com.oim.icepouring.module.batteryModule.vectorBatteryModel;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.Observable;

import com.oim.icepouring.databinding.ActivityMainBinding;
import com.oim.icepouring.module.batteryModule.BatteryDataMonitor;
import com.oim.icepouring.module.batteryModule.vectorBatteryModel.VectorUpdater;

import java.util.Objects;

public class BatteryVectorUpdater implements VectorUpdater {
  private BatteryDataMonitor batteryDataMonitor;
  private ActivityMainBinding activityMainBinding;
  private  volatile  boolean isBatterySocRed;
  private  volatile boolean isBatteryDamaged;
  private  volatile boolean isContactorServiceable;

  public BatteryVectorUpdater( BatteryDataMonitor batteryDataMonitor, ActivityMainBinding activityMainBinding)
  {
    this.batteryDataMonitor = batteryDataMonitor;
    this.activityMainBinding = activityMainBinding;
    setOnAction(batteryDataMonitor.getBattery_0810FFFF_model().getSoc(), new PropertyChangedCallbackSoc());
    setOnAction(batteryDataMonitor.getBatteryState_0C07F301_model().getBatteryStatus(), new PropertyChangedCallbackBatteryState());
    setOnAction(batteryDataMonitor.getContactors_0CFEF301_model().getIsErrorPresented(), new PropertyChangedCallbackContactorState());
  }
    @Override
    public void setVisible(boolean isTrue, ImageView... imageView) {
      for( ImageView imageView1 : imageView)
        imageView1.setVisibility(isTrue? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setOnAction(Observable baseObservableField, Observable.OnPropertyChangedCallback callback) {
        baseObservableField.addOnPropertyChangedCallback(callback);
   }
  private class PropertyChangedCallbackBatteryState extends Observable.OnPropertyChangedCallback {
    private Handler threadHandler;
    public PropertyChangedCallbackBatteryState() {
      threadHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onPropertyChanged(Observable sender, int propertyId) {
      threadHandler.post(new Runnable() {
        @Override
        public void run() {

          switch (Objects.requireNonNull(batteryDataMonitor.getBatteryState_0C07F301_model().getBatteryStatus().get())) {
            case "Battery Charging":
              setVisible(false, activityMainBinding.uncharging);
              setVisible(true, activityMainBinding.charging);
              setVisible(isBatteryDamaged = false, activityMainBinding.batteryErrorActive);
              break;
            case "Battery On With Error" :
              setVisible(true, activityMainBinding.uncharging);
              setVisible(false, activityMainBinding.charging);
              setVisible(isBatteryDamaged = true, activityMainBinding.batteryErrorActive);
            case "Battery Off With Error" :
              setVisible(true, activityMainBinding.uncharging);
              setVisible(false, activityMainBinding.charging);
              setVisible(isBatteryDamaged = true, activityMainBinding.batteryErrorActive);
            default:
              setVisible(true, activityMainBinding.uncharging);
              setVisible(false, activityMainBinding.charging);
              setVisible(isBatteryDamaged = false, activityMainBinding.batteryErrorActive);
          }
        }
      });
    }
  }
  private class PropertyChangedCallbackSoc extends Observable.OnPropertyChangedCallback
  {
    private Handler threadHandler;
    public  PropertyChangedCallbackSoc()
    {
      threadHandler = new Handler(Looper.getMainLooper());
    }
    @Override
    public void onPropertyChanged(Observable sender, int propertyId) {
      threadHandler.post(new Runnable() {
        @Override
        public void run() {
          short socValue = batteryDataMonitor.getBattery_0810FFFF_model().getSoc().get();
          activityMainBinding.batteySocProgressBar.setFinishedColor(socValue >= 40 ?
                  Color.parseColor("#00CC00") : socValue < 20 ?
                  Color.parseColor("#FF0000") : Color.parseColor("#FF6600"));

          activityMainBinding.uncharging.setColorFilter(socValue >= 40 ?
                  Color.parseColor("#00CC00") : socValue < 20 ?
                  Color.parseColor("#FF0000") : Color.parseColor("#FF6600"));

          activityMainBinding.charging.setColorFilter(socValue >= 40 ?
                  Color.parseColor("#00CC00") : socValue < 20 ?
                  Color.parseColor("#FF0000") : Color.parseColor("#FF6600"));

          setVisible(isBatteryDamaged = (socValue == 255), activityMainBinding.batterySocError, activityMainBinding.batteryErrorActive);
          setVisible(isBatterySocRed = socValue <= 10, activityMainBinding.batteryErrorActive );
          setVisible(!isBatterySocRed && !isBatteryDamaged && !isContactorServiceable && (socValue < 40 && socValue > 10), activityMainBinding.batteryAlertActive);
          activityMainBinding.batteySocProgressBar.setProgress(socValue != 255 ? socValue : 0);

        }
      });
    }
  }

  private class PropertyChangedCallbackContactorState extends Observable.OnPropertyChangedCallback
  {
    private Handler threadHandler;
    public  PropertyChangedCallbackContactorState()
    {
      threadHandler = new Handler(Looper.getMainLooper());
    }
    @Override
    public void onPropertyChanged(Observable sender, int propertyId) {
      threadHandler.post(new Runnable() {
        @Override
        public void run() {
          boolean  isTrue = activityMainBinding.getContactors0CFEF301Model().getIsErrorPresented().get();
          setVisible(isContactorServiceable = isTrue, activityMainBinding.batteryErrorActive);
        }
      });
    }
  }
}
