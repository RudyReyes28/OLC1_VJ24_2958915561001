/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.instrucciones.sentenciaControl;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import com.rudyreyes.javacraft.modelo.errores.Errores;
import com.rudyreyes.javacraft.modelo.instrucciones.metodos.FuncionReturn;
import com.rudyreyes.javacraft.modelo.instrucciones.sentenciasTransferencia.SentenciaBreak;
import com.rudyreyes.javacraft.modelo.instrucciones.sentenciasTransferencia.SentenciaContinue;
import com.rudyreyes.javacraft.modelo.simbolo.Arbol;
import com.rudyreyes.javacraft.modelo.simbolo.TablaSimbolos;
import com.rudyreyes.javacraft.modelo.simbolo.Tipo;
import com.rudyreyes.javacraft.modelo.simbolo.TipoDato;
import java.util.LinkedList;

/**
 *
 * @author rudyo
 */
public class SentenciaIf extends Instruccion {
    
    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;

    public SentenciaIf(Instruccion condicion, LinkedList<Instruccion> instrucciones, int linea, int columna) {
        super(new Tipo(TipoDato.VOID), linea, columna);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        
        var cond = this.condicion.interpretar(arbol, tabla);
        if (cond instanceof Errores) {
            return cond;
        }

        // ver que cond sea booleano
        if (this.condicion.tipo.getTipo() != TipoDato.BOOLEANO) {
            return new Errores("SEMANTICO", "Expresion invalida",
                    this.linea, this.columna);
        }
        
        var newTabla = new TablaSimbolos(tabla);
        newTabla.setNombre(tabla.getNombre()+"-if");
        arbol.agregarTablaEntorno(newTabla);
        if ((boolean) cond) {
            for (var i : this.instrucciones) {
                if (i instanceof SentenciaBreak) {
                    return i;
                }
                
                if (i instanceof SentenciaContinue) {
                    return i;
                }

                if (i instanceof FuncionReturn) {
                    var res = i.interpretar(arbol, newTabla);
                    if (res instanceof Errores) {
                        return res;
                    }
                    return res;
                }
                
                if(i == null){
                    continue;
                }
                
                var resultado = i.interpretar(arbol, newTabla);
                if (resultado instanceof SentenciaBreak) {
                    return resultado;
                }
                
                if(resultado instanceof FuncionReturn){
                    return resultado;
                }
                
                if (resultado instanceof SentenciaContinue) {
                    return resultado;
                }
                
                if (resultado instanceof Errores) {
                    return resultado;
                }
                /*
                    Manejo de errores
                */
            }
        }
        
        return null;
    }
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        //IF (EXP) {INSTRUCCIONES}
        
        String stIf = "n" + arbol.getContador();
        String ifN = "n" + arbol.getContador();
        String par1 = "n" + arbol.getContador();
        String exp = "n" + arbol.getContador();
        String par2 = "n" + arbol.getContador();
        String llave1 = "n" + arbol.getContador();
        String inst = "n" + arbol.getContador();
        String llave2 = "n" + arbol.getContador();
        
        String resultado = anterior+" ->"+stIf+";\n"; 
        resultado += stIf + "[label=\"SentenciaIf\"];\n";
        resultado += ifN + "[label=\"if\"];\n";
        resultado += par1 + "[label=\"(\"];\n";
        resultado += exp + "[label=\"EXP\"];\n";
        resultado += par2 + "[label=\")\"];\n";
        resultado += llave1 + "[label=\"{\"];\n";
        resultado += inst + "[label=\"INSTRUCCIONES\"];\n";
        resultado += llave2 + "[label=\"}\"];\n";
        
        resultado += stIf + " ->" + ifN + ";\n";
        resultado += stIf + " ->" + par1 + ";\n";
        resultado += stIf + " ->" + exp + ";\n";
        resultado += stIf + " ->" + par2 + ";\n";
        resultado += stIf + " ->" + llave1 + ";\n";
        resultado += stIf + " ->" + inst + ";\n";
        resultado += stIf + " ->" + llave2 + ";\n";
        
        resultado += this.condicion.generarast(arbol, exp);
        
        for (var i : this.instrucciones) {
            if(i ==null ){
                continue;
            }
            
            String nodoAux = "n" + arbol.getContador();
                resultado += nodoAux + "[label=\"INSTRUCCION\"];\n";
                resultado += inst + "-> " + nodoAux + ";\n";
                resultado += i.generarast(arbol, nodoAux);
        }
        
        return resultado;
    }
    
}
