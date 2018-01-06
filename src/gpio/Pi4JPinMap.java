/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpio;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import gpio.Fehlerbehandlung.FalscherModusException;
import gpio.Fehlerbehandlung.PinExistiertNichtException;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author marian
 */
class Pi4JPinMap {

    //----------------Static-Bereich--------------------------------------------
    private static ArrayList<Pi4JPinMap> ar_mapping = null;
    
    public static Pi4JPinMap getPi4JPin(int pin) throws Exception
    {
        ArrayList<Pi4JPinMap> ar = getMapArrayList();
        Pi4JPinMap result = null;
        
        for(Pi4JPinMap p : ar)
        {
            if(p.isPin(pin))
                result = p;
        }
        
        if(result == null)
            throw new PinExistiertNichtException("Pin existiert nicht");
        
        return result;
    }
    
    private static ArrayList<Pi4JPinMap> getMapArrayList()
    {
        if(ar_mapping != null)
            return ar_mapping;
        
        ar_mapping = new ArrayList<Pi4JPinMap>();
        
        /*
        Nicht wirklich schön jund clean, wie dieser Kommentar
        */        
        ar_mapping.add(new Pi4JPinMap(3, RaspiPin.GPIO_08));
        ar_mapping.add(new Pi4JPinMap(5, RaspiPin.GPIO_09));
        ar_mapping.add(new Pi4JPinMap(7, RaspiPin.GPIO_07));
        ar_mapping.add(new Pi4JPinMap(8, RaspiPin.GPIO_15));
        ar_mapping.add(new Pi4JPinMap(10, RaspiPin.GPIO_16));
        ar_mapping.add(new Pi4JPinMap(11, RaspiPin.GPIO_00));
        ar_mapping.add(new Pi4JPinMap(13, RaspiPin.GPIO_01));
        ar_mapping.add(new Pi4JPinMap(14, RaspiPin.GPIO_02));
        ar_mapping.add(new Pi4JPinMap(15, RaspiPin.GPIO_03));
        ar_mapping.add(new Pi4JPinMap(16, RaspiPin.GPIO_04));
        ar_mapping.add(new Pi4JPinMap(18, RaspiPin.GPIO_05));
        ar_mapping.add(new Pi4JPinMap(19, RaspiPin.GPIO_12));
        ar_mapping.add(new Pi4JPinMap(21, RaspiPin.GPIO_13));
        ar_mapping.add(new Pi4JPinMap(22, RaspiPin.GPIO_06));
        ar_mapping.add(new Pi4JPinMap(23, RaspiPin.GPIO_14));
        ar_mapping.add(new Pi4JPinMap(24, RaspiPin.GPIO_10));
        ar_mapping.add(new Pi4JPinMap(26, RaspiPin.GPIO_11));
        ar_mapping.add(new Pi4JPinMap(27, RaspiPin.GPIO_30));
        ar_mapping.add(new Pi4JPinMap(28, RaspiPin.GPIO_31));
        ar_mapping.add(new Pi4JPinMap(29, RaspiPin.GPIO_21));
        ar_mapping.add(new Pi4JPinMap(31, RaspiPin.GPIO_22));
        ar_mapping.add(new Pi4JPinMap(32, RaspiPin.GPIO_26));
        ar_mapping.add(new Pi4JPinMap(33, RaspiPin.GPIO_23));
        ar_mapping.add(new Pi4JPinMap(35, RaspiPin.GPIO_24));
        ar_mapping.add(new Pi4JPinMap(36, RaspiPin.GPIO_27));
        ar_mapping.add(new Pi4JPinMap(37, RaspiPin.GPIO_25));
        ar_mapping.add(new Pi4JPinMap(38, RaspiPin.GPIO_28));
        ar_mapping.add(new Pi4JPinMap(40, RaspiPin.GPIO_29));
          
        return ar_mapping;        
    }
    
    //----------------Klassen-def-----------------------------------------------
    private int pin;
    private Pin pifourJ;
    
    public Pi4JPinMap(int internPin, Pin pifourJ)
    {
        this.pin = internPin;
        this.pifourJ = pifourJ;
    }
    
    public boolean isPin(int pin)
    {
        return this.pin == pin;
    }
    
    public Pin getInternPin()
    {
        return pifourJ;   
    }
    
    public int getPin()
    {
        return pin;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pi4JPinMap other = (Pi4JPinMap) obj;
        if (this.pin != other.pin) {
            return false;
        }
        if (!Objects.equals(this.pifourJ, other.pifourJ)) {
            return false;
        }
        return true;
    }
}
