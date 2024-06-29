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
public class OperacionResta extends Instruccion {
    private Instruccion operando1;
    private Instruccion operando2;
    
     public OperacionResta(Instruccion operando1, Instruccion operando2, int linea, int columna) {
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
        
        return resta(opIzq, opDer);
    }
    
    
    public Object resta(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case ENTERO -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(TipoDato.ENTERO);
                        return (int) op1 - (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(TipoDato.DECIMAL);
                        return (int) op1 - (double) op2;
                    }
                    
                    case CARACTER -> {
                        this.tipo.setTipo(TipoDato.ENTERO);
                        System.out.println("Caracter: "+(int) op2.toString().charAt(0));
                        return (int) op1 - (int) op2.toString().charAt(0);
                    }
                    
                    
                    default -> {
                        return new Errores("SEMANTICO", "Resta erronea: la operacion "+op1.toString()+" no se puede restar con la operacion "+op2.toString(), this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(TipoDato.DECIMAL);
                        return (double) op1 - (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(TipoDato.DECIMAL);
                        return (double) op1 - (double) op2;
                    }
                    
                    case CARACTER -> {
                        this.tipo.setTipo(TipoDato.DECIMAL);
                        System.out.println("Caracter: "+(int) op2.toString().charAt(0));
                        return (double) op1 - (int) op2.toString().charAt(0);
                    }
                    
                    
                    default -> {
                        return new Errores("SEMANTICO", "Resta erronea: la operacion "+op1.toString()+" no se puede restar con la operacion "+op2.toString(), this.linea, this.columna);
                    }
                }
            }
            
            
            case CARACTER ->{
                switch (tipo2) {
                    case ENTERO -> {
                        System.out.println("Caracter: "+(int) op2.toString().charAt(0));
                        this.tipo.setTipo(TipoDato.ENTERO);
                        return (int) op1.toString().charAt(0) - (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(TipoDato.DECIMAL);
                        return (int) op1.toString().charAt(0) - (double) op2;
                    }
                    
                    
                    default -> {
                        return new Errores("SEMANTICO", "Resta erronea: la operacion "+op1.toString()+" no se puede restar con la operacion "+op2.toString(), this.linea, this.columna);
                    }
                }
            }
            
            default -> {
                return new Errores("SEMANTICO", "Suma erronea", this.linea, this.columna);

            }
        }
    }
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        String nodoExp1 = "n" + arbol.getContador();
        String nodoOp = "n" + arbol.getContador();
        String nodoExp2 = "n" + arbol.getContador();

        String resultado = anterior + " -> " + nodoExp1 + ";\n";
        resultado += anterior + " ->" + nodoOp + ";\n";
        resultado += anterior + " ->" + nodoExp2 + ";\n";

        resultado += nodoExp1 + "[label=\"EXP\"];\n";
        resultado += nodoOp + "[label=\"-\"];\n";
        resultado += nodoExp2 + "[label=\"EXP\"];\n";
        resultado += this.operando1.generarast(arbol, nodoExp1);
        resultado += this.operando2.generarast(arbol, nodoExp2);
        return resultado;
    }
}
