/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.expresiones.logicas;

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
public class OperadorOr extends Instruccion{
    private Instruccion operando1;
    private Instruccion operando2;
    
    public OperadorOr(Instruccion operando1, Instruccion operando2, int linea, int columna) {
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
        
        
        return operacionOr(opIzq, opDer);
    }
    
    public Object operacionOr(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            
            case BOOLEANO ->{
                switch (tipo2) {
                    
                    case BOOLEANO -> {
                        this.tipo.setTipo(TipoDato.BOOLEANO);
                        boolean valor1 = op1 instanceof Boolean ? (Boolean) op1 : Boolean.valueOf(op1.toString().toLowerCase());
                        boolean valor2 = op2 instanceof Boolean ? (Boolean) op2 : Boolean.valueOf(op2.toString().toLowerCase());
                        return valor1 || valor2;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Expresion Logica erronea: la operacion "+op1.toString()+" no se puede operar(OR) con la operacion "+op2.toString(), this.linea, this.columna);
                    }
                }
                
                
            }
            default -> {
                return new Errores("SEMANTICO", "Tipo de dato no soportado para la operacion logica (OR): " + tipo1, this.linea, this.columna);
            }
        }
    
    
    }
}
