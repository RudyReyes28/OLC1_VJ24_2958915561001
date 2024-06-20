/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.expresiones.accesovector;

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
public class AccesoVectorUnaDimension extends Instruccion{
    //<ID> [ <EXPRESION> ]
    private String identificador;
    private Instruccion posicion;

    public AccesoVectorUnaDimension(String identificador, Instruccion posicion, int linea, int columna) {
        super(new Tipo(TipoDato.VOID), linea, columna);
        this.identificador = identificador;
        this.posicion = posicion;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var vector = tabla.getVariable(identificador);
        
        if (vector == null) {
            return new Errores("SEMANTICO", "El vector \" " + this.identificador+" \" no existe",
                    this.linea, this.columna);
        }
        var p = this.posicion.interpretar(arbol, tabla);
        
        if(p instanceof Errores){
            return p;
        }
        
        if(this.posicion.tipo.getTipo() != TipoDato.ENTERO){
            return new  Errores("SEMANTICO", "La posicion vector \" " + this.identificador+" \" no es del tipo de dato entero",
                    this.linea, this.columna);
        }
        
        var valor = vector.getValor();
        
        if(valor instanceof Object[]){
            Object [] resultado = (Object []) valor;
            
            if((int)p>=resultado.length){
                return new  Errores("SEMANTICO", "La posicion  \" " + p+" \" es mayor que la longitud del vector",
                    this.linea, this.columna);
            }
            this.tipo.setTipo(vector.getTipo().getTipo());
            return resultado[(int)p];
        }
        
        
        return null;
    }
    
    
    
    
    
}
