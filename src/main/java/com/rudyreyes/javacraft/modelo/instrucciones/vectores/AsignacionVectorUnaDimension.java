/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.instrucciones.vectores;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import com.rudyreyes.javacraft.modelo.errores.Errores;
import com.rudyreyes.javacraft.modelo.simbolo.Arbol;
import com.rudyreyes.javacraft.modelo.simbolo.TablaSimbolos;
import com.rudyreyes.javacraft.modelo.simbolo.Tipo;
import com.rudyreyes.javacraft.modelo.simbolo.TipoDato;
import java.util.List;

/**
 *
 * @author rudyo
 */
public class AsignacionVectorUnaDimension extends Instruccion{
    private String id;
    private Instruccion posicion;
    private Instruccion asignacion;

    public AsignacionVectorUnaDimension(String id, Instruccion posicion, Instruccion asignacion, int linea, int columna) {
        super(new Tipo(TipoDato.VOID), linea, columna);
        this.id = id;
        this.posicion = posicion;
        this.asignacion = asignacion;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var variable = tabla.getVariable(id);
        
        if (variable == null) {
            return new Errores("SEMANTICO", "El vector \" " + this.id+" \" no existe",
                    this.linea, this.columna);
        }
        
        var posicionV = this.posicion.interpretar(arbol, tabla);
        
        if(posicionV instanceof Errores){
            return posicionV;
        }
        
        if(this.posicion.tipo.getTipo() != TipoDato.ENTERO){
            return new  Errores("SEMANTICO", "La posicion vector \" " + this.id+" \" no es del tipo de dato entero",
                    this.linea, this.columna);
        }
        
        var nuevoValor = this.asignacion.interpretar(arbol, tabla);
        
        if (nuevoValor instanceof Errores) {
            return nuevoValor;
        }
        
        if (variable.getTipo().getTipo() != this.asignacion.tipo.getTipo()) {
            return new Errores("SEMANTICO", "Tipo de dato erroneo, no se puede asignar el valor de tipo "+this.asignacion.tipo.getTipo()+ " a la variable de tipo "+ variable.getTipo().getTipo(),
                    this.linea, this.columna);
        }
        
        //VALIDAR MUTABILIDAD
        
        if(!variable.isMutable()){
            return new Errores("SEMANTICO", "La variable \" "+id+" \" es de tipo const, no se puede asignar el valor",
                    this.linea, this.columna);
        }
        
        var arreglo = variable.getValor();
        
        if(arreglo instanceof Object[]){
            Object [] resultado = (Object []) arreglo;
            
            if((int)posicionV>=resultado.length){
                return new  Errores("SEMANTICO", "La posicion  \" " + posicionV+" \" es mayor que la longitud del vector",
                    this.linea, this.columna);
            }
            
            resultado[(int) posicionV] = nuevoValor;
            
            variable.setValor(resultado);
            variable.setLinea(this.linea);
            variable.setColumna(this.columna);
        
        }else if(arreglo instanceof List){
            if((int)posicionV>=((List<Object>) arreglo).size() ){
                return new  Errores("SEMANTICO", "La posicion  \" " + posicionV+" \" es mayor que la longitud de la lista",
                    this.linea, this.columna);
            }
            
            ((List<Object>) arreglo).set((int)posicionV, nuevoValor);
            
        }else{
            return new Errores("SEMANTICO", "La variable \" " + this.id+" \" no es un vector o una lista",
                    this.linea, this.columna);
        }
        
        
        return null;
    }
    
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        String stAsig = "n" + arbol.getContador();
        String idV = "n" + arbol.getContador();
        String cr1 = "n" + arbol.getContador();
        String pos = "n" + arbol.getContador();
        String cr2 = "n" + arbol.getContador();
        String igualN = "n" + arbol.getContador();
        String asig = "n" + arbol.getContador();
        String pC = "n" + arbol.getContador();
        
        String resultado = anterior+" ->"+stAsig+";\n"; 
        
        resultado += stAsig + "[label=\"ASIGNACION VECTOR\"];\n";
        resultado += idV + "[label=\""+this.id+"\"];\n";
        resultado += cr1 + "[label=\"[\"];\n";
        resultado += pos + "[label=\"POS\"];\n";
        resultado += cr2 + "[label=\"]\"];\n";
        resultado += igualN + "[label=\"=\"];\n";
        resultado += asig + "[label=\"EXP\"];\n";
        resultado += pC + "[label=\";\"];\n";
        
        resultado += stAsig + " ->" + idV + ";\n";
        resultado += stAsig + " ->" + igualN + ";\n";
        resultado += stAsig + " ->" + cr1 + ";\n";
        resultado += stAsig + " ->" + pos + ";\n";
        resultado += stAsig + " ->" + cr2 + ";\n";
        resultado += stAsig + " ->" + asig + ";\n";
        resultado += stAsig + " ->" + pC + ";\n";
        resultado +=this.posicion.generarast(arbol, pos);
        
        return resultado += this.asignacion.generarast(arbol, asig);
    }
    
    
}
