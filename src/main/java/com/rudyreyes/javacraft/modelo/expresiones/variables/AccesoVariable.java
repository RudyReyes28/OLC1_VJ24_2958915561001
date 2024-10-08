/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.expresiones.variables;

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
public class AccesoVariable extends Instruccion{
    
    private String id;

    public AccesoVariable(String id, int linea, int col) {
        super(new Tipo(TipoDato.VOID), linea, col);
        this.id = id;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var valor = tabla.getVariable(this.id);
        if (valor == null) {
            return new Errores("SEMANTICA", "La variable \" " + this.id+" \" no existe",
                    this.linea, this.columna);
        }
        this.tipo.setTipo(valor.getTipo().getTipo());
        return valor.getValor();
    }
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        String nodoVar = "n" + arbol.getContador();//n1
        String nodoValor = "n" + arbol.getContador();//n2

        String resultado = anterior + " -> " + nodoVar+";\n";

        resultado += nodoVar + "[label=\"NATIVO\"];\n";
        resultado += nodoValor + "[label=\""
                + this.id + "\"];\n";

        resultado += nodoVar + " -> " + nodoValor+";\n";
        return resultado;
    }
    
}
