/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.instrucciones.metodos;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import com.rudyreyes.javacraft.modelo.errores.Errores;
import com.rudyreyes.javacraft.modelo.expresiones.Nativo;
import com.rudyreyes.javacraft.modelo.simbolo.Arbol;
import com.rudyreyes.javacraft.modelo.simbolo.TablaSimbolos;
import com.rudyreyes.javacraft.modelo.simbolo.Tipo;
import com.rudyreyes.javacraft.modelo.simbolo.TipoDato;

/**
 *
 * @author rudyo
 */
public class FuncionReturn extends Instruccion{
    public Instruccion instruccion;

    public FuncionReturn(Instruccion instruccion, int linea, int columna) {
        super(new Tipo(TipoDato.ENTERO), linea, columna);
        this.instruccion = instruccion;
    }

    public FuncionReturn(int linea, int columna) {
        super(new Tipo(TipoDato.ENTERO), linea, columna);
        this.instruccion = null;
    }
    
    

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var resultado = this.instruccion.interpretar(arbol, tabla);
        
        if(resultado instanceof Errores){
            return resultado;
        }
        this.tipo.setTipo(this.instruccion.tipo.getTipo());
        
        return new FuncionReturn( new Nativo(resultado, new Tipo(this.instruccion.tipo.getTipo()), linea, columna), linea, columna);
    }
    
    
    
    
    

    
}
