package com.oim.icepouring.batteryModul.model;

import com.oim.icepouring.batteryModul.tx.Contactors_0CFEF301;
import com.oim.icepouring.can.candata.DataFromDevice;
import com.oim.icepouring.can.candata.DataFromDeviceModel;

public class Contactors_0CFEF301_Model implements DataFromDeviceModel {

    private Contactors_0CFEF301 contactors_0CFEF301;
    public Contactors_0CFEF301_Model()
    {
        contactors_0CFEF301 = new Contactors_0CFEF301();
    }

    @Override
    public void updateModel() {


    }

    @Override
    public DataFromDevice getDataFromDevice() {
        return contactors_0CFEF301;
    }
}
