package com.oim.icepouring.usb;

import java.io.IOException;

public interface UsbService
{
    public void connect() throws Exception;
    public int read(byte[] receivedData)  throws IOException;
    public void write(byte[] receivedData) throws IOException;
    public boolean isConnected();
    public void closeConnection()throws IOException;
}
