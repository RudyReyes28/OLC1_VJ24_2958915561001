/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.instrucciones;

import com.rudyreyes.javacraft.modelo.abstracto.Instruccion;
import com.rudyreyes.javacraft.modelo.errores.Errores;
import com.rudyreyes.javacraft.modelo.expresiones.Nativo;
import com.rudyreyes.javacraft.modelo.simbolo.Arbol;
import com.rudyreyes.javacraft.modelo.simbolo.Simbolo;
import com.rudyreyes.javacraft.modelo.simbolo.TablaSimbolos;
import com.rudyreyes.javacraft.modelo.simbolo.Tipo;
import com.rudyreyes.javacraft.modelo.simbolo.TipoDato;
import static com.rudyreyes.javacraft.modelo.simbolo.TipoDato.ENTERO;

/**
 *
 * @author rudyo
 */
public class DeclaracionVariable extends Instruccion{
    public boolean mutabilidad;
    public String identificador;
    public Instruccion valor;

    public DeclaracionVariable(boolean mutabilidad, String identificador, Instruccion valor, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.mutabilidad = mutabilidad;
        this.identificador = identificador;
        
        
        if(valor ==null){
            switch (tipo.getTipo()){
                case ENTERO:
                    valor =  new Nativo(0, new Tipo(TipoDato.ENTERO), linea-1, columna-1 );
                    break;
                case DECIMAL:
                    valor =  new Nativo(0.0, new Tipo(TipoDato.DECIMAL), linea-1, columna-1 );
                    break;
                case CADENA:
                    valor =  new Nativo("", new Tipo(TipoDato.CADENA), linea-1, columna-1 );
                    break;
                case CARACTER:
                    valor =  new Nativo('0', new Tipo(TipoDato.CARACTER), linea-1, columna-1 );
                    break;
                case BOOLEANO:
                    valor =  new Nativo(true, new Tipo(TipoDato.BOOLEANO), linea-1, columna-1 );
                    break;
            }
        }
        
        this.valor = valor;
    }

    

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var valorInterpretado = this.valor.interpretar(arbol, tabla);

        //validamos si es error
        if (valorInterpretado instanceof Errores) {
            return valorInterpretado;
        }

        //validamos los tipo
        if (this.valor.tipo.getTipo() != this.tipo.getTipo()) {
            return new Errores("SEMANTICO", "Tipo de dato erroneo, no se puede asignar el valor de tipo "+this.valor.tipo.getTipo()+ " a la variable de tipo "+ this.tipo.getTipo(), this.linea, this.columna);
        }

        Simbolo s = new Simbolo(mutabilidad,this.tipo, this.identificador, valorInterpretado);

        boolean creacion = tabla.setVariable(s);
        if (!creacion) {
            return new Errores("SEMANTICO", "La variable \""+this.identificador+"\" ya existe", this.linea, this.columna);
        }

        return null;
    }
    
    
    
    
    
}
