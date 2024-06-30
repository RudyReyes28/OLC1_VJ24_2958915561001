/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.instrucciones.sentenciasCiclicas;

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
public class SentenciaDoWhile extends Instruccion{
    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;

    public SentenciaDoWhile(Instruccion condicion, LinkedList<Instruccion> instrucciones, int linea, int columna) {
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
        
        
        do{
            var newTabla = new TablaSimbolos(tabla);
            newTabla.setNombre(tabla.getNombre()+"-do:while");
            arbol.agregarTablaEntorno(newTabla);

            //ejecutar instrucciones
            for (var i : this.instrucciones) {
                if (i instanceof SentenciaBreak) {
                    return null;
                }
                
                if (i instanceof SentenciaContinue) {
                    break;
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
                var resIns = i.interpretar(arbol, newTabla);
                if (resIns instanceof SentenciaBreak) {
                    return null;
                }
                
                if (resIns instanceof SentenciaContinue) {
                    break;
                }
                
                if(resIns instanceof FuncionReturn){
                    return resIns;
                }
                
                if (resIns instanceof Errores) {
                    return resIns;
                }
            }
            
            
        }while((boolean)this.condicion.interpretar(arbol, tabla));
        
        
        return null;
    }
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        /*do {
[ <INSTRUCCIONES> ]
} while ( <EXPRESION> ) ;
*/  
        String stWhile = "n" + arbol.getContador();
        String doN = "n" + arbol.getContador();
        String llave1 = "n" + arbol.getContador();
        String inst = "n" + arbol.getContador();
        String llave2 = "n" + arbol.getContador();
        String whileN = "n" + arbol.getContador();
        String par1 = "n" + arbol.getContador();
        String asig = "n" + arbol.getContador();
        String par2 = "n" + arbol.getContador();
        String pC = "n" + arbol.getContador();
        
        String resultado = anterior+" ->"+stWhile+";\n"; 
        
        
        resultado += stWhile + "[label=\"CICLO DO-WHILE\"];\n";
        resultado += doN + "[label=\"do\"];\n";
        resultado += llave1 + "[label=\"{\"];\n";
        resultado += inst + "[label=\"INSTRUCCIONES\"];\n";
        resultado += llave2 + "[label=\"}\"];\n";
        resultado += whileN + "[label=\"while\"];\n";
        resultado += par1 + "[label=\"(\"];\n";
        resultado += asig + "[label=\"COND\"];\n";
        resultado += par2 + "[label=\")\"];\n";
        resultado += pC + "[label=\";\"];\n";
        
        resultado += stWhile + " ->" + doN + ";\n";
        resultado += stWhile + " ->" + llave1 + ";\n";
        resultado += stWhile + " ->" + inst + ";\n";
        resultado += stWhile + " ->" + llave2 + ";\n";
        resultado += stWhile + " ->" + whileN + ";\n";
        resultado += stWhile + " ->" + par1 + ";\n";
        resultado += stWhile + " ->" + asig + ";\n";
        resultado += stWhile + " ->" + par2 + ";\n";
        resultado += stWhile + " ->" + pC + ";\n";
        
        resultado += this.condicion.generarast(arbol, asig);
        
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
