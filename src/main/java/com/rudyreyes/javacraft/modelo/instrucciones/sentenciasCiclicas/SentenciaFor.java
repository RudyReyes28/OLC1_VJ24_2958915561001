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
public class SentenciaFor extends Instruccion{
    private Instruccion asignacion;
    private Instruccion condicion;
    private Instruccion actualizacion;
    private LinkedList<Instruccion> instrucciones;

    public SentenciaFor(Instruccion asignacion, Instruccion condicion, Instruccion actualizacion, LinkedList<Instruccion> instrucciones, int linea, int col) {
        super(new Tipo(TipoDato.VOID), linea, col);
        this.asignacion = asignacion;
        this.condicion = condicion;
        this.actualizacion = actualizacion;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        //creamos un nuevo entorno
        var newTabla = new TablaSimbolos(tabla);
        newTabla.setNombre(tabla.getNombre()+"-for");
        arbol.agregarTablaEntorno(newTabla);

        // asignacion/declaracion
        var asignacion = this.asignacion.interpretar(arbol, newTabla);
        if (asignacion instanceof Errores) {
            return asignacion;
        }

        //validar la condicion -> Booleano
        var cond = this.condicion.interpretar(arbol, newTabla);
        if (cond instanceof Errores) {
            return cond;
        }

        if (this.condicion.tipo.getTipo() != TipoDato.BOOLEANO) {
            return new Errores("SEMANTICO", "La condicion debe ser bool",
                    this.linea, this.columna);
        }

        while ((boolean) this.condicion.interpretar(arbol, newTabla)) {
            //nuevo entorno
            var newTabla2 = new TablaSimbolos(newTabla);
            newTabla2.setNombre(tabla.getNombre()+"-for");
            arbol.agregarTablaEntorno(newTabla2);

            //ejecutar instrucciones
            for (var i : this.instrucciones) {
                if (i instanceof SentenciaBreak) {
                    return null;
                }
                if (i instanceof SentenciaContinue) {
                    break;
                }
                
                if (i instanceof FuncionReturn) {
                    var res = i.interpretar(arbol, newTabla2);
                    if (res instanceof Errores) {
                        return res;
                    }
                    return res;
                }
                
                if(i == null){
                    continue;
                }
                
                var resIns = i.interpretar(arbol, newTabla2);
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

            //actualizar la variable
            var act = this.actualizacion.interpretar(arbol, newTabla);
            if (act instanceof Errores) {
                return act;
            }
        }
        return null;
    }
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        /*for ( <ASIGNACIÓN> ; <CONDICIÓN> ; <ACTUALIZACIÓN> ) {
<INSTRUCCIONES>
}
*/
        String stFor = "n" + arbol.getContador();
        String forN = "n" + arbol.getContador();
        String par1 = "n" + arbol.getContador();
        String asig = "n" + arbol.getContador();
        String pc1 = "n" + arbol.getContador();
        String cond = "n" + arbol.getContador();
        String pc2 = "n" + arbol.getContador();
        String act = "n" + arbol.getContador();
        String par2 = "n" + arbol.getContador();
        String llave1 = "n" + arbol.getContador();
        String inst = "n" + arbol.getContador();
        String llave2 = "n" + arbol.getContador();
        
        String resultado = anterior+" ->"+stFor+";\n"; 
        
        resultado += stFor + "[label=\"CICLO FOR\"];\n";
        resultado += forN + "[label=\"for\"];\n";
        resultado += par1 + "[label=\"(\"];\n";
        resultado += asig + "[label=\"ASIG\"];\n";
        resultado += pc1 + "[label=\";\"];\n";
        resultado += cond + "[label=\"COND\"];\n";
        resultado += pc2 + "[label=\";\"];\n";
        resultado += act + "[label=\"ACTU\"];\n";
        resultado += par2 + "[label=\")\"];\n";
        resultado += llave1 + "[label=\"{\"];\n";
        resultado += inst + "[label=\"INSTRUCCIONES\"];\n";
        resultado += llave2 + "[label=\"}\"];\n";
        
        resultado += stFor + " ->" + forN + ";\n";
        resultado += stFor + " ->" + par1 + ";\n";
        resultado += stFor + " ->" + asig + ";\n";
        resultado += stFor + " ->" + pc1 + ";\n";
        resultado += stFor + " ->" + cond + ";\n";
        resultado += stFor + " ->" + pc2 + ";\n";
        resultado += stFor + " ->" + act + ";\n";
        resultado += stFor + " ->" + par2 + ";\n";
        resultado += stFor + " ->" + llave1 + ";\n";
        resultado += stFor + " ->" + inst + ";\n";
        resultado += stFor + " ->" + llave2 + ";\n";
        
        resultado += this.asignacion.generarast(arbol, asig);
        resultado += this.condicion.generarast(arbol, cond);
        resultado += this.actualizacion.generarast(arbol, act);
        
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
