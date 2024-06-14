/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.instrucciones.sentenciaControl;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import java.util.LinkedList;



/**
 *
 * @author rudyo
 */
public class CasoMatch {
    Instruccion caso;
    LinkedList<Instruccion> instrucciones;

    public CasoMatch(Instruccion caso, LinkedList<Instruccion> instrucciones) {
        this.caso = caso;
        this.instrucciones = instrucciones;
    }

    
    
    public Instruccion getCaso() {
        return caso;
    }

    public void setCaso(Instruccion caso) {
        this.caso = caso;
    }

    public LinkedList<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    
    
    
}
