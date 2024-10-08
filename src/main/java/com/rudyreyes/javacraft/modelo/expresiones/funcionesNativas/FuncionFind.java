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
import java.util.List;

/**
 *
 * @author rudyo
 */
public class FuncionFind extends Instruccion{
    
    private String id;
    private Instruccion instruccion;

    public FuncionFind(String id, Instruccion instruccion, int linea, int columna) {
        super(new Tipo(TipoDato.BOOLEANO), linea, columna);
        this.id = id;
        this.instruccion = instruccion;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var simbolo = tabla.getVariable(id);
        
        if(simbolo == null){
            return new Errores("SEMANTICO", "El vector \" " + this.id+" \" no existe",
                    this.linea, this.columna);
        }
        
        var buscar = this.instruccion.interpretar(arbol, tabla);
        
        if(buscar instanceof Errores){
            return buscar;
        }
        
        var vectorL = simbolo.getValor();
        
        if(vectorL instanceof Object [][]){
            Object [][] vec = (Object[][]) vectorL;
            
            int fila = vec.length;
            
            for(int i = 0; i<fila; i++){
                int col = vec[i].length;
                

                for (int j = 0; j < col; j++) {
                    if (vec[i][j].equals(buscar)) {
                        return true;
                    }
                }
                
            }
            
            return false;
        }
        
        if(vectorL instanceof Object[]){
            Object [] vec = (Object[]) vectorL;
            int tamano = vec.length;
            for(int i = 0; i<tamano; i++){
                if(vec[i].equals(buscar)){
                    return true;
                }
                
            }
            return false;
        }
        
        
        
        if(vectorL instanceof List){
            return ((List<Object>)vectorL).contains(buscar);
        }
        
        
        return new Errores("Semantico", "El elemento no es un vector ni una lista", linea, columna);

    }
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        String stAsig = "n" + arbol.getContador();
        String idV = "n" + arbol.getContador();
        String punto = "n" + arbol.getContador();
        String cN = "n" + arbol.getContador();
        String par1 = "n" + arbol.getContador();
        String exp = "n" + arbol.getContador();
        String par2 = "n" + arbol.getContador();
        
        String resultado = anterior+" ->"+stAsig+";\n"; 
        
        resultado += stAsig + "[label=\"FUNCION FIND\"];\n";
        resultado += idV + "[label=\""+this.id+"\"];\n";
        resultado += punto + "[label=\".\"];\n";
        resultado += cN + "[label=\"find\"];\n";
        resultado += par1 + "[label=\"(\"];\n";
        resultado += exp + "[label=\"EXP\"];\n";
        resultado += par2 + "[label=\")\"];\n";
        
        resultado += stAsig + " ->" + idV + ";\n";
        resultado += stAsig + " ->" + punto + ";\n";
        resultado += stAsig + " ->" + cN + ";\n";
        resultado += stAsig + " ->" + par1 + ";\n";
        resultado += stAsig + " ->" + exp + ";\n";
        resultado += stAsig + " ->" + par2 + ";\n";
        
        return resultado += this.instruccion.generarast(arbol, exp);
    }
    
}
