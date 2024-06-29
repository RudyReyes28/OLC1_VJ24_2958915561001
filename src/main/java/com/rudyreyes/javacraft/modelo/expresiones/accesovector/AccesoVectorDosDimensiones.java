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
public class AccesoVectorDosDimensiones extends Instruccion{
    private String identificador;
    private Instruccion posicion1;
    private Instruccion posicion2;

    public AccesoVectorDosDimensiones(String identificador, Instruccion posicion1, Instruccion posicion2, int linea, int columna) {
        super(new Tipo(TipoDato.VOID), linea, columna);
        this.identificador = identificador;
        this.posicion1 = posicion1;
        this.posicion2 = posicion2;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var vector = tabla.getVariable(identificador);
        
        if (vector == null) {
            return new Errores("SEMANTICO", "El vector \" " + this.identificador+" \" no existe",
                    this.linea, this.columna);
        }
        
        var primeraP = posicion1.interpretar(arbol, tabla);
        if(primeraP instanceof Errores){
            return primeraP;
        }
        
        if(this.posicion1.tipo.getTipo() != TipoDato.ENTERO){
            return new  Errores("SEMANTICO", "La posicion vector \" " + this.identificador+" \" no es del tipo de dato entero",
                    this.linea, this.columna);
        }
        
        
        var segundaP = posicion2.interpretar(arbol, tabla);
        if(segundaP instanceof Errores){
            return segundaP;
        }
        
        if(this.posicion2.tipo.getTipo() != TipoDato.ENTERO){
            return new  Errores("SEMANTICO", "La posicion vector \" " + this.identificador+" \" no es del tipo de dato entero",
                    this.linea, this.columna);
        }
        
        var valor = vector.getValor();
        
        if(valor instanceof Object[][]){
            Object [][] resultado = (Object [][]) valor;
            
            if((int)primeraP>=resultado.length){
                return new  Errores("SEMANTICO", "La posicion  \" " + primeraP+" \" es mayor que la longitud del vector",
                    this.linea, this.columna);
            }
            
            if((int)segundaP>=resultado[(int)primeraP].length){
                return new  Errores("SEMANTICO", "La posicion  \" " + segundaP+" \" es mayor que la longitud del vector",
                    this.linea, this.columna);
            }
            this.tipo.setTipo(vector.getTipo().getTipo());
            return resultado[(int)primeraP][(int)segundaP];
        }
        
        return new  Errores("SEMANTICO", "La variable \" " + this.identificador+" \" no es un vector",
                    this.linea, this.columna);

    }

    @Override
    public String generarast(Arbol arbol, String anterior) {
        return "";
    }
    
    
    
    
}
