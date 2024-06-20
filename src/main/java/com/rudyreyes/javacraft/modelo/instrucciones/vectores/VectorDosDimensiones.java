/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.instrucciones.vectores;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import com.rudyreyes.javacraft.modelo.errores.Errores;
import com.rudyreyes.javacraft.modelo.simbolo.Arbol;
import com.rudyreyes.javacraft.modelo.simbolo.Simbolo;
import com.rudyreyes.javacraft.modelo.simbolo.TablaSimbolos;
import com.rudyreyes.javacraft.modelo.simbolo.Tipo;
import java.util.LinkedList;

/**
 *
 * @author rudyo
 */
public class VectorDosDimensiones extends Instruccion {
    public boolean mutabilidad;
    public String identificador;
    public LinkedList<LinkedList<Instruccion>> listado;

    public VectorDosDimensiones(boolean mutabilidad, String identificador, LinkedList<LinkedList<Instruccion>> listado, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.mutabilidad = mutabilidad;
        this.identificador = identificador;
        this.listado = listado;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        Object[][] valores = new Object[listado.size()][];
        
        int posicionFila = 0;
        for (LinkedList<Instruccion> fila : listado) {
            Object[] filaI = new Object[fila.size()];
            int posicionColumna = 0;
            for (Instruccion valor : fila) {
                var resultado = valor.interpretar(arbol, tabla);
                if (resultado instanceof Errores) {
                    return resultado;
                }
                
                if (valor.tipo.getTipo() != this.tipo.getTipo()) {
                    return new Errores("SEMANTICO", "Tipo de dato erroneo, no se puede asignar el valor de tipo " + valor.tipo.getTipo() + " al vector de tipo " + this.tipo.getTipo(), this.linea, this.columna);
                }
                filaI[posicionColumna++] = resultado;
            }
            valores[posicionFila++] = filaI;
        }
        
        Simbolo simbolo = new Simbolo(mutabilidad, this.tipo, identificador, valores, linea, columna);
        if (!tabla.setVariable(simbolo)) {
            return new Errores("SEMANTICO", "El vector \""+this.identificador+"\" ya existe", this.linea, this.columna);

        }
        return null;
    }

    
}
