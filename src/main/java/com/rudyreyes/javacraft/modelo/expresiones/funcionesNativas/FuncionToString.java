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
public class FuncionToString extends  Instruccion{
    private Instruccion expresion;

    public FuncionToString(Instruccion expresion, int linea, int columna) {
        super(new Tipo(TipoDato.CADENA), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        Object valor = null;
        
        valor = this.expresion.interpretar(arbol, tabla);
        
        if(valor instanceof Errores){
            return valor;
        }
        
        if(this.expresion.tipo.getTipo() != TipoDato.VOID){
            return valor.toString();
        }
        
        return new Errores("SEMANTICO", "Tipo de dato no permitido para la funcion ToString", linea, columna);

    }
    
    
    
    
    

}
