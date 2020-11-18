package com.oim.icepouring.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.oim.icepouring.R;
import com.oim.icepouring.usb.UsbConnector;

public class MainActivity extends AppCompatActivity {

    private UsbConnector usbConnector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usbConnector = new UsbConnector(this);
        try {
            usbConnector.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected  void onPause()
    {
    super.onPause();
    }
}