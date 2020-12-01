package com.oim.icepouring.batteryModul.tx;

import com.oim.icepouring.can.candata.DataFromDevice;
import com.oim.icepouring.util.Parser;

import java.util.HashMap;
import java.util.Map;

public class Contactors_0CFEF301 implements DataFromDevice {
    private byte prechargeState ;
    private byte plusState ;
    private byte groundState ;
    private short contactorState;

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

    @Override
    public void parseDataFromCan(byte[] data) {
        if(data.length == 8) {
            prechargeState  = (byte) Parser.LittleIndianParser.uint_8ToShort(data[1]);
            plusState = (byte) Parser.LittleIndianParser.uint_8ToShort(data[2]);
            groundState = (byte) Parser.LittleIndianParser.uint_8ToShort(data[3]);

            byte[] partArray = new byte[2];
            System.arraycopy(data, 4, partArray, 0, partArray.length);
            contactorState = (short) Parser.LittleIndianParser.uint_16ToInt(partArray);
            ContactorError.setGroundState(contactorState);
            ContactorError.setPlusState(contactorState);
            ContactorError.setPrechargeState(contactorState);
            ContactorError.setSensorState(contactorState);
        }
    }
    public static class ContactorError
    {
        public static Map<Integer, Boolean> precharge = new HashMap<>();
//        static
//        {
//            precharge.put(1, false);
//            precharge.put(2, false);
//            precharge.put(4, false);
//        }

        public static Map<Integer, Boolean> plus = new HashMap<>();
//        static
//        {
//            plus.put(8, false);
//            plus.put(16, false);
//            plus.put(32, false);
//        }
        public static Map<Integer, Boolean> ground = new HashMap<>();
//        static
//        {
//            ground.put(64, false);
//            ground.put(128, false);
//            ground.put(256, false);
//        }

        public static Map<Integer, Boolean> sensor = new HashMap<>();
//        static
//        {
//            sensor.put(512, false);
//            sensor.put(1024, false);
//
//        }
       public static void setPrechargeState(short data)
       {
           if(precharge.containsKey(data & 1))
           {
               precharge.put(data & 1, true);
           }
           else
               precharge.put(1, false);

          if(precharge.containsKey(data & 2))
           {
               precharge.put(2, true);
           }
          else
              precharge.put(2, false);

            if(precharge.containsKey(data & 4))
            {
               precharge.put(4, true);
            }
            else
                precharge.put( 4, false);

       }
       public static void setPlusState(short data)
       {
           if(plus.containsKey(data & 8))
           {
               plus.put(8, true);
           }
           else
               plus.put( 8, false);

           if(plus.containsKey(data & 16))
           {
               plus.put(16, true);
           }
           else
               plus.put(16, false);
           if(plus.containsKey(data  & 32))
           {
               plus.put( 32, true);
           }
           else
               plus.put(32, false);
       }
       public static void setGroundState(short data)
       {
           if(ground.containsKey(data & 64))
           {
               ground.put( 64, true);
           }
           else
               ground.put( 64, false);

           if(ground.containsKey(data & 128))
           {
               ground.put(128, true);
           }
           else
               ground.put(128, false);
           if(ground.containsKey(data  & 256))
           {
               ground.put(256, true);
           }
           else
               ground.put(256, false);
       }
       public static void setSensorState(short data)
       {
           if(sensor.containsKey(data & 512))
           {
               sensor.put( 512, true);
           }
           else
               sensor.put(512, false);
           if(sensor.containsKey(data  & 1024))
           {
               sensor.put(1024, true);
           }
           else
               sensor.put(1024, false);
       }
    }
}
