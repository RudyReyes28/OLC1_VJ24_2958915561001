/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.expresiones.aritmeticas;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import com.rudyreyes.javacraft.modelo.errores.Errores;
import com.rudyreyes.javacraft.modelo.simbolo.Arbol;
import com.rudyreyes.javacraft.modelo.simbolo.TablaSimbolos;
import com.rudyreyes.javacraft.modelo.simbolo.Tipo;
import com.rudyreyes.javacraft.modelo.simbolo.TipoDato;

/**
 *
 * @author rudyo
 */
public class OperacionPotencia extends Instruccion{
    private Instruccion operando1;
    private Instruccion operando2;
    
    public OperacionPotencia(Instruccion operando1, Instruccion operando2, int linea, int columna) {
        super(new Tipo(TipoDato.ENTERO), linea, columna);
        this.operando1 = operando1;
        this.operando2 = operando2;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        Object opIzq = null, opDer = null;
        
        opIzq = this.operando1.interpretar(arbol, tabla);
        if (opIzq instanceof Errores) {
            return opIzq;
        }
        opDer = this.operando2.interpretar(arbol, tabla);
        if (opDer instanceof Errores) {
            return opDer;
        }
        
        return potencia(opIzq, opDer);

    }
    
    
    public Object potencia(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        
        
        switch (tipo1) {
            case ENTERO -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(TipoDato.ENTERO);
                        return (int) Math.pow((int) op1, (int) op2); 
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(TipoDato.DECIMAL);
                        return (double) Math.pow((int) op1, (double) op2); 
                    }
                    
                    
                    default -> {
                        return new Errores("SEMANTICO", "Potencia erronea: la operacion "+op1.toString()+" no se puede potenciar a la "+op2.toString(), this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(TipoDato.DECIMAL);
                        return (double) Math.pow((double) op1, (int) op2); 
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(TipoDato.DECIMAL);
                        return (double) Math.pow((double) op1, (double) op2); 
                    }
                    
                    
                    default -> {
                        return new Errores("SEMANTICO", "Potencia erronea: la operacion "+op1.toString()+" no se puede potenciar a la "+op2.toString(), this.linea, this.columna);
                    }
                }
            }
            
            default -> {
                return new Errores("SEMANTICO", "Potencia erronea", this.linea, this.columna);

            }
        }
    } 
}
