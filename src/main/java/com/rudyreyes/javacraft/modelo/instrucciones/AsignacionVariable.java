/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.instrucciones;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import com.rudyreyes.javacraft.modelo.errores.Errores;
import com.rudyreyes.javacraft.modelo.simbolo.Arbol;
import com.rudyreyes.javacraft.modelo.simbolo.Simbolo;
import com.rudyreyes.javacraft.modelo.simbolo.TablaSimbolos;
import com.rudyreyes.javacraft.modelo.simbolo.Tipo;
import com.rudyreyes.javacraft.modelo.simbolo.TipoDato;

/**
 *
 * @author rudyo
 */
public class AsignacionVariable extends Instruccion{
    
    private String id;
    private Instruccion exp;
    
    
    public AsignacionVariable(String id, Instruccion exp, int linea, int col) {
        super(new Tipo(TipoDato.VOID), linea, col);
        this.id = id;
        this.exp = exp;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var variable = tabla.getVariable(id);
        
        //VARIBLE EXISTE
        if (variable == null) {
            return new Errores("SEMANTICO", "La variable \" " + variable+" \" no existe",
                    this.linea, this.columna);
        }
        
        //OBTENER VALOR
        var nuevoValor = this.exp.interpretar(arbol, tabla);
        if (nuevoValor instanceof Errores) {
            return nuevoValor;
        }
        
        //VALIDAR TIPOS
        if (variable.getTipo().getTipo() != this.exp.tipo.getTipo()) {
            return new Errores("SEMANTICO", "Tipo de dato erroneo, no se puede asignar el valor de tipo "+this.exp.tipo.getTipo()+ " a la variable de tipo "+ variable.getTipo().getTipo(),
                    this.linea, this.columna);
        }
        
        //VALIDAR MUTABILIDAD
        
        if(!variable.isMutable()){
            return new Errores("SEMANTICO", "La variable \" "+id+" \" es de tipo const, no se puede asignar el valor",
                    this.linea, this.columna);
        }
        
        variable.setValor(nuevoValor);
        variable.setLinea(this.linea);
        variable.setColumna(this.columna);
        
        return null;
    }
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        //num = 10;
        String stAsig = "n" + arbol.getContador();
        String idV = "n" + arbol.getContador();
        String igualN = "n" + arbol.getContador();
        String asig = "n" + arbol.getContador();
        String pC = "n" + arbol.getContador();
        
        String resultado = anterior+" ->"+stAsig+";\n"; 
        
        resultado += stAsig + "[label=\"ASIGNACION VAR\"];\n";
        resultado += idV + "[label=\""+this.id+"\"];\n";
        resultado += igualN + "[label=\"=\"];\n";
        resultado += asig + "[label=\"EXP\"];\n";
        resultado += pC + "[label=\";\"];\n";
        
        resultado += stAsig + " ->" + idV + ";\n";
        resultado += stAsig + " ->" + igualN + ";\n";
        resultado += stAsig + " ->" + asig + ";\n";
        resultado += stAsig + " ->" + pC + ";\n";
        
        return resultado += this.exp.generarast(arbol, asig);
    }
}
