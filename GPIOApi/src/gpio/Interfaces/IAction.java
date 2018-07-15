/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpio.Interfaces;

import gpio.Enums.EStatus;

/**
 * Eine Klasse die dieses Interface implementiert kann dem Controller Übergeben werden 
 * und implementierte Methode wird aufgerufen.
 * @author marian
 */
public interface IAction {
    void excecute(int pin,EStatus status);
}
