package com.oim.icepouring.batteryModule.model;

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

    public Contactors_0CFEF301_Model()
    {
        contactors_0CFEF301 = new Contactors_0CFEF301();
        prechargeState = new ObservableField<>();
        plusState = new ObservableField<>();
        groundState = new ObservableField<>();
        sensorState = new ObservableField<>();
    }

    @Override
    public void updateModel() {
        prechargeState.set(getErrorName(contactors_0CFEF301.getContactorError().getPrecharge()));
        plusState.set(getErrorName(contactors_0CFEF301.getContactorError().getPlus()));
        groundState.set(getErrorName(contactors_0CFEF301.getContactorError().getGround()));
        sensorState.set(getErrorName(contactors_0CFEF301.getContactorError().getSensor()));

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
