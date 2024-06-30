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
public class FuncionLength extends Instruccion{
    private Instruccion expresion;

    public FuncionLength(Instruccion expresion, int linea, int columna) {
        super(new Tipo(TipoDato.ENTERO), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var resultado = expresion.interpretar(arbol, tabla);
        
        if(resultado instanceof Errores){
            return resultado;
        }
        
        if(resultado instanceof Object []){
            return ((Object[]) resultado).length;
        }
        
        if(resultado instanceof Object [][]){
            return ((Object[][]) resultado).length;
        }
        
        if(resultado instanceof List){
            return ((List) resultado).size();
        }
        
        if(expresion.tipo.getTipo() == TipoDato.CADENA){
            return ((String) resultado).length();
        }
        
        return new Errores("SEMANTICO", "El tipo de dato no es apropiado para la funcion length", linea, columna);

        
    }
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        String stAsig = "n" + arbol.getContador();
        String cN = "n" + arbol.getContador();
        String par1 = "n" + arbol.getContador();
        String exp = "n" + arbol.getContador();
        String par2 = "n" + arbol.getContador();
        
        String resultado = anterior+" ->"+stAsig+";\n"; 
        
        resultado += stAsig + "[label=\"FUNCION LENGTH\"];\n";
        resultado += cN + "[label=\"length\"];\n";
        resultado += par1 + "[label=\"(\"];\n";
        resultado += exp + "[label=\"EXP\"];\n";
        resultado += par2 + "[label=\")\"];\n";
        
        resultado += stAsig + " ->" + cN + ";\n";
        resultado += stAsig + " ->" + par1 + ";\n";
        resultado += stAsig + " ->" + exp + ";\n";
        resultado += stAsig + " ->" + par2 + ";\n";
        
        return resultado += this.expresion.generarast(arbol, exp);
    }

}
    
    
