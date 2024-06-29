/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.instrucciones.vectores;

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
public class AsignacionVectorDosDimensiones extends Instruccion{
    private String id;
    private Instruccion posicion1;
    private Instruccion posicion2;
    private Instruccion asignacion;

    public AsignacionVectorDosDimensiones(String id, Instruccion posicion1, Instruccion posicion2, Instruccion asignacion, int linea, int columna) {
        super(new Tipo(TipoDato.VOID), linea, columna);
        this.id = id;
        this.posicion1 = posicion1;
        this.posicion2 = posicion2;
        this.asignacion = asignacion;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var variable = tabla.getVariable(id);
        
        if (variable == null) {
            return new Errores("SEMANTICO", "El vector \" " + this.id+" \" no existe",
                    this.linea, this.columna);
        }
        
        var posicionF = this.posicion1.interpretar(arbol, tabla);
        
        if(posicionF instanceof Errores){
            return posicionF;
        }
        
        if(this.posicion1.tipo.getTipo() != TipoDato.ENTERO){
            return new  Errores("SEMANTICO", "La posicion vector \" " + this.id+" \" no es del tipo de dato entero",
                    this.linea, this.columna);
        }
        
        var posicionC = this.posicion2.interpretar(arbol, tabla);
        
        if(posicionC instanceof Errores){
            return posicionC;
        }
        
        if(this.posicion2.tipo.getTipo() != TipoDato.ENTERO){
            return new  Errores("SEMANTICO", "La posicion vector \" " + this.id+" \" no es del tipo de dato entero",
                    this.linea, this.columna);
        }
        
        
        var nuevoValor = this.asignacion.interpretar(arbol, tabla);
        
        if (nuevoValor instanceof Errores) {
            return nuevoValor;
        }
        
        if (variable.getTipo().getTipo() != this.asignacion.tipo.getTipo()) {
            return new Errores("SEMANTICO", "Tipo de dato erroneo, no se puede asignar el valor de tipo "+this.asignacion.tipo.getTipo()+ " al vector de tipo "+ variable.getTipo().getTipo(),
                    this.linea, this.columna);
        }
        
        //VALIDAR MUTABILIDAD
        
        if(!variable.isMutable()){
            return new Errores("SEMANTICO", "La variable \" "+id+" \" es de tipo const, no se puede asignar el valor",
                    this.linea, this.columna);
        }
        
        var arreglo = variable.getValor();
        
        if(arreglo instanceof Object[][]){
            Object [][] resultado = (Object [][]) arreglo;
            
            if((int)posicionF>=resultado.length){
                return new  Errores("SEMANTICO", "La posicion  \" " + posicionF+" \" es mayor que la longitud del vector",
                    this.linea, this.columna);
            }
            
            if((int)posicionC>=resultado[(int)posicionF].length){
                return new  Errores("SEMANTICO", "La posicion  \" " + posicionC+" \" es mayor que la longitud del vector",
                    this.linea, this.columna);
            }
            
            resultado[(int) posicionF][(int)posicionC] = nuevoValor;
            
            variable.setValor(resultado);
            variable.setLinea(this.linea);
            variable.setColumna(this.columna);
        }else{
            return new Errores("SEMANTICO", "La variable \" " + this.id+" \" no es un vector",
                    this.linea, this.columna);
        }
        
        
        return null;
        
    }
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        return "";
    }
    

    
}
