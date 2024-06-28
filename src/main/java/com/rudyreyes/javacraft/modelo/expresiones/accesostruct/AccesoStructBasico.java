/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.expresiones.accesostruct;

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
public class AccesoStructBasico extends Instruccion{
    
    private String nombreStruct;
    private String campo;

    public AccesoStructBasico(String nombreStruct, String campo, int linea, int columna) {
        super(new Tipo(TipoDato.VOID), linea, columna);
        this.nombreStruct = nombreStruct;
        this.campo = campo;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var struct = tabla.getVariable(nombreStruct);
        if (struct == null) {
            return new Errores("SEMANTICO", "El struct \" " + this.nombreStruct+" \" no existe",
                    this.linea, this.columna);
        }
        
        var valor = struct.getValor();
        
        if(valor instanceof List){
            LinkedList<HashMap> acceso = (LinkedList<HashMap>) valor;
            
            for(int i =0; i< acceso.size(); i++){
                String nombreC =(String) acceso.get(i).get("id");
                if(nombreC.equalsIgnoreCase(campo)){
                    var valorC = acceso.get(i).get("valor");
                    var tipoDato = acceso.get(i).get("tipo");
                    this.tipo.setTipo(((Tipo)tipoDato).getTipo());
                    return valorC;
                }
            }
        }
             return new Errores("SEMANTICO", "La \" " + this.nombreStruct+" \" no es un struct",
                    this.linea, this.columna);
        
    }
    
    
    
    
}
