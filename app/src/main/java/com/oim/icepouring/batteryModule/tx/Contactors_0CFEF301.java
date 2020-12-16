package com.oim.icepouring.batteryModule.tx;

import com.oim.icepouring.can.candata.DataFromDevice;
import com.oim.icepouring.util.Parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class Contactors_0CFEF301 implements DataFromDevice {

    private short contactorState;

    public short getContactorState() {
        return contactorState;
    }

    public void setContactorState(short contactorState) {
        this.contactorState = contactorState;
    }

    @Override
    public void parseDataFromCan(byte[] data) {
        if(data.length == 8){
            byte[] partArray = new byte[2];
            System.arraycopy(data, 4, partArray, 0, partArray.length);
            contactorState = (short) Parser.LittleIndianParser.uint_16ToInt(partArray);
        }
    }
}
