/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpio.Fehlerbehandlung;

/**
 *
 * @author marian
 */
public class FalscherModusException extends Exception{
    
    public FalscherModusException(String message) {
        super(message);
    }
}
