/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.instrucciones.sentenciaControl;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import com.rudyreyes.javacraft.modelo.errores.Errores;
import com.rudyreyes.javacraft.modelo.simbolo.Arbol;
import com.rudyreyes.javacraft.modelo.simbolo.TablaSimbolos;
import com.rudyreyes.javacraft.modelo.simbolo.Tipo;
import com.rudyreyes.javacraft.modelo.simbolo.TipoDato;
import java.util.LinkedList;

/**
 *
 * @author rudyo
 */
public class SentenciaElseIF extends Instruccion{
    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;
    private Instruccion instruccionesElseIf;

    public SentenciaElseIF(Instruccion condicion, LinkedList<Instruccion> instrucciones, Instruccion instruccionesElseIf, int linea, int columna) {
        super(new Tipo(TipoDato.VOID), linea, columna);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
        this.instruccionesElseIf = instruccionesElseIf;
    }

    

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var cond = this.condicion.interpretar(arbol, tabla);
        if (cond instanceof Errores) {
            return cond;
        }

        // ver que cond sea booleano
        if (this.condicion.tipo.getTipo() != TipoDato.BOOLEANO) {
            return new Errores("SEMANTICO", "Expresion invalida",
                    this.linea, this.columna);
        }
        
        var newTabla = new TablaSimbolos(tabla);
        if ((boolean) cond) {
            for (var i : this.instrucciones) {
                var resultado = i.interpretar(arbol, newTabla);
                /*
                    Manejo de errores
                */
            }
        }else{
            
                var resultado = instruccionesElseIf.interpretar(arbol, newTabla);
                /*
                    Manejo de errores
                */
            
        }
        
        return null;
    }

    

    
    
    
    
}
