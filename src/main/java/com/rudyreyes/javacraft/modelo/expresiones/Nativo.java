/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.expresiones;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import com.rudyreyes.javacraft.modelo.simbolo.Arbol;
import com.rudyreyes.javacraft.modelo.simbolo.TablaSimbolos;
import com.rudyreyes.javacraft.modelo.simbolo.Tipo;

/**
 *
 * @author rudyo
 */
public class Nativo extends Instruccion{
    public Object valor;

    public Nativo(Object valor, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.valor = valor;
    }

    

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        return this.valor;
    }

    @Override
    public String generarast(Arbol arbol, String anterior) {
        String nodoNativo = "n" + arbol.getContador();//n1
        String nodoValor = "n" + arbol.getContador();//n2

        String resultado = anterior + " -> " + nodoNativo+";\n";

        resultado += nodoNativo + "[label=\"NATIVO\"];\n";
        resultado += nodoValor + "[label=\""
                + this.valor.toString() + "\"];\n";

        resultado += nodoNativo + " -> " + nodoValor+";\n";
        return resultado;
    }
    
    
    
}
