/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.instrucciones.metodos;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import com.rudyreyes.javacraft.modelo.errores.Errores;
import com.rudyreyes.javacraft.modelo.simbolo.Arbol;
import com.rudyreyes.javacraft.modelo.simbolo.TablaSimbolos;
import com.rudyreyes.javacraft.modelo.simbolo.Tipo;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author rudyo
 */
public class Metodo extends Instruccion{
    public String id;
    public LinkedList<HashMap> parametros;
    public LinkedList<Instruccion> instrucciones;

    public Metodo(String id, LinkedList<HashMap> parametros, LinkedList<Instruccion> instrucciones, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.id = id;
        this.parametros = parametros;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        for (var i : this.instrucciones) {
            if(i ==null ){
                continue;
            }
            if(i instanceof FuncionReturn){
                
                var res = i.interpretar(arbol, tabla);
                if (res instanceof Errores) {
                    return res;
                }
                if (tipo.getTipo() == i.tipo.getTipo()) {
                    return res;
                } else {
                    return new Errores("SEMANTICO", "El tipo de retorno no coincide con el tipo del metodo", linea, columna);
                }


                
            }
            var resultado = i.interpretar(arbol, tabla);
            if(resultado instanceof Errores){
                return resultado;
            }
            
            if(resultado instanceof FuncionReturn){
                    return resultado;
            }
            
            // return;
        }
        return null;
    }
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        String cadena= " ";
        for (var i : this.instrucciones) {
            if(i ==null ){
                continue;
            }
            
            String nodoAux = "n" + arbol.getContador();
                cadena += nodoAux + "[label=\"INSTRUCCION\"];\n";
                cadena += anterior + "-> " + nodoAux + ";\n";
                cadena += i.generarast(arbol, nodoAux);
        }
        
        return cadena;
    }
}
