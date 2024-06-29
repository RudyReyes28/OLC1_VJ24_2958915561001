/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.expresiones.funcionesNativas;

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
public class FuncionRound extends Instruccion{
    private Instruccion expresion;

    public FuncionRound(Instruccion expresion, int linea, int columna) {
        super(new Tipo(TipoDato.ENTERO), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
         var resultado = this.expresion.interpretar(arbol, tabla);
         
         if(resultado instanceof Errores){
             return resultado;
         }
         
         if(this.expresion.tipo.getTipo() == TipoDato.ENTERO){
             return resultado;
         
         }
         
         if(this.expresion.tipo.getTipo() == TipoDato.DECIMAL){
             return (int) Math.round((double) resultado);
         }
         
         return new Errores("SEMANTICO", "El tipo de dato no es entero ni decimal para la funcion round", linea, columna);
    }
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        return "";
    }
    
}
