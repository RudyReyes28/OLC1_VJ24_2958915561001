/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.instrucciones.sentenciaControl;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import com.rudyreyes.javacraft.modelo.errores.Errores;
import com.rudyreyes.javacraft.modelo.instrucciones.metodos.FuncionReturn;
import com.rudyreyes.javacraft.modelo.instrucciones.sentenciasTransferencia.SentenciaBreak;
import com.rudyreyes.javacraft.modelo.instrucciones.sentenciasTransferencia.SentenciaContinue;
import com.rudyreyes.javacraft.modelo.simbolo.Arbol;
import com.rudyreyes.javacraft.modelo.simbolo.TablaSimbolos;
import com.rudyreyes.javacraft.modelo.simbolo.Tipo;
import com.rudyreyes.javacraft.modelo.simbolo.TipoDato;
import java.util.LinkedList;

/**
 *
 * @author rudyo
 */
public class SentenciaIfElse extends Instruccion{
    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;
    private LinkedList<Instruccion> instruccionesElse;

    public SentenciaIfElse(Instruccion condicion, LinkedList<Instruccion> instrucciones, LinkedList<Instruccion> instruccionesElse, int linea, int columna) {
        super(new Tipo(TipoDato.VOID), linea, columna);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
        this.instruccionesElse = instruccionesElse;
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
        newTabla.setNombre(tabla.getNombre()+"-if:else");
        arbol.agregarTablaEntorno(newTabla);
        if ((boolean) cond) {
            for (var i : this.instrucciones) {
                if (i instanceof SentenciaBreak) {
                    return i;
                }
                
                if (i instanceof SentenciaContinue) {
                    return i;
                }
                if (i instanceof FuncionReturn) {
                    var res = i.interpretar(arbol, newTabla);
                    if (res instanceof Errores) {
                        return res;
                    }
                    return res;
                }
                
                if(i == null){
                    continue;
                }
                
                var resultado = i.interpretar(arbol, newTabla);
                if (resultado instanceof SentenciaBreak) {
                    return resultado;
                }
                
                if (resultado instanceof SentenciaContinue) {
                    return resultado;
                }
                
                if(resultado instanceof FuncionReturn){
                    return resultado;
                }
                
                if (resultado instanceof Errores) {
                    return resultado;
                }
            }
        }else{
            for (var i : this.instruccionesElse) {
                if (i instanceof SentenciaBreak) {
                    return i;
                }
                
                if (i instanceof SentenciaContinue) {
                    return i;
                }
                
                if (i instanceof FuncionReturn) {
                    var res = i.interpretar(arbol, newTabla);
                    if (res instanceof Errores) {
                        return res;
                    }
                    return res;
                }
                
                if(i == null){
                    continue;
                }
                
                var resultado = i.interpretar(arbol, newTabla);
                if (resultado instanceof SentenciaBreak) {
                    return resultado;
                }
                
                if (resultado instanceof SentenciaContinue) {
                    return resultado;
                }
                
                if(resultado instanceof FuncionReturn){
                    return resultado;
                }
                
                if (resultado instanceof Errores) {
                    return resultado;
                }
            }
        }
        
        return null;
    }
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        return "";
    }
}
