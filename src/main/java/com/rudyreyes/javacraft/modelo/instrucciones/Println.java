/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.instrucciones;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import com.rudyreyes.javacraft.modelo.errores.Errores;
import com.rudyreyes.javacraft.modelo.simbolo.*;

/**
 *
 * @author rudyo
 */
public class Println extends Instruccion{
    private Instruccion expresion;

    public Println(Instruccion expresion, int linea, int columna) {
        super(new Tipo(TipoDato.VOID), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var resultado = this.expresion.interpretar(arbol, tabla);
        
        if(resultado instanceof Errores){
            return resultado;
        }
        
        arbol.Println(resultado.toString());
        return null;
    }
    
    
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
         // PRINTT -> PRINT ( EXP ) ;
        String nodoPP = "n" + arbol.getContador();
        String nodoP = "n" + arbol.getContador();
        String nodoP1 = "n" + arbol.getContador();
        String nodoExp = "n" + arbol.getContador();
        String nodoP2 = "n" + arbol.getContador();
        String nodoPC = "n" + arbol.getContador();

        String resultado = nodoPP + "[label=\"PRINTLN\"];\n";
        resultado += anterior + " -> " + nodoPP + ";\n";

        resultado += nodoP + "[label=\"println\"];\n";
        resultado += nodoP1 + "[label=\"(\"];\n";
        resultado += nodoExp + "[label=\"EXPRESION\"];\n";
        resultado += nodoP2 + "[label=\")\"];\n";
        resultado += nodoPC + "[label=\";\"];\n";

        resultado += nodoPP + " -> " + nodoP + ";\n";
        resultado += nodoPP + " -> " + nodoP1 + ";\n";
        resultado += nodoPP + " -> " + nodoExp + ";\n";
        resultado += nodoPP + " -> " + nodoP2 + ";\n";
        resultado += nodoPP + " -> " + nodoPC + ";\n";

        resultado += this.expresion.generarast(arbol, nodoExp);

        return resultado;
    }
    
}
