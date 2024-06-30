/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.expresiones.accesovector;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import com.rudyreyes.javacraft.modelo.errores.Errores;
import com.rudyreyes.javacraft.modelo.simbolo.Arbol;
import com.rudyreyes.javacraft.modelo.simbolo.TablaSimbolos;
import com.rudyreyes.javacraft.modelo.simbolo.Tipo;
import com.rudyreyes.javacraft.modelo.simbolo.TipoDato;

/**
 *
 * @author rudyo
 */
public class AccesoVectorDosDimensiones extends Instruccion{
    private String identificador;
    private Instruccion posicion1;
    private Instruccion posicion2;

    public AccesoVectorDosDimensiones(String identificador, Instruccion posicion1, Instruccion posicion2, int linea, int columna) {
        super(new Tipo(TipoDato.VOID), linea, columna);
        this.identificador = identificador;
        this.posicion1 = posicion1;
        this.posicion2 = posicion2;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var vector = tabla.getVariable(identificador);
        
        if (vector == null) {
            return new Errores("SEMANTICO", "El vector \" " + this.identificador+" \" no existe",
                    this.linea, this.columna);
        }
        
        var primeraP = posicion1.interpretar(arbol, tabla);
        if(primeraP instanceof Errores){
            return primeraP;
        }
        
        if(this.posicion1.tipo.getTipo() != TipoDato.ENTERO){
            return new  Errores("SEMANTICO", "La posicion vector \" " + this.identificador+" \" no es del tipo de dato entero",
                    this.linea, this.columna);
        }
        
        
        var segundaP = posicion2.interpretar(arbol, tabla);
        if(segundaP instanceof Errores){
            return segundaP;
        }
        
        if(this.posicion2.tipo.getTipo() != TipoDato.ENTERO){
            return new  Errores("SEMANTICO", "La posicion vector \" " + this.identificador+" \" no es del tipo de dato entero",
                    this.linea, this.columna);
        }
        
        var valor = vector.getValor();
        
        if(valor instanceof Object[][]){
            Object [][] resultado = (Object [][]) valor;
            
            if((int)primeraP>=resultado.length){
                return new  Errores("SEMANTICO", "La posicion  \" " + primeraP+" \" es mayor que la longitud del vector",
                    this.linea, this.columna);
            }
            
            if((int)segundaP>=resultado[(int)primeraP].length){
                return new  Errores("SEMANTICO", "La posicion  \" " + segundaP+" \" es mayor que la longitud del vector",
                    this.linea, this.columna);
            }
            this.tipo.setTipo(vector.getTipo().getTipo());
            return resultado[(int)primeraP][(int)segundaP];
        }
        
        return new  Errores("SEMANTICO", "La variable \" " + this.identificador+" \" no es un vector",
                    this.linea, this.columna);

    }

    @Override
    public String generarast(Arbol arbol, String anterior) {
        String stAsig = "n" + arbol.getContador();
        String idV = "n" + arbol.getContador();
        String par1 = "n" + arbol.getContador();
        String exp = "n" + arbol.getContador();
        String par2 = "n" + arbol.getContador();
        String par11 = "n" + arbol.getContador();
        String exp2 = "n" + arbol.getContador();
        String par22 = "n" + arbol.getContador();
        
        String resultado = anterior+" ->"+stAsig+";\n"; 
        
        resultado += stAsig + "[label=\"ACCESO VECTOR\"];\n";
        resultado += idV + "[label=\""+this.identificador+"\"];\n";
        resultado += par1 + "[label=\"[\"];\n";
        resultado += exp + "[label=\"POS\"];\n";
        resultado += par2 + "[label=\"]\"];\n";
        resultado += par11 + "[label=\"[\"];\n";
        resultado += exp2 + "[label=\"POS\"];\n";
        resultado += par22 + "[label=\"]\"];\n";
        
        resultado += stAsig + " ->" + idV + ";\n";
        resultado += stAsig + " ->" + par1 + ";\n";
        resultado += stAsig + " ->" + exp + ";\n";
        resultado += stAsig + " ->" + par2 + ";\n";
        resultado += stAsig + " ->" + par11 + ";\n";
        resultado += stAsig + " ->" + exp2 + ";\n";
        resultado += stAsig + " ->" + par22 + ";\n";
        resultado += this.posicion1.generarast(arbol, exp);
        resultado += this.posicion2.generarast(arbol, exp2);
        
        return resultado ;
    }
    
    
    
    
}
