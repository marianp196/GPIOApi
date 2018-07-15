/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpio;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigital;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinState;
import gpio.Enums.EModus;
import gpio.Enums.EStatus;
import gpio.Fehlerbehandlung.PinGesperrtException;
import gpio.Interfaces.IAction;
import gpio.Interfaces.IControler;
import java.util.ArrayList;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
/**
 * 
 * @author marian
 */
class Controler implements IControler {
    private GpioController internControler;
    
    private PinModeHandler pins;
    private ArrayList<ActionThread> ar_actions;
    
    public Controler()
    {
        internControler = GpioFactory.getInstance();
        pins = new PinModeHandler();
        ar_actions = new ArrayList<>();
    }
    
    @Override
    public void register(int pin, EModus modus) throws Exception  {
        Pi4JPinMap internPin = Pi4JPinMap.getPi4JPin(pin);
        PinMode internPinMode = getInternPinMode(modus);
                
        GpioPinDigital pd;
        
        if(pins.pinDefinedInList(internPin.getInternPin()))
        {
            pd = pins.getPinFromList(internPin);
            
            if(pins.pinGesperrt(internPin) && pd.getMode() !=  getInternPinMode(modus))
                throw new PinGesperrtException(internPin.toString());
            
            pd.setMode(internPinMode);
        }
        else
        {
            pd = createPinInstance(internPin, modus);
            pins.addPin(pd);
        }        
        
    }
    
    @Override
    public void exec(String befehl) throws Exception {
        String[] decoded = befehl.split("=");
        
        if(decoded.length != 2)
            throw new IllegalArgumentException("Falsche Befehlssyntax!");
        
        int pin;
        try
        {
            pin = Integer.valueOf(decoded[0]);
        }catch(Exception e)
        {
            throw new IllegalArgumentException("Pin nicht interpretierbar!");
        }
        
        execBefehlAusfuehren(pin, decoded[1]);
        
    }

    @Override
    public void setSate(int pin, EStatus stat) throws Exception {
        Pi4JPinMap internPin = Pi4JPinMap.getPi4JPin(pin);
        
        if(!pins.pinDefinedInList(internPin.getInternPin()))
        {
            register(pin, EModus.Out);           
        }        
        
        GpioPinDigitalOutput digitalOutput = pins.getDigitalOutputPin(internPin);
        
        if(stat == EStatus.High)
            digitalOutput.setState(PinState.HIGH);
        else if(stat == EStatus.Low)
            digitalOutput.setState(PinState.LOW);
        else
            throw new NotImplementedException();
    }
    
    @Override
    public boolean getState(int pin) throws Exception{
        Pi4JPinMap internPinNumber = Pi4JPinMap.getPi4JPin(pin);
                
        GpioPinDigitalInput internPin = pins.getDigitalInputPin(internPinNumber);
        
        return internPin.isHigh();
    }

    @Override
    public void setAction(int pin, IAction action) throws Exception {
        Pi4JPinMap internPin = Pi4JPinMap.getPi4JPin(pin);
        
        if(action == null)
        {
            removeAction(internPin);
            return;
        }          
        //zur prüfung
        pins.getDigitalInputPin(internPin);
        
        pins.sperrPin(internPin);
        
        ActionThread a = new ActionThread(internPin, action);
        a.start();
        
        ar_actions.add(a);
    }

    @Override
    public void setHigh(int pin) throws Exception {
        this.setSate(pin, EStatus.High);
    }
        

    @Override
    public void setLow(int pin) throws Exception{
        this.setSate(pin, EStatus.Low);
    }
    
    
    private void removeAction(Pi4JPinMap pin)
    {
        System.out.println("hi");
        ActionThread result =null;
        for(ActionThread at : ar_actions)
        {
            if(at.getPin().equals(pin))
            {
                result = at;
            }
        }
        
        if(result != null)
        {
            result.stop();
            ar_actions.remove(result);
            pins.entsperrPin(pin);
        }
        
        
    }
    
    private GpioPinDigital createPinInstance(Pi4JPinMap pin, EModus mode)
    {
        if(mode == EModus.Out)
            return internControler.provisionDigitalOutputPin(pin.getInternPin());
        else if(mode == EModus.In)
            return internControler.provisionDigitalInputPin(pin.getInternPin());
        else
            throw new NotImplementedException();
    }
    
    private PinMode getInternPinMode(EModus mode)
    {
        if(mode == EModus.In)
            return PinMode.DIGITAL_INPUT;
        else
            return PinMode.DIGITAL_OUTPUT;
    }  

    private void execBefehlAusfuehren(int pin, String befehl) throws Exception {
        
        if(befehl.equals("h") || befehl.equals("H"))
            setSate(pin, EStatus.High);
        
        else if(befehl.equals("l") || befehl.equals("L"))
            setSate(pin, EStatus.Low);
        
        else if(befehl.equals("o") || befehl.equals("O"))
            register(pin, EModus.Out);
        
        else if(befehl.equals("i") || befehl.equals("I"))
            register(pin, EModus.In); 
        
        else
            throw new IllegalArgumentException("Opearation nicht implementiert!");
    }
        
}
