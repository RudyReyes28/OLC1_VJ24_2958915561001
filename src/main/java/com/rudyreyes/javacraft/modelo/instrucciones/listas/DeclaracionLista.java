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
        //List< <TIPO> > <ID> = new List();
        String stAsig = "n" + arbol.getContador();
        String list = "n" + arbol.getContador();
        String menor = "n" + arbol.getContador();
        String tipoN = "n" + arbol.getContador();
        String mayor = "n" + arbol.getContador();
        String idV = "n" + arbol.getContador();
        String igualN = "n" + arbol.getContador();
        String asig = "n" + arbol.getContador();
        String pC = "n" + arbol.getContador();
        
        String resultado = anterior+" ->"+stAsig+";\n"; 
        
        resultado += stAsig + "[label=\"DECLARACION LIST\"];\n";
        resultado += list + "[label=\"list\"];\n";
        resultado += menor + "[label=\"<\"];\n";
        resultado += tipoN + "[label=\""+this.tipo.getTipo().toString()+"\"];\n";
        resultado += mayor + "[label=\">\"];\n";
        resultado += idV + "[label=\""+this.identificador+"\"];\n";
        resultado += igualN + "[label=\"=\"];\n";
        resultado += asig + "[label=\"new List()\"];\n";
        resultado += pC + "[label=\";\"];\n";
        
        resultado += stAsig + " ->" + list + ";\n";
        resultado += stAsig + " ->" + menor + ";\n";
        resultado += stAsig + " ->" + tipoN + ";\n";
        resultado += stAsig + " ->" + mayor + ";\n";
        resultado += stAsig + " ->" + idV + ";\n";
        resultado += stAsig + " ->" + igualN + ";\n";
        resultado += stAsig + " ->" + asig + ";\n";
        resultado += stAsig + " ->" + pC + ";\n";
        
        return resultado ;
    }
}
