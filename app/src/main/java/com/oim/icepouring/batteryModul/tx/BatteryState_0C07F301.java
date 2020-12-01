package com.oim.icepouring.batteryModul.tx;

import com.oim.icepouring.can.candata.DataFromDevice;

import java.util.HashMap;
import java.util.Map;

public class BatteryState_0C07F301 implements DataFromDevice {
    private String bmsStatus;

    public String getBmsStatus() {
        return bmsStatus;
    }

    public void setBmsStatus(String bmsStatus) {
        this.bmsStatus = bmsStatus;
    }

    @Override
    public void parseDataFromCan(byte[] data) {
        if(data.length == 8) {
            bmsStatus = BmsState.getBatteryStatus(data[1]);
        }
    }
    private static class BmsState
    {
        private static Map<Integer, String> bsmStatus = new HashMap<>();

        static
        {
            bsmStatus.put(0, "Battery Off");
            bsmStatus.put(1, "Battery Init");
            bsmStatus.put(2, "Battery Ready");
            bsmStatus.put(3, "Battery On");
            bsmStatus.put(4, "Battery Charging");
            bsmStatus.put(5, "Battery On With Error");
            bsmStatus.put(6, "Battery Off With Error");
            bsmStatus.put(7, "Battery Charging Off");
        }
        public static String getBatteryStatus(byte code)
        {
            return bsmStatus.get(code & 0x0F);
        }
    }
}
