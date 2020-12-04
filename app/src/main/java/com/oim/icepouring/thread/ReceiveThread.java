package com.oim.icepouring.thread;

import com.oim.icepouring.batteryModule.model.BatteryDataMonitor;
import com.oim.icepouring.can.Can;
import com.oim.icepouring.can.candata.DataFromDeviceModel;
import com.oim.icepouring.usb.UsbCan;
import com.oim.icepouring.usb.UsbCanCdr;
import com.oim.icepouring.usb.UsbConnector;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class ReceiveThread extends Thread {


    private Map<Integer, DataFromDeviceModel> canPackage ;
    private UsbConnector usbConnector ;
private BatteryDataMonitor batteryDataMonitor;
    public ReceiveThread(BatteryDataMonitor batteryDataMonitor)
    {
        this.batteryDataMonitor = batteryDataMonitor;
    }
    public Map<Integer, DataFromDeviceModel> getCanPackage() {
        return canPackage;
    }

    public UsbConnector getUsbConnector() {
        return usbConnector;
    }

    public void setUsbConnector(UsbConnector usbConnector) {
        this.usbConnector = usbConnector;
    }


    @Override
    public void run() {

        if(usbConnector.getUsbConnection() != null) {
            byte[] receiveBuffer = new byte[512];
            while (!isInterrupted()) {
                if(usbConnector.getUsbSerialPort().isOpen())
                {
                    try {
                        int len =usbConnector.getUsbSerialPort().read(receiveBuffer, 100);// time doesn't matter
                        if (len > 0) {
                            byte[] array = Arrays.copyOf(receiveBuffer, len);
                            objectMapping(array);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private void objectMapping(byte[] data)
    {
        UsbCan usbCanPackage = new UsbCanCdr();
        usbCanPackage.parseUsbPacket(data);
        for(Can can : usbCanPackage.getAllCan())
        {
            if(canPackage.get(can.getId()) != null)
            {
                DataFromDeviceModel dataFromDeviceModel = canPackage.get(can.getId());
                Objects.requireNonNull(dataFromDeviceModel).getDataFromDevice().parseDataFromCan(can.getData());
                dataFromDeviceModel.updateModel();
            }
        }
    }
    public void setUnitIdMapper(Map<Integer, DataFromDeviceModel> canPackage)
    {
        this.canPackage = canPackage;
    }
}
