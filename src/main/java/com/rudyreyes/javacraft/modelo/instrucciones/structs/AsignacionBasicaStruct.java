/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.instrucciones.structs;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import com.rudyreyes.javacraft.modelo.errores.Errores;
import com.rudyreyes.javacraft.modelo.simbolo.Arbol;
import com.rudyreyes.javacraft.modelo.simbolo.TablaSimbolos;
import com.rudyreyes.javacraft.modelo.simbolo.Tipo;
import com.rudyreyes.javacraft.modelo.simbolo.TipoDato;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author rudyo
 */
public class AsignacionBasicaStruct extends Instruccion{
    private String nombreS;
    private String campo;
    private Instruccion valor;

    public AsignacionBasicaStruct(String nombreS, String campo, Instruccion valor, int linea, int columna) {
        super(new Tipo(TipoDato.VOID), linea, columna);
        this.nombreS = nombreS;
        this.campo = campo;
        this.valor = valor;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var struct = tabla.getVariable(nombreS);
        if (struct == null) {
            return new Errores("SEMANTICO", "El struct \" " + this.nombreS+" \" no existe",
                    this.linea, this.columna);
        }
        var exp = this.valor.interpretar(arbol, tabla);
        
        if(exp instanceof Errores){
            return exp;
        }
        
        if(!struct.isMutable()){
            return new Errores("SEMANTICO", "El struct \" "+nombreS+" \" es de tipo const, no se puede asignar el valor",
                    this.linea, this.columna);
        }
        
        var lista = struct.getValor();
        
        if(lista instanceof List){
            LinkedList<HashMap> acceso = (LinkedList<HashMap>) lista;
            
            for(int i =0; i< acceso.size(); i++){
                String nombreC =(String) acceso.get(i).get("id");
                if(nombreC.equalsIgnoreCase(campo)){
                    var tipoDato = acceso.get(i).get("tipo");
                    if (((Tipo)tipoDato).getTipo() != this.valor.tipo.getTipo()) {
                        return new Errores("SEMANTICO", "Tipo de dato erroneo, no se puede asignar el valor de tipo " + this.valor.tipo.getTipo() + " a la variable de tipo " + ((Tipo)tipoDato).getTipo() ,
                                this.linea, this.columna);
                    }
                    acceso.get(i).put("valor", exp);
                    
                }
            }
        }
             return null;
    }
    
    
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        //<ID> . <CAMPO> = <EXPRESION> ;

        String stAsig = "n" + arbol.getContador();
        String idV = "n" + arbol.getContador();
        String punto = "n" + arbol.getContador();
        String cN = "n" + arbol.getContador();
        String igualN = "n" + arbol.getContador();
        String asig = "n" + arbol.getContador();
        String pC = "n" + arbol.getContador();
        
        String resultado = anterior+" ->"+stAsig+";\n"; 
        
        resultado += stAsig + "[label=\"ASIGNACION STRUCT\"];\n";
        resultado += idV + "[label=\""+this.nombreS+"\"];\n";
        resultado += punto + "[label=\".\"];\n";
        resultado += cN + "[label=\""+this.campo+"\"];\n";
        resultado += igualN + "[label=\"=\"];\n";
        resultado += asig + "[label=\"EXP\"];\n";
        resultado += pC + "[label=\";\"];\n";
        
        resultado += stAsig + " ->" + idV + ";\n";
        resultado += stAsig + " ->" + punto + ";\n";
        resultado += stAsig + " ->" + cN + ";\n";
        resultado += stAsig + " ->" + igualN + ";\n";
        resultado += stAsig + " ->" + asig + ";\n";
        resultado += stAsig + " ->" + pC + ";\n";
        
        return resultado += this.valor.generarast(arbol, asig);
    }
    
    
}
