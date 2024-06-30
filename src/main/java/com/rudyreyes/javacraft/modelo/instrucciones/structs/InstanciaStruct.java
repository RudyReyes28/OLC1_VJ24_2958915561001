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
import java.util.List;

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

                }else{
                    var resultado = valor.interpretar(arbol, tabla);
                    if (resultado instanceof Errores) {
                        return resultado;
                    }
                    
                    if(resultado instanceof List){
                        LinkedList<HashMap> hashAnidado = new LinkedList<>();
                        for (HashMap mapR :(LinkedList<HashMap>) resultado) {
                            hashAnidado.add(new HashMap(mapR));
                        }
                        
                        nuevoHash.get(i).put("valor", hashAnidado);
                    }else{
                        return new Errores("SEMANTICO", "La variable no es un struct",
                                this.linea, this.columna);
                    }
                    
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
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        /*<MUTABILIDAD> <ID> : <NOMBRE_STRUCT> = {
<VALORES_STRUCT> };
*/
        String declaracion = "n" + arbol.getContador();
        String mut = "n" + arbol.getContador();
        String idN = "n" + arbol.getContador();
        String dosP = "n" + arbol.getContador();
        String tipoN= "n" + arbol.getContador();
        String igual= "n" + arbol.getContador();
        String expN = "n" + arbol.getContador();
        String fin = "n" + arbol.getContador();
        
        String resultado = anterior+" ->"+declaracion+";\n";
        
        resultado += declaracion + "[label=\"Declaracion Variable\"];\n";
        resultado += mut + "[label=\""+ ((this.mutabilidad)? "var":"const") +"\"];\n";
        resultado += idN + "[label=\""+this.id+"\"];\n";
        resultado += dosP + "[label=\":\"];\n";
        resultado += tipoN + "[label=\""+this.nombreStruct+"\"];\n";
        resultado += igual + "[label=\"=\"];\n";
        resultado += expN + "[label=\"EXPRESION\"];\n";
        resultado += fin + "[label=\";\"];\n";
        
        resultado += declaracion + " ->" + mut + ";\n";
        resultado += declaracion + " ->" + idN + ";\n";
        resultado += declaracion + " ->" + dosP + ";\n";
        resultado += declaracion + " ->" + tipoN + ";\n";
        resultado += declaracion + " ->" + igual + ";\n";
        resultado += declaracion + " ->" + expN + ";\n";
        resultado += declaracion + " ->" + fin + ";\n";
        
        
        return resultado ;
    }
    
}
