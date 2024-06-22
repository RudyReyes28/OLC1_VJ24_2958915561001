/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.instrucciones.listas;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import com.rudyreyes.javacraft.modelo.errores.Errores;
import com.rudyreyes.javacraft.modelo.simbolo.Arbol;
import com.rudyreyes.javacraft.modelo.simbolo.TablaSimbolos;
import com.rudyreyes.javacraft.modelo.simbolo.Tipo;
import com.rudyreyes.javacraft.modelo.simbolo.TipoDato;
import java.util.List;

/**
 *
 * @author rudyo
 */
public class AppendLista extends Instruccion {
    
    private String id;
    private Instruccion valor;

    public AppendLista(String id, Instruccion valor, int linea, int columna) {
        super(new Tipo(TipoDato.VOID), linea, columna);
        this.id = id;
        this.valor = valor;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var variable = tabla.getVariable(id);
        
        if (variable == null) {
            return new Errores("SEMANTICO", "La variable \" " + variable+" \" no existe",
                    this.linea, this.columna);
        }
        
        var nuevoValor = this.valor.interpretar(arbol, tabla);
        if (nuevoValor instanceof Errores) {
            return nuevoValor;
        }
        
        if (variable.getTipo().getTipo() != this.valor.tipo.getTipo()) {
            return new Errores("SEMANTICO", "Tipo de dato erroneo, no se puede asignar el valor de tipo "+this.valor.tipo.getTipo()+ " a la variable de tipo "+ variable.getTipo().getTipo(),
                    this.linea, this.columna);
        }
        
        if (variable.getValor() instanceof List) {
            ((List<Object>) variable.getValor()).add(nuevoValor);
        }else{
            return new Errores("SEMANTICO", "La variable \" " + this.id+" \" no es una lista",
                    this.linea, this.columna);
        }
        
        return null;
    }
    
    

    
}
