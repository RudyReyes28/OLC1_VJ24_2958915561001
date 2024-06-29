/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.instrucciones.metodos;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import com.rudyreyes.javacraft.modelo.errores.Errores;
import com.rudyreyes.javacraft.modelo.instrucciones.DeclaracionVariable;
import com.rudyreyes.javacraft.modelo.simbolo.Arbol;
import com.rudyreyes.javacraft.modelo.simbolo.TablaSimbolos;
import com.rudyreyes.javacraft.modelo.simbolo.Tipo;
import com.rudyreyes.javacraft.modelo.simbolo.TipoDato;
import java.util.LinkedList;

/**
 *
 * @author rudyo
 */
public class StartWith extends Instruccion{
    private String id;
    private LinkedList<Instruccion> parametros;

    public StartWith(String id, LinkedList<Instruccion> parametros, int linea, int columna) {
        super(new Tipo(TipoDato.VOID), linea, columna);
        this.id = id;
        this.parametros = parametros;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var busqueda = arbol.getFuncion(id);
        if (busqueda == null) {
            return new Errores("SEMANTICO", "La Funcion '"+ this.id+"' no existente",
                    this.linea, this.columna);
        }
        
        if (busqueda instanceof Metodo) {
            var metodo = (Metodo)busqueda;
            var newTabla = new TablaSimbolos(arbol.getTablaGlobal());
            newTabla.setNombre("Start_with");

            
            if (metodo.parametros.size() != this.parametros.size()) {
                return new Errores("SEMANTICO", "Los parametros de la funcion '"+id+"' son erroneos",
                        this.linea, this.columna);
            }

            
            for (int i = 0; i < this.parametros.size(); i++) {
                var identificador = (String) metodo.parametros.get(i).get("id");
                var valor = this.parametros.get(i);
                var tipo2 = (Tipo) metodo.parametros.get(i).get("tipo");

                var declaracionParametro = new DeclaracionVariable(true,
                        identificador, valor, tipo2, this.linea, this.columna);

                var resultadoDeclaracion = declaracionParametro.interpretar(arbol, newTabla);

                if (resultadoDeclaracion instanceof Errores) {
                    return resultadoDeclaracion;
                }
            }

            var resultadoFuncion = metodo.interpretar(arbol, newTabla);
            if (resultadoFuncion instanceof Errores) {
                return resultadoFuncion;
            }
        }
        return null;
        
    }
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        return "";
    }
    
}
