package com.oim.icepouring.vectorModel.vectorBatteryModel;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.Observable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.oim.icepouring.activities.ChargeFragment;
import com.oim.icepouring.activities.SpeedPanelFragment;
import com.oim.icepouring.databinding.ActivityMainBinding;
import com.oim.icepouring.module.batteryModule.BatteryDataMonitor;
import com.oim.icepouring.vectorModel.VectorUpdater;

import java.util.Objects;

public class BatteryVectorUpdater implements VectorUpdater {
  private BatteryDataMonitor batteryDataMonitor;
  private ActivityMainBinding activityMainBinding;
  private FragmentManager fragmentManager;
  private  volatile  boolean isBatterySocRed;
  private  volatile boolean isBatteryDamaged;
  private  volatile boolean isContactorServiceable;

  private SpeedPanelFragment speedPanelFragment;
  private ChargeFragment chargeFragment;
  private int i = 0;
  public void  setSpeedPanelFragment(SpeedPanelFragment fragment)
  {
    speedPanelFragment = fragment;
  }
  public void  setChargeFragment(ChargeFragment fragment)
  {
    chargeFragment = fragment;
  }
  public BatteryVectorUpdater( BatteryDataMonitor batteryDataMonitor, ActivityMainBinding activityMainBinding, FragmentManager fragmentManager)
  {
    this.batteryDataMonitor = batteryDataMonitor;
    this.activityMainBinding = activityMainBinding;
    this.fragmentManager = fragmentManager;
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
              isBatteryDamaged = false;
              setVisible(isBatteryDamaged  || isBatterySocRed || isContactorServiceable, activityMainBinding.batteryErrorActive);
              fragmentManager.beginTransaction().replace(activityMainBinding.flFragment.getId(), chargeFragment).commit();

              break;

            case "Battery On With Error" :
            case "Battery Off With Error" :
              setVisible(true, activityMainBinding.uncharging);
              setVisible(false, activityMainBinding.charging);
              isBatteryDamaged = true;
              setVisible(isBatteryDamaged  || isBatterySocRed || isContactorServiceable, activityMainBinding.batteryErrorActive);

              fragmentManager.beginTransaction().replace(activityMainBinding.flFragment.getId(), speedPanelFragment).commit();
              break;

            default:
              setVisible(true, activityMainBinding.uncharging);
              setVisible(false, activityMainBinding.charging);

              isBatteryDamaged = false;
              setVisible(isBatteryDamaged  || isBatterySocRed || isContactorServiceable, activityMainBinding.batteryErrorActive);

              fragmentManager.beginTransaction().replace(activityMainBinding.flFragment.getId(), speedPanelFragment).commit();


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
          setVisible(isBatteryDamaged = (socValue == 255) , activityMainBinding.batterySocError);
          setVisible(isBatteryDamaged || isBatterySocRed || isContactorServiceable,  activityMainBinding.batteryErrorActive);
          setVisible((isBatterySocRed = socValue <= 10) || isBatteryDamaged || isBatterySocRed , activityMainBinding.batteryErrorActive );
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
          setVisible(isContactorServiceable = isTrue || isBatterySocRed || isBatteryDamaged, activityMainBinding.batteryErrorActive);
        }
      });
    }
  }
}
