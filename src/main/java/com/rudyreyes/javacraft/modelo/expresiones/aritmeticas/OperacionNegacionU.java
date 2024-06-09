/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.expresiones.aritmeticas;

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
public class OperacionNegacionU extends Instruccion {
    private Instruccion operandoUnico;

    public OperacionNegacionU(Instruccion operandoUnico, int linea, int columna) {
        super(new Tipo(TipoDato.ENTERO), linea, columna);
        this.operandoUnico = operandoUnico;
    
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        Object unico = null;
        unico = this.operandoUnico.interpretar(arbol, tabla);
        if (unico instanceof Errores) {
              return unico;
        }
        
        return negacion(unico);
    }
    
    public Object negacion(Object op1) {
        var opU = this.operandoUnico.tipo.getTipo();
        switch (opU) {
            case ENTERO -> {
                this.tipo.setTipo(TipoDato.ENTERO);
                return (int) op1 * -1;
            }
            case DECIMAL -> {
                this.tipo.setTipo(TipoDato.DECIMAL);
                return (double) op1 * -1;
            }
            default -> {
                return new Errores("SEMANTICO", "Negacion erronea", this.linea, this.columna);
            }
        }
    }

   
    
    
    
    
}
