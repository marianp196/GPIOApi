/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpio;

import com.pi4j.io.gpio.Pin;
import gpio.Enums.EStatus;
import gpio.Fehlerbehandlung.FalscherModusException;
import gpio.Interfaces.IAction;
import gpio.Interfaces.IControler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marian
 */
class ActionThread extends Thread{
    private Pi4JPinMap pin;
    private IAction action;
    private IControler controler;
    
    public Pi4JPinMap getPin() {
        return pin;
    }

    public IControler getControler() {
        return controler;
    }  
    
    public ActionThread(Pi4JPinMap pin, IAction action)
    {
        if(action == null)
            throw new NullPointerException("action");
        if(pin == null)
            throw new NullPointerException("pin");
        
        this.controler = ControlerFactory.getInstance();
        this.action = action;
        this.pin = pin;
    }
    
    
    @Override
    public void run()
    {
        try {
            System.out.println(pin.getPin());
            boolean status = controler.getState(pin.getPin());            
            boolean statusAlt = status;
        
            while(true)
            {
                status = controler.getState(pin.getPin());                
                if(status != statusAlt)
                    action.excecute(pin.getPin() ,getStatus(status));
                statusAlt = status;
            }        
        } catch (Exception ex) {
            Logger.getLogger(ActionThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private EStatus getStatus(boolean status)
    {
        if(status)
            return EStatus.High;
        else
            return EStatus.Low;
    }
}
