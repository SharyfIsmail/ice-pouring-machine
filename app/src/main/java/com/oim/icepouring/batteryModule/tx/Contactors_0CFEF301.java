package com.oim.icepouring.batteryModule.tx;

import com.oim.icepouring.can.candata.DataFromDevice;
import com.oim.icepouring.util.Parser;

import java.util.HashMap;
import java.util.Map;

public class Contactors_0CFEF301 implements DataFromDevice {
    private byte prechargeState ;
    private byte plusState ;
    private byte groundState ;
    private short contactorState;
    private ContactorError contactorError = new ContactorError();

    public short getContactorState() {
        return contactorState;
    }

    public void setContactorState(short contactorState) {
        this.contactorState = contactorState;
    }

    public byte getPrechargeState() {
        return prechargeState;
    }

    public void setPrechargeState(byte prechargeState) {
        this.prechargeState = prechargeState;
    }

    public byte getPlusState() {
        return plusState;
    }

    public void setPlusState(byte plusState) {
        this.plusState = plusState;
    }

    public byte getGroundState() {
        return groundState;
    }

    public void setGroundState(byte groundState) {
        this.groundState = groundState;
    }

    public ContactorError getContactorError (){return contactorError ; }

    @Override
    public void parseDataFromCan(byte[] data) {
        if(data.length == 8) {
            prechargeState  = (byte) Parser.LittleIndianParser.uint_8ToShort(data[1]);
            plusState = (byte) Parser.LittleIndianParser.uint_8ToShort(data[2]);
            groundState = (byte) Parser.LittleIndianParser.uint_8ToShort(data[3]);

            byte[] partArray = new byte[2];
            System.arraycopy(data, 4, partArray, 0, partArray.length);
            contactorState = (short) Parser.LittleIndianParser.uint_16ToInt(partArray);
            contactorError.setGroundState(contactorState);
            contactorError.setPlusState(contactorState);
            contactorError.setPrechargeState(contactorState);
            contactorError.setSensorState(contactorState);
        }
    }
    public static class ContactorError
    {
        private  Map<Integer, String> precharge = new HashMap<>();
        private  Map<Integer, String> plus = new HashMap<>();
        private  Map<Integer, String> ground = new HashMap<>();
        private  Map<Integer, String> sensor = new HashMap<>();

        public Map<Integer, String> getPrecharge() {
            return precharge;
        }

        public Map<Integer, String> getPlus() {
            return plus;
        }

        public Map<Integer, String> getGround() {
            return ground;
        }

        public Map<Integer, String> getSensor() {
            return sensor;
        }

        public  void setPrechargeState(short data)
       {

           if((data & 1) == 1)
           {
               precharge.put( 1, "Precharge Open Load");
           }
           else
               precharge.remove( 1);

          if((data & 2) == 2)
           {
               precharge.put(2, "Precharge Welding");
           }
          else
              precharge.remove(2);

            if((data & 4) == 4)
            {
               precharge.put(4, "Precharge relay feedback broken");
            }
            else
                precharge.remove( 4);

       }
       public  void setPlusState(short data)
       {
           if((data & 8) == 8)
           {
               plus.put(8, "Plus Open Load");
           }
           else
           plus.remove(8);

           if((data & 16) == 16)
           {
               plus.put(16, "Plus Welding");
           }
           else
              plus.remove(16);
           if((data  & 32) == 32)
           {
               plus.put( 32, "Plus relay feedback broken");
           }
           else
            plus.remove(32);
       }
       public  void setGroundState(short data)
       {
           if((data & 64) == 64)
           {
               ground.put( 64, "Ground Open Load");
           }
           else
               ground.remove(64);
           if((data & 128) == 128)
           {
               ground.put(128, "Ground Welding");
           }
           else
               ground.remove(128);
           if((data  & 256) == 256)
           {
               ground.put(256, "Ground relay feedback broken");
           }
           else
               ground.remove(256);
       }
       public  void setSensorState(short data)
       {
           if((data & 512)== 512)
           {
               sensor.put( 512, "External Voltage Sensor Broken");
           }
           else
               sensor.remove(512);
           if((data  & 1024) == 1024)
           {
               sensor.put(1024, "Power Switch Current Sensor Crash");
           }
           else
               sensor.remove(1024);
       }
    }
}
