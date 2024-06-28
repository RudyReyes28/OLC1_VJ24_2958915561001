/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.instrucciones.structs;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import com.rudyreyes.javacraft.modelo.errores.Errores;
import com.rudyreyes.javacraft.modelo.simbolo.Arbol;
import com.rudyreyes.javacraft.modelo.simbolo.Simbolo;
import com.rudyreyes.javacraft.modelo.simbolo.TablaSimbolos;
import com.rudyreyes.javacraft.modelo.simbolo.Tipo;
import com.rudyreyes.javacraft.modelo.simbolo.TipoDato;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author rudyo
 */
public class InstanciaStruct extends Instruccion{
    private boolean mutabilidad;
    private String id;
    private String nombreStruct;
    public LinkedList<Instruccion> valores;

    public InstanciaStruct(boolean mutabilidad, String id, String nombreStruct, LinkedList<Instruccion> valores, int linea, int columna) {
        super(new Tipo(TipoDato.VOID), linea, columna);
        this.mutabilidad = mutabilidad;
        this.id = id;
        this.nombreStruct = nombreStruct;
        this.valores = valores;
    }
    
    
    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var busqueda = arbol.getStruct(nombreStruct);
        if(busqueda == null){
            return new Errores("SEMANTICO", "Struct no existente",
                    this.linea, this.columna);
        }
        
        if(busqueda instanceof DeclaracionStruct){
            DeclaracionStruct struct = (DeclaracionStruct) busqueda;
            
            if(struct.listado.size() != this.valores.size()){
                return new Errores("SEMANTICO", "Los valores del struct son erroneos",
                        this.linea, this.columna);
            }
            
            
            
            LinkedList<HashMap> nuevoHash = new LinkedList<>();
            for (HashMap map : struct.listado) {
                nuevoHash.add(new HashMap(map));
            }

            for (int i = 0; i < this.valores.size(); i++) {
                

                var tipoDato = struct.listado.get(i).get("tipo");
                var valor = this.valores.get(i);
                
                if (tipoDato instanceof Tipo) {
                    var resultado = valor.interpretar(arbol, tabla);

                    if (resultado instanceof Errores) {
                        return resultado;
                    }

                    if (((Tipo) tipoDato).getTipo() != valor.tipo.getTipo()) {
                        return new Errores("SEMANTICO", "Los tipos de datos del struct son erroneos",
                                this.linea, this.columna);
                    }

                    nuevoHash.get(i).put("valor", resultado);

                }
                
            }

            Simbolo s = new Simbolo(mutabilidad, this.tipo, this.id, nuevoHash, this.linea, this.columna);

            boolean creacion = tabla.setVariable(s);
            if (!creacion) {
                return new Errores("SEMANTICO", "La variable \"" + this.id + "\" ya existe", this.linea, this.columna);
            }
            
        }
        return null;
    }
    

    
}
