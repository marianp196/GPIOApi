/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpio;

import gpio.Interfaces.IControler;
import java.util.ArrayList;

/**
 *
 * @author marian
 */
public class ControlerFactory 
{
    private static IControler controler = null;
    
    public static IControler getInstance()
    {       
        if(controler == null)
            controler = new Controler();
        
        return controler;
    }
   
}
