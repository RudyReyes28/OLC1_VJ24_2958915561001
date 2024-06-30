/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.instrucciones.sentenciasTransferencia;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import com.rudyreyes.javacraft.modelo.simbolo.Arbol;
import com.rudyreyes.javacraft.modelo.simbolo.TablaSimbolos;
import com.rudyreyes.javacraft.modelo.simbolo.Tipo;
import com.rudyreyes.javacraft.modelo.simbolo.TipoDato;

/**
 *
 * @author rudyo
 */
public class SentenciaBreak extends Instruccion {
    
    public SentenciaBreak(int linea, int col) {
        super(new Tipo(TipoDato.VOID), linea, col);
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        return null;
    }
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        
        String bre = "n" + arbol.getContador();
        String dosP = "n" + arbol.getContador();
        
        String resultado = anterior+" ->"+bre+";\n"; 
         resultado += anterior+" ->"+dosP+";\n"; 
        
        resultado += bre + "[label=\"break\"];\n";
        resultado += dosP + "[label=\";\"];\n";
        
        return resultado;
        
    }
    
}
