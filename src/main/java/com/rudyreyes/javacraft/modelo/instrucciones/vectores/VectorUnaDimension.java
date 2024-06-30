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
public class VectorUnaDimension extends Instruccion {
    public boolean mutabilidad;
    public String identificador;
    public LinkedList<Instruccion> listado;

    public VectorUnaDimension(boolean mutabilidad, String identificador, LinkedList<Instruccion> listado, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.mutabilidad = mutabilidad;
        this.identificador = identificador;
        this.listado = listado;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        Object[] valores = new Object[listado.size()];
        int posicion = 0;
        for (Instruccion valor : listado) {
            var interpretado = valor.interpretar(arbol, tabla);
            if (interpretado instanceof Errores) {
                return interpretado;
            }
            
            if (valor.tipo.getTipo() != this.tipo.getTipo()) {
                return new Errores("SEMANTICO", "Tipo de dato erroneo, no se puede asignar el valor de tipo "+valor.tipo.getTipo()+ " al vector de tipo "+ this.tipo.getTipo(), this.linea, this.columna);
            }
            valores[posicion++] = interpretado;
        }
        
        Simbolo simbolo = new Simbolo(mutabilidad, this.tipo, identificador, valores, linea, columna);
        if (!tabla.setVariable(simbolo)) {
            return new Errores("SEMANTICO", "El vector \""+this.identificador+"\" ya existe", this.linea, this.columna);

        }
        return null;
    }
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
       // <MUTABILIDAD> <ID> : <TIPO> [ ] = [ <LISTAVALORES> ] ;
       String declaracion = "n" + arbol.getContador();
        String mut = "n" + arbol.getContador();
        String idN = "n" + arbol.getContador();
        String dosP = "n" + arbol.getContador();
        String tipoN= "n" + arbol.getContador();
        String llaves= "n" + arbol.getContador();
        String igual = "n" + arbol.getContador();
        String Cor1= "n" + arbol.getContador();
        String expN = "n" + arbol.getContador();
        String Cor2= "n" + arbol.getContador();
        String fin = "n" + arbol.getContador();
        
        String resultado = anterior+" ->"+declaracion+";\n";
        
        resultado += declaracion + "[label=\"Declaracion Vector\"];\n";
        resultado += mut + "[label=\""+ ((this.mutabilidad)? "var":"const") +"\"];\n";
        resultado += idN + "[label=\""+this.identificador+"\"];\n";
        resultado += dosP + "[label=\":\"];\n";
        resultado += tipoN + "[label=\""+this.tipo.getTipo().toString()+"\"];\n";
        resultado += llaves + "[label=\"[]\"];\n";
        resultado += igual + "[label=\"=\"];\n";
        resultado += Cor1 + "[label=\"[\"];\n";
        resultado += expN + "[label=\"LiSTADO EXP\"];\n";
        resultado += Cor2 + "[label=\"]\"];\n";
        resultado += fin + "[label=\";\"];\n";
        
        resultado += declaracion + " ->" + mut + ";\n";
        resultado += declaracion + " ->" + idN + ";\n";
        resultado += declaracion + " ->" + dosP + ";\n";
        resultado += declaracion + " ->" + tipoN + ";\n";
        resultado += declaracion + " ->" + llaves + ";\n";
        resultado += declaracion + " ->" + igual + ";\n";
        resultado += declaracion + " ->" + Cor1 + ";\n";
        resultado += declaracion + " ->" + expN + ";\n";
        resultado += declaracion + " ->" + Cor2 + ";\n";
        resultado += declaracion + " ->" + fin + ";\n";
        
        for (var i : this.listado) {
            if(i ==null ){
                continue;
            }
            
            String nodoAux = "n" + arbol.getContador();
                resultado += nodoAux + "[label=\"INSTRUCCION\"];\n";
                resultado += expN + "-> " + nodoAux + ";\n";
                resultado += i.generarast(arbol, nodoAux);
        }
        return resultado;
    }
    
}
