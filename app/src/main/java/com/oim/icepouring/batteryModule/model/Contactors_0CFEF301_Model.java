package com.oim.icepouring.batteryModule.model;

import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.oim.icepouring.batteryModule.tx.Contactors_0CFEF301;
import com.oim.icepouring.can.candata.DataFromDevice;
import com.oim.icepouring.can.candata.DataFromDeviceModel;

import java.util.Map;

public class Contactors_0CFEF301_Model implements DataFromDeviceModel {

    private Contactors_0CFEF301 contactors_0CFEF301;
    private ObservableField<String> prechargeState;
    private ObservableField<String> plusState;
    private ObservableField<String> groundState;
    private ObservableField<String> sensorState;
//
//    private ObservableBoolean prechargeStatus;
//    private ObservableBoolean plusStatus;
//    private ObservableBoolean groundStatus;
//    private ObservableBoolean sensorStatus;


    public ObservableField<String> getPrechargeState() {
        return prechargeState;
    }

    public void setPrechargeState(ObservableField<String> prechargeState) {
        this.prechargeState = prechargeState;
    }

    public ObservableField<String> getPlusState() {
        return plusState;
    }

    public void setPlusState(ObservableField<String> plusState) {
        this.plusState = plusState;
    }

    public ObservableField<String> getGroundState() {
        return groundState;
    }

    public void setGroundState(ObservableField<String> groundState) {
        this.groundState = groundState;
    }

    public ObservableField<String> getSensorState() {
        return sensorState;
    }

    public void setSensorState(ObservableField<String> sensorState) {
        this.sensorState = sensorState;
    }

    public Contactors_0CFEF301_Model()
    {
        contactors_0CFEF301 = new Contactors_0CFEF301();
        prechargeState = new ObservableField<>();
        plusState = new ObservableField<>();
        groundState = new ObservableField<>();
        sensorState = new ObservableField<>();

//        prechargeStatus = new ObservableBoolean();
//        prechargeStatus.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
//            @Override
//            public void onPropertyChanged(Observable sender, int propertyId) {
//
//            }
//        });
//        plusStatus = new ObservableBoolean();
//        plusStatus.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
//            @Override
//            public void onPropertyChanged(Observable sender, int propertyId) {
//
//            }
//        });
//        groundStatus = new ObservableBoolean();
//        groundStatus.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
//            @Override
//            public void onPropertyChanged(Observable sender, int propertyId) {
//
//            }
//        });
//        sensorStatus = new ObservableBoolean();
//        sensorStatus.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
//            @Override
//            public void onPropertyChanged(Observable sender, int propertyId) {
//
//            }
//        });

    }

    @Override
    public void updateModel() {
        prechargeState.set(getErrorName(contactors_0CFEF301.getContactorError().getPrecharge()));
        plusState.set(getErrorName(contactors_0CFEF301.getContactorError().getPlus()));
        groundState.set(getErrorName(contactors_0CFEF301.getContactorError().getGround()));
        sensorState.set(getErrorName(contactors_0CFEF301.getContactorError().getSensor()));

//        plusStatus.set(!contactors_0CFEF301.getContactorError().getPlus().isEmpty());
//        groundStatus.set(!contactors_0CFEF301.getContactorError().getGround().isEmpty());
//        prechargeStatus.set(!contactors_0CFEF301.getContactorError().getPrecharge().isEmpty());
//        sensorStatus.set(!contactors_0CFEF301.getContactorError().getSensor().isEmpty());

    }

    @Override
    public DataFromDevice getDataFromDevice() {
        return contactors_0CFEF301;
    }

    private String getErrorName(Map<Integer, String> map)
    {
        StringBuilder stringBuilder = new StringBuilder("");
            for(Map.Entry<Integer,String> entry :map.entrySet())
            {
                stringBuilder.append(entry.getValue());
            }
            return stringBuilder.toString();
     }
}
