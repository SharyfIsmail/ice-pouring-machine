package com.oim.icepouring.usb;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;

import com.oim.icepouring.usb.usbDriver.UsbSerialDriver;
import com.oim.icepouring.usb.usbDriver.UsbSerialPort;
import com.oim.icepouring.usb.usbDriver.UsbSerialProber;

import java.io.IOException;

public class UsbConnector implements UsbService {
    final static int vendorId = 1027;
    private UsbDevice device = null;
    private UsbSerialPort usbSerialPort;
    private UsbDeviceConnection usbConnection;
    private AppCompatActivity activity;

    public UsbConnector(AppCompatActivity activity) {
        this.activity = activity;
    }

    public UsbSerialPort getUsbSerialPort() {
        return usbSerialPort;
    }

    public UsbDevice getDevice() {
        return device;
    }

    public UsbDeviceConnection getUsbConnection()
    {
        return usbConnection;
    }
    @Override
    public void connect() throws Exception {
        UsbManager mUsbManger = (UsbManager) activity.getSystemService(Context.USB_SERVICE);
        for (UsbDevice usbDevice : mUsbManger.getDeviceList().values()) {
            if (usbDevice.getVendorId() == vendorId)
                device = usbDevice;
        }
        if (device == null)
            throw new Exception("Failed to connect, device is not found");
        UsbSerialDriver driver = UsbSerialProber.getDefaultProber().probeDevice(device);
        if (driver == null)
            throw new Exception("Failed to connect, not match for driver");
        usbSerialPort = driver.getPorts().get(0);
        usbConnection = mUsbManger.openDevice(driver.getDevice());
        if (usbConnection == null)
            throw new Exception("Couldn't open the connection");
        usbSerialPort.open(usbConnection);
        usbSerialPort.setParameters(460800, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
    }

    public int read(byte[] receivedData) throws IOException {
        return usbSerialPort.read(receivedData, 100);

    }

    @Override
    public void write(byte[] receivedData) throws IOException {
        throw new IOException("Method is not supported yet");
    }

    @Override
    public boolean isConnected() {
        return usbSerialPort.isOpen();
    }

    @Override
    public void closeConnection() throws IOException {
        usbSerialPort.close();

    }
}
