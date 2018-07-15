
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpio;

import com.pi4j.io.gpio.GpioPinDigital;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import gpio.Enums.EModus;
import gpio.Fehlerbehandlung.FalscherModusException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author marian
 */
class PinModeHandler {
    private ArrayList<GpioPinDigital> ar_pins;
    private ArrayList<Pi4JPinMap> ar_sperrliste;
    
    public PinModeHandler()
    {
        ar_pins = new ArrayList<>();
        ar_sperrliste = new ArrayList<>();
    }
       
    
    public void addPin(GpioPinDigital pinDigital)
    {
        if(!pinDefinedInList(pinDigital.getPin()))
            ar_pins.add(pinDigital);
    }
    
    public GpioPinDigital getPinFromList(Pi4JPinMap pin)
    {
        GpioPinDigital result = null;
        
        for(GpioPinDigital p : ar_pins)
        {
            if(p.getPin().equals(pin.getInternPin()))
                result = p;
        }
        
        if(result == null)
        {
            throw new InvalidParameterException("Pin nicht registriert");
        }
        
        return result;
    }
    
    public void sperrPin(Pi4JPinMap pin)
    {
        if(pinDefinedInList(pin.getInternPin()) && !ar_sperrliste.contains(pin))
            ar_sperrliste.add(pin);
    }
    
    public void entsperrPin(Pi4JPinMap pin)
    {
        if(pinDefinedInList(pin.getInternPin()))
            ar_sperrliste.remove(pin);
    }
    
    public boolean pinGesperrt(Pi4JPinMap pin)
    {
        return ar_sperrliste.contains(pin);
    }
    
    public PinMode getMode(Pi4JPinMap pin)
    {
        GpioPinDigital pd = getPinFromList(pin);
        return pd.getMode();
    }
    
    public GpioPinDigitalOutput getDigitalOutputPin(Pi4JPinMap internPin) throws FalscherModusException {
        GpioPinDigital digitalPin = getPinFromList(internPin);
       
        if(digitalPin.getMode() != PinMode.DIGITAL_OUTPUT)
            throw new FalscherModusException("Status nicht auf Output");
        
        GpioPinDigitalOutput digitalOutput = (GpioPinDigitalOutput)digitalPin;
        return digitalOutput;
    }
    
    public GpioPinDigitalInput getDigitalInputPin(Pi4JPinMap internPin) throws FalscherModusException {
        GpioPinDigital digitalPin = getPinFromList(internPin);
    
        if(digitalPin.getMode() != PinMode.DIGITAL_INPUT)
            throw new FalscherModusException("Status nicht auf Input");
        
        GpioPinDigitalInput digitalInput = (GpioPinDigitalInput)digitalPin;
        return digitalInput;
    }
    
    public boolean pinDefinedInList(Pin pin)
    {
        GpioPinDigital result = null;
        
        for(GpioPinDigital p : ar_pins)
        {
            if(p.getPin() == pin)
                result = p;
        }
        
        return result != null; 
    }
  
}
