/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.simbolo;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import com.rudyreyes.javacraft.modelo.errores.Errores;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author rudyo
 */
public class Arbol {
    
    private LinkedList<Instruccion> instrucciones;
    private String consola;
    private TablaSimbolos tablaGlobal;
    public  LinkedList<Errores> errores;
    public List<TablaSimbolos> tablasEntornos;

    public Arbol(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
        this.consola = "";
        this.tablaGlobal = new TablaSimbolos();
        this.errores = new LinkedList<>();
        this.tablasEntornos = new ArrayList<>();
    }

    public LinkedList<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public String getConsola() {
        return consola;
    }

    public void setConsola(String consola) {
        this.consola = consola;
    }

    public TablaSimbolos getTablaGlobal() {
        return tablaGlobal;
    }

    public void setTablaGlobal(TablaSimbolos tablaGlobal) {
        this.tablaGlobal = tablaGlobal;
    }

    public LinkedList<Errores> getErrores() {
        return errores;
    }

    public void setErrores(LinkedList<Errores> errores) {
        this.errores = errores;
    }
    
    public void agregarTablaEntorno(TablaSimbolos tabla) {
        this.tablasEntornos.add(tabla);
    }
    
    public List<EntornoSimbolos> getTodosLosSimbolos() {
        List<EntornoSimbolos> todosLosSimbolos = new ArrayList<>();

        for (TablaSimbolos tabla : tablasEntornos) {
            List<Simbolo> simbolos = tabla.getSimbolosTablaActual();
            EntornoSimbolos entornoSimbolos = new EntornoSimbolos(tabla.getNombre(), simbolos);
            todosLosSimbolos.add(entornoSimbolos);
        }

        // Agregar los símbolos de la tabla global
        List<Simbolo> simbolosGlobales = tablaGlobal.getSimbolosTablaActual();
        EntornoSimbolos entornoGlobal = new EntornoSimbolos(tablaGlobal.getNombre(), simbolosGlobales);
        todosLosSimbolos.add(entornoGlobal);

        return todosLosSimbolos;
    }

    public void mostrarTodosLosSimbolos() {
    List<EntornoSimbolos> todosLosSimbolos = getTodosLosSimbolos();
    for (EntornoSimbolos entornoSimbolos : todosLosSimbolos) {
        System.out.println("Entorno: " + entornoSimbolos.getNombreEntorno());
        for (Simbolo simbolo : entornoSimbolos.getSimbolos()) {
            System.out.println(simbolo.imprimirSimbolo());
        }
    }
}
    
    
    public void Println(String valor){
        
        this.consola += secuenciasEscape(valor) +"\n";
    }
    
        private String secuenciasEscape(String valor) {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < valor.length(); i++) {
            char c = valor.charAt(i);
            if (c == '\\' && i + 1 < valor.length()) {
                char siguienteC = valor.charAt(i + 1);
                switch (siguienteC) {
                    case 'n':
                        resultado.append('\n');
                        i++;
                        break;
                    case 't':
                        resultado.append('\t');
                        i++; 
                        break;
                    case 'r':
                        resultado.append('\r');
                        i++; 
                        break;
                    case '\\':
                        resultado.append('\\');
                        i++;
                        break;
                    case '\"':
                        resultado.append('\"');
                        i++; 
                        break;
                    case '\'':
                        resultado.append('\'');
                        i++; 
                        break;
                    default:
                        resultado.append(c); 
                        break;
                }
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    
}
