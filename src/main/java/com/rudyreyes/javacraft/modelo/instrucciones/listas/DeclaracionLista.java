/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.instrucciones.listas;

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


    //List< <TIPO> > <ID> = new List();
//List<int> miLista = new List();
public class DeclaracionLista extends Instruccion{
    public boolean mutabilidad;
    public String identificador;

    public DeclaracionLista(String identificador, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.mutabilidad = true;
        this.identificador = identificador;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        
        Simbolo s = new Simbolo(mutabilidad,this.tipo, this.identificador, new LinkedList<>(), this.linea, this.columna);

        boolean creacion = tabla.setVariable(s);
        if (!creacion) {
            return new Errores("SEMANTICO", "La variable \""+this.identificador+"\" ya existe", this.linea, this.columna);
        }

        return null;
    }
    
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        return "";
    }
}
