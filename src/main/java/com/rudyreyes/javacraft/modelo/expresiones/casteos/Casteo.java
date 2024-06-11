/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.expresiones.casteos;

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
public class Casteo extends Instruccion{
    
    private Instruccion operacion;
    private TipoCasteo castear;

    public Casteo(Instruccion operacion, TipoCasteo castear, int linea, int columna) {
        super(new Tipo(TipoDato.ENTERO), linea, columna);
        this.operacion = operacion;
        this.castear = castear;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        Object op = null;
        
        op = this.operacion.interpretar(arbol, tabla);
        if (op instanceof Errores) {
            return op;
        }
        
        return switch (castear) {
            case ENTERO ->
                this.castearEntero(op);
            case DECIMAL ->
                this.castearDecimal(op);
                
            case CARACTER ->
                this.castearCaracter(op);
            default ->
                new Errores("SEMANTICO", "Tipo de Casteo invalido", this.linea, this.columna);
        };
    }
    
    
    public Object castearEntero(Object op){
        
        var tipo1 = this.operacion.tipo.getTipo();
        
        switch (tipo1) {
            
            case DECIMAL ->{
                this.tipo.setTipo(TipoDato.ENTERO);
                return (int)((double) op);
            }
            
            case CARACTER ->{
                this.tipo.setTipo(TipoDato.ENTERO);
                return (int) op.toString().charAt(0);
            }
            default -> {
                return new Errores("SEMANTICO", "Tipo de dato no soportado para el casteo a ENTERO: " + tipo1, this.linea, this.columna);
            }
        }
        
    }
    
    public Object castearDecimal(Object op){
        var tipo1 = this.operacion.tipo.getTipo();
        
        switch (tipo1) {
            
            case ENTERO ->{
                this.tipo.setTipo(TipoDato.DECIMAL);
                return Double.valueOf(op.toString());
            }
            
            case CARACTER ->{
                this.tipo.setTipo(TipoDato.DECIMAL);
                return (double) ((int) op.toString().charAt(0));
            }
            default -> {
                return new Errores("SEMANTICO", "Tipo de dato no soportado para el casteo a DECIMAL: " + tipo1, this.linea, this.columna);
            }
        }
    }
    
    public Object castearCaracter(Object op){
        var tipo1 = this.operacion.tipo.getTipo();
        
        switch (tipo1) {
            
            case ENTERO ->{
                this.tipo.setTipo(TipoDato.CARACTER);
                return (char)((int)op);
            }
            
            default -> {
                return new Errores("SEMANTICO", "Tipo de dato no soportado para el casteo a CARACTER: " + tipo1, this.linea, this.columna);
            }
        }
    }
    
}
