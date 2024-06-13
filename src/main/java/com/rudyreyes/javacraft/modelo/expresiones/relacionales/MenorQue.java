/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.expresiones.relacionales;

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
public class MenorQue extends Instruccion{
    
    private Instruccion operando1;
    private Instruccion operando2;
    
    public MenorQue(Instruccion operando1, Instruccion operando2, int linea, int columna) {
        super(new Tipo(TipoDato.BOOLEANO), linea, columna);
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
        
        return operacionMenorQue(opIzq, opDer);
    }
    
    
    public Object operacionMenorQue(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case ENTERO -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(TipoDato.BOOLEANO);
                        return (int) op1 < (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(TipoDato.BOOLEANO);
                        return (int) op1 < (double) op2;
                    }
                    
                    case CARACTER -> {
                        this.tipo.setTipo(TipoDato.BOOLEANO);
                        return (int) op1 < (int) op2.toString().charAt(0);
                    }
                    
                    default -> {
                        return new Errores("SEMANTICO", "Operacion Relacional erronea: la operacion "+op1.toString()+" no se puede comparar(<) con la operacion "+op2.toString(), this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(TipoDato.BOOLEANO);
                        return (double) op1 < (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(TipoDato.BOOLEANO);
                        return (double) op1 < (double) op2;
                    }
                    
                    case CARACTER -> {
                        this.tipo.setTipo(TipoDato.BOOLEANO);
                        return (double) op1 < (int) op2.toString().charAt(0);
                    }
                    
                    default -> {
                        return new Errores("SEMANTICO", "Operacion Relacional erronea: la operacion "+op1.toString()+" no se puede comparar(<) con la operacion "+op2.toString(), this.linea, this.columna);
                    }
                }
            }
            
            case BOOLEANO ->{
                switch (tipo2) {
                    
                    case BOOLEANO -> {
                        this.tipo.setTipo(TipoDato.BOOLEANO);
                        int primerOp = Boolean.compare(Boolean.parseBoolean(op1.toString().toLowerCase()), false);
                        int segundop = Boolean.compare(Boolean.parseBoolean(op2.toString().toLowerCase()), false);
                        return primerOp  < segundop;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Operacion Relacional erronea: la operacion "+op1.toString()+" no se puede comparar(<) con la operacion "+op2.toString(), this.linea, this.columna);
                    }
                }
            }
            
            case CARACTER ->{
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(TipoDato.BOOLEANO);
                        return (int) op1.toString().charAt(0) < (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(TipoDato.BOOLEANO);
                        return (int) op1.toString().charAt(0) < (double) op2;
                    }
                    
                    case CARACTER -> {
                        this.tipo.setTipo(TipoDato.BOOLEANO);
                        return  (int) op1.toString().charAt(0) < (int) op2.toString().charAt(0) ;
                    }
                    
                    default -> {
                        return new Errores("SEMANTICO", "Operacion Relacional erronea: la operacion "+op1.toString()+" no se puede comparar(<) con la operacion "+op2.toString(), this.linea, this.columna);
                    }
                }
            }
            
            case CADENA -> {
                if(tipo2 == TipoDato.CADENA){
                    this.tipo.setTipo(TipoDato.BOOLEANO);
                    return op1.toString().compareTo(op2.toString()) < 0;
                }else{
                     return new Errores("SEMANTICO", "Operacion Relacional erronea: la operacion "+op1.toString()+" no se puede comparar(<) con la operacion "+op2.toString(), this.linea, this.columna);

                }
            }
            default -> {
                return new Errores("SEMANTICO", "Tipo de dato no soportado para la comparación (MENOR QUE): " + tipo1, this.linea, this.columna);
            }
        }
    
    
    }
    
}
