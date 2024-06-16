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
public class SentenciaMatch extends Instruccion{
    private Instruccion condicion;
    private boolean ejecutarDefault = true;
    private LinkedList<CasoMatch> casos;
    private LinkedList<Instruccion> casoDefault;

    public SentenciaMatch(Instruccion condicion, LinkedList<CasoMatch> casos, LinkedList<Instruccion> casoDefault, int linea, int columna) {
        super(new Tipo(TipoDato.VOID), linea, columna);
        this.condicion = condicion;
        this.casos = casos;
        this.casoDefault = casoDefault;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        ejecutarDefault = true;
        var cond = this.condicion.interpretar(arbol, tabla);
        if (cond instanceof Errores) {
            return cond;
        }

        
        
        var newTabla = new TablaSimbolos(tabla);
        newTabla.setNombre(tabla.getNombre()+"-match");
        arbol.agregarTablaEntorno(newTabla);
        if (this.casos != null) {
            for (CasoMatch c : this.casos) {
                var condCaso = c.getCaso().interpretar(arbol, tabla);
                if (this.condicion.tipo.getTipo() == c.getCaso().tipo.getTipo()) {
                    if (cond.equals(condCaso)) {
                        for (var i : c.getInstrucciones()) {
                            var resultado = i.interpretar(arbol, newTabla);
                            
                            if (resultado instanceof Errores) {
                                return resultado;
                            }
                        }
                        this.ejecutarDefault = false;
                        break;
                    }
                }
            }
        } else {
            if (casoDefault != null) {
                for (var i : casoDefault) {
                    var resultado = i.interpretar(arbol, newTabla);
                    if (resultado instanceof Errores) {
                            return resultado;
                    }
                }
                this.ejecutarDefault = false;
            }
        }
        
        if (ejecutarDefault) {
            if (casoDefault != null) {
                for (var i : casoDefault) {
                    var resultado = i.interpretar(arbol, newTabla);
                    if (resultado instanceof Errores) {
                            return resultado;
                    }
                }
            }
        }

        
        
        
        return null;
    }
    
    
    
}
