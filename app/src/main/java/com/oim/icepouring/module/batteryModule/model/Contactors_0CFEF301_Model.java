package com.oim.icepouring.module.batteryModule.model;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableFloat;

import com.oim.icepouring.module.batteryModule.ErrorChecker;
import com.oim.icepouring.module.batteryModule.tx.Contactors_0CFEF301;
import com.oim.icepouring.can.candata.DataFromDevice;
import com.oim.icepouring.can.candata.DataFromDeviceModel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Contactors_0CFEF301_Model implements DataFromDeviceModel, ErrorChecker {

    private Contactors_0CFEF301 contactors_0CFEF301;
    private ObservableBoolean isErrorPresented ;
    private List<String> errors;

    public ObservableBoolean getIsErrorPresented() {
        return isErrorPresented;
    }

    public void setIsErrorPresented(ObservableBoolean isErrorPresented) {
        this.isErrorPresented = isErrorPresented;
    }

    public Contactors_0CFEF301_Model()
    {
        contactors_0CFEF301 = new Contactors_0CFEF301();
        errors = new CopyOnWriteArrayList<>();
        isErrorPresented = new ObservableBoolean(false);
    }

    @Override
    public void updateModel() {
//      setGroundState(contactors_0CFEF301.getContactorState());
//      setPlusState(contactors_0CFEF301.getContactorState());
//      setPrechargeState(contactors_0CFEF301.getContactorState());
//      setSensorState(contactors_0CFEF301.getContactorState());
      registerError(contactors_0CFEF301.getContactorState());
      isErrorPresented.set(checkErrorExistence());
    }

    @Override
    public void registerError(Object object) {
        short data = (Short) object;
        if((data & 1) == 1)
        {
            if(!errors.contains("Precharge OpenLoad"))
               errors.add("Precharge OpenLoad");
        }
        else
            errors.remove("Precharge OpenLoad");
        if((data & 2) == 2)
        {
            if(!errors.contains("Precharge OpenLoad"))
               errors.add("Precharge OpenLoad");
        }
        else
            errors.remove("Precharge Welding");

        if((data & 4) == 4)
        {
            if(!errors.contains("Precharge feedback broken"))
                errors.add("Precharge feedback broken");
        }
        else
            errors.remove( "Precharge feedback broken");
        if((data & 8) == 8)
        {
            if(!errors.contains("Plus Open Load"))
                errors.add( "Plus Open Load");
        }
        else
            errors.remove("Plus Open Load");

        if((data & 16) == 16)
        {
            if(!errors.contains("Plus Welding"))
                errors.add("Plus Welding");
        }
        else
            errors.remove("Plus Welding");
        if((data  & 32) == 32)
        {
            if(!errors.contains("Plus relay feedback is broken"))
                errors.add("Plus relay feedback is broken");
        }
        else
            errors.remove("Plus relay feedback is broken");
        if((data & 64) == 64)
        {
            if(!errors.contains("Ground relay Open Load"))
                errors.add( "Ground relay Open Load");
        }
        else
            errors.remove("Ground relay Open Load");
        if((data & 128) == 128)
        {
            if(!errors.contains("Ground relay Welding"))
                errors.add("Ground relay Welding");
        }
        else
            errors.remove("Ground relay Welding");
        if((data  & 256) == 256)
        {
            if(!errors.contains("Ground relay feedback is broken"))
                errors.add("Ground relay feedback is broken");
        }
        else
            errors.remove("Ground relay feedback is broken");
        if((data & 512)== 512)
        {
            if(!errors.contains("Voltage Sensor is Broken"))
                errors.add("Voltage Sensor is Broken");
        }
        else
            errors.remove("Voltage Sensor is Broken");
        if((data  & 1024) == 1024)
        {
            if(!errors.contains("Current Sensor is Broken"))
                errors.add("Current Sensor is Broken");
        }
        else
            errors.remove("Current Sensor is Broken");
    }

    public boolean checkErrorExistence()
    {
        return errors.isEmpty();
    }

    @Override
    public List <String>getErrorList() {
        return errors;
    }

    @Override
    public DataFromDevice getDataFromDevice() {
        return contactors_0CFEF301;
    }

//    private void setPrechargeState(short data)
//    {
//        if((data & 1) == 1)
//        {
//            errors.add("Precharge OpenLoad");
//        }
//        else
//            errors.remove("Precharge OpenLoad");
//        if((data & 2) == 2)
//        {
//            errors.add("Precharge Welding");
//        }
//        else
//            errors.remove("Precharge Welding");
//
//        if((data & 4) == 4)
//        {
//            errors.add("Precharge feedback broken");
//        }
//        else
//            errors.remove( "Precharge feedback broken");
//    }
//
//    private  void setPlusState(short data)
//    {
//        if((data & 8) == 8)
//        {
//            errors.add( "Plus Open Load");
//        }
//        else
//            errors.remove("Plus Open Load");
//
//        if((data & 16) == 16)
//        {
//            errors.add("Plus Welding");
//        }
//        else
//            errors.remove("Plus Welding");
//        if((data  & 32) == 32)
//        {
//            errors.add("Plus relay feedback is broken");
//        }
//        else
//            errors.remove("Plus relay feedback is broken");
//    }
//    private  void setGroundState(short data)
//    {
//        if((data & 64) == 64)
//        {
//            errors.add( "Ground relay Open Load");
//        }
//        else
//            errors.remove("Ground relay Open Load");
//        if((data & 128) == 128)
//        {
//            errors.add("Ground relay Welding");
//        }
//        else
//            errors.remove("Ground relay Welding");
//        if((data  & 256) == 256)
//        {
//            errors.add("Ground relay feedback is broken");
//        }
//        else
//            errors.remove("Ground relay feedback is broken");
//    }
//    private  void setSensorState(short data)
//    {
//        if((data & 512)== 512)
//        {
//            errors.add("Voltage Sensor is Broken");
//        }
//        else
//            errors.remove("Voltage Sensor is Broken");
//        if((data  & 1024) == 1024)
//        {
//            errors.add("Current Sensor is Broken");
//        }
//        else
//            errors.remove("Current Sensor is Broken");
//    }
}
