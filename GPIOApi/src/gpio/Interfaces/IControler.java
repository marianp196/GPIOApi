/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpio.Interfaces;

import gpio.Enums.EModus;
import gpio.Enums.EStatus;

/**
 *
 * @author marian
 */
public interface IControler {
    void register(int pin, EModus modus) throws Exception;
   
    void exec(String befehl) throws Exception;
    void setSate(int pin,EStatus stat)throws Exception;
    void setHigh(int pin) throws Exception;
    void setLow(int pin) throws Exception;    
    
    boolean getState(int pin) throws Exception;
    void setAction(int pin, IAction action) throws Exception;
    
}
