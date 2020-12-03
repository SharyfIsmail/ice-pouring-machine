package com.oim.icepouring.batteryModule.tx;

import com.oim.icepouring.can.candata.DataFromDevice;
import com.oim.icepouring.util.Parser;

public class Battery_0810FFFF implements DataFromDevice
{

    private int current;
    private float totalBatteryVoltage;
    private short soc;
    private short maxTemp;
    private short minTemp;

    public int getCurrent() {
        return current;
    }

    public float getTotalBatteryVoltage() {
        return totalBatteryVoltage / 100;
    }

    public short getSoc() {
        return soc;
    }

    public short getMaxTemp() {
        return (short)(maxTemp - 35);
    }

    public short getMinTemp() {
        return (short)(minTemp - 35);
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setTotalBatteryVoltage(float totalBatteryVoltage) {
        this.totalBatteryVoltage = totalBatteryVoltage;
    }

    public void setSoc(short soc) {
        this.soc = soc;
    }

    public void setMaxTemp(short maxTemp) {
        this.maxTemp = maxTemp;
    }

    public void setMinTemp(short minTemp) {
        this.minTemp = minTemp;
    }

    @Override
    public void parseDataFromCan(byte[] data) {
        if(data.length == 8)
        {
            byte[] partArray = new byte[2];
            System.arraycopy(data, 1, partArray, 0, partArray.length);
            current = Parser.LittleIndianParser.uint_16ToInt(partArray);

            System.arraycopy(data, 3, partArray, 0, partArray.length);
            totalBatteryVoltage = Parser.LittleIndianParser.uint_16ToInt(partArray);

            soc = Parser.LittleIndianParser.uint_8ToShort(data[5]);

            maxTemp = Parser.LittleIndianParser.uint_8ToShort(data[6]);
            minTemp = Parser.LittleIndianParser.uint_8ToShort(data[7]);
        }

    }
}
