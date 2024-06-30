/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.instrucciones.listas;

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
public class RemoveLista extends Instruccion{
    private String id;
    private Instruccion posicion;

    public RemoveLista(String id, Instruccion posicion, int linea, int columna) {
        super(new Tipo(TipoDato.VOID), linea, columna);
        this.id = id;
        this.posicion = posicion;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var lista = tabla.getVariable(id);
        
        if (lista == null) {
            return new Errores("SEMANTICO", "La lista \" " + this.id+" \" no existe",
                    this.linea, this.columna);
        }
        var p = this.posicion.interpretar(arbol, tabla);
        
        if(p instanceof Errores){
            return p;
        }
        
        if(this.posicion.tipo.getTipo() != TipoDato.ENTERO){
            return new  Errores("SEMANTICO", "La posicion de la lista \" " + this.id+" \" no es del tipo de dato entero",
                    this.linea, this.columna);
        }
        
        var valor = lista.getValor();
        
        if(valor instanceof List){
            if((int)p>=((List<Object>) valor).size() ){
                return new  Errores("SEMANTICO", "La posicion  \" " + p+" \" es mayor que la longitud de la lista",
                    this.linea, this.columna);
            }
            this.tipo.setTipo(lista.getTipo().getTipo());
            var resultado =  ((List<Object>) valor).get((int)p);
            ((List<Object>) valor).remove((int)p);
            
            return resultado;
        }else{
            return new Errores("SEMANTICO", "La variable \" " + this.id+" \" no es una lista",
                    this.linea, this.columna);
        }
        
    }
    
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        //<ID>.remove( <EXPRESION>)

        String stAsig = "n" + arbol.getContador();
        String idV = "n" + arbol.getContador();
        String punto = "n" + arbol.getContador();
        String cN = "n" + arbol.getContador();
        String par1 = "n" + arbol.getContador();
        String exp = "n" + arbol.getContador();
        String par2 = "n" + arbol.getContador();
        
        String resultado = anterior+" ->"+stAsig+";\n"; 
        
        resultado += stAsig + "[label=\"REMOVE\"];\n";
        resultado += idV + "[label=\""+this.id+"\"];\n";
        resultado += punto + "[label=\".\"];\n";
        resultado += cN + "[label=\"remove\"];\n";
        resultado += par1 + "[label=\"(\"];\n";
        resultado += exp + "[label=\"POS\"];\n";
        resultado += par2 + "[label=\")\"];\n";
        
        resultado += stAsig + " ->" + idV + ";\n";
        resultado += stAsig + " ->" + punto + ";\n";
        resultado += stAsig + " ->" + cN + ";\n";
        resultado += stAsig + " ->" + par1 + ";\n";
        resultado += stAsig + " ->" + exp + ";\n";
        resultado += stAsig + " ->" + par2 + ";\n";
        
        return resultado += this.posicion.generarast(arbol, exp);
    }
    
    
}
