/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.simbolo;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import java.util.LinkedList;

/**
 *
 * @author rudyo
 */
public class Arbol {
    
    private LinkedList<Instruccion> instrucciones;
    private String consola;
    private TablaSimbolos tablaGlobal;
    private LinkedList<Error> errores;

    public Arbol(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
        this.consola = "";
        this.tablaGlobal = new TablaSimbolos();
        this.errores = new LinkedList<>();
        
    }

    public LinkedList<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public String getConsola() {
        return consola;
    }

    public void setConsola(String consola) {
        this.consola = consola;
    }

    public TablaSimbolos getTablaGlobal() {
        return tablaGlobal;
    }

    public void setTablaGlobal(TablaSimbolos tablaGlobal) {
        this.tablaGlobal = tablaGlobal;
    }

    public LinkedList<Error> getErrores() {
        return errores;
    }

    public void setErrores(LinkedList<Error> errores) {
        this.errores = errores;
    }
    
    
    public void Println(String valor){
        this.consola += valor +"\n";
    }
    
    
    
    
    
}
