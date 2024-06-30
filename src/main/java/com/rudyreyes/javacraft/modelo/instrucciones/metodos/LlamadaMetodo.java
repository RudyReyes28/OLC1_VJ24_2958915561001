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
public class LlamadaMetodo extends Instruccion{
    private String id;
    private LinkedList<Instruccion> parametros;

    public LlamadaMetodo(String id, LinkedList<Instruccion> parametros, int linea, int columna) {
        super(new Tipo(TipoDato.VOID), linea, columna);
        this.id = id;
        this.parametros = parametros;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var busqueda = arbol.getFuncion(this.id);
        if (busqueda == null) {
            return new Errores("SEMANTICO", "Funcion no existente",
                    this.linea, this.columna);
        }
        if (busqueda instanceof Metodo) {
            var metodo = (Metodo)busqueda;
            
            var newTabla = new TablaSimbolos(arbol.getTablaGlobal());
            newTabla.setNombre("LLAMADA METODO " + this.id);
            arbol.agregarTablaEntorno(newTabla);
            if (metodo.parametros.size() != this.parametros.size()) {
                return new Errores("SEMANTICO", "Parametros Erroneos",
                        this.linea, this.columna);
            }
            
            for (int i = 0; i < this.parametros.size(); i++) {
                var identificador = (String) metodo.parametros.
                        get(i).get("id");

                var valor = this.parametros.get(i);

                var tipo2 = (Tipo) metodo.parametros.
                        get(i).get("tipo");

                var declaracionParametro = new DeclaracionVariable(true, identificador, null, tipo2,
                        this.linea, this.columna);

                var resultado = declaracionParametro.interpretar(arbol, newTabla);
                
                if (resultado instanceof Errores) {
                    return resultado;
                }
                
                var valorInterpretado = valor.interpretar(arbol, tabla);
                if (valorInterpretado instanceof Errores) {
                    return valorInterpretado;
                }
                
                var variable = newTabla.getVariable(identificador);
                if (variable == null) {
                    return new Errores("SEMANTICO", "Error declaracion parametros",
                            this.linea, this.columna);
                }
                
                if (variable.getTipo().getTipo() != valor.tipo.getTipo()) {
                    return new Errores("SEMANTICO", "Error en tipo de parametro",
                            this.linea, this.columna);
                }
                
                variable.setValor(valorInterpretado);
                
            }
            
            var resultadoFuncion = metodo.interpretar(arbol, newTabla);
            if(resultadoFuncion instanceof FuncionReturn){
                
                    FuncionReturn miR = (FuncionReturn)resultadoFuncion;
                    var resR = miR.instruccion.interpretar(arbol, tabla);
                    
                    this.tipo.setTipo(miR.instruccion.tipo.getTipo());
                    
                    return resR;
                
            }
            if (resultadoFuncion instanceof Errores) {
                return resultadoFuncion;
            }
        }
        
        return null;

    }
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        var busqueda = arbol.getFuncion(id);
        var metodo = (Metodo)busqueda;
        String nodoExp1 = "n" + arbol.getContador();
        String nodoOp = "n" + arbol.getContador();
        String nodoExp2 = "n" + arbol.getContador();
        String inst = "n" + arbol.getContador();
        
        String resultado = anterior + " -> " + nodoExp1 + ";\n";
        
        
        resultado += nodoExp1 + "[label=\"LLAMADA METODO\"];\n";
        resultado += nodoOp + "[label=\"Metodo\"];\n";
        resultado += nodoExp2 + "[label=\""+ this.id+" \"];\n";
        resultado += inst + "[label=\" INSTRUCCIONES \"];\n";
        
        resultado += nodoExp1 +"->"+ nodoOp+ ";\n";
        resultado += nodoOp +"->"+ nodoExp2+ ";\n";
        resultado += nodoExp2 +"->"+ inst+ ";\n";
   
        return resultado+= metodo.generarast(arbol, inst);
    }
    
}
