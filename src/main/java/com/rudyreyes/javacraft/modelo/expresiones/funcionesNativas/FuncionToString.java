/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.expresiones.funcionesNativas;

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
public class FuncionToString extends  Instruccion{
    private Instruccion expresion;

    public FuncionToString(Instruccion expresion, int linea, int columna) {
        super(new Tipo(TipoDato.CADENA), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        Object valor = null;
        
        valor = this.expresion.interpretar(arbol, tabla);
        
        if(valor instanceof Errores){
            return valor;
        }
        
        
        
        if(valor instanceof Object[]){
            return imprimirVector(valor);
        }
        
        if(valor instanceof List){
            return imprimirStruct(valor);
        }
        
        if(this.expresion.tipo.getTipo() != TipoDato.VOID){
            return valor.toString();
        }
        
        return new Errores("SEMANTICO", "Tipo de dato no permitido para la funcion ToString", linea, columna);

    }
    
    private String imprimirStruct(Object valor){
        String struct = "{";
       
        if(valor instanceof List){
            LinkedList<HashMap> acceso = (LinkedList<HashMap>) valor;
            
            
            for(int i =0; i< acceso.size(); i++){
                String nombreC =(String) acceso.get(i).get("id");
                    var valorC =  acceso.get(i).get("valor");
                    
                    if(valorC instanceof List){
                        struct += nombreC +": {";
                        LinkedList<HashMap> acceso2 = (LinkedList<HashMap>) valorC;

                        for (int j = 0; j < acceso2.size(); j++) {
                                String nombreC2 = (String) acceso2.get(j).get("id");
                                var valorC2 = acceso2.get(j).get("valor");
                                struct += nombreC2 + ":"+valorC2 +",";
                        }
                        struct += " }";
                        
                    }else{
                        struct += nombreC + ":"+valorC +",";
                    }
                
                    
            }
        
        }
        struct += "}";
    
        return struct;
        
    }
    
    private String imprimirVector(Object valor){
        String vector = "[";
        if(valor instanceof Object[]){
            Object [] resultado = (Object []) valor;
            
            for(int i=0; i<resultado.length; i++){
                vector += (String)resultado[(int)i] + ", ";
            }
        }
        vector+= "]"; 
        
        return vector;
    }
    
    
    

}
