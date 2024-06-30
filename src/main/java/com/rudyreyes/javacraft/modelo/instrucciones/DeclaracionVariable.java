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
                    valor =  new Nativo(0, new Tipo(TipoDato.ENTERO), linea, columna-1 );
                    break;
                case DECIMAL:
                    valor =  new Nativo(0.0, new Tipo(TipoDato.DECIMAL), linea, columna-1 );
                    break;
                case CADENA:
                    valor =  new Nativo("", new Tipo(TipoDato.CADENA), linea, columna-1 );
                    break;
                case CARACTER:
                    valor =  new Nativo('0', new Tipo(TipoDato.CARACTER), linea, columna-1 );
                    break;
                case BOOLEANO:
                    valor =  new Nativo(true, new Tipo(TipoDato.BOOLEANO), linea, columna-1 );
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

        Simbolo s = new Simbolo(mutabilidad,this.tipo, this.identificador, valorInterpretado, this.linea, this.columna);

        boolean creacion = tabla.setVariable(s);
        if (!creacion) {
            return new Errores("SEMANTICO", "La variable \""+this.identificador+"\" ya existe", this.linea, this.columna);
        }

        return null;
    }
    
    
    
    @Override
    public String generarast(Arbol arbol, String anterior) {
        //<MUTABILIDAD> <ID> : <TIPO> ;
        String declaracion = "n" + arbol.getContador();
        String mut = "n" + arbol.getContador();
        String idN = "n" + arbol.getContador();
        String dosP = "n" + arbol.getContador();
        String tipoN= "n" + arbol.getContador();
        String igual= "n" + arbol.getContador();
        String expN = "n" + arbol.getContador();
        String fin = "n" + arbol.getContador();
        
        String resultado = anterior+" ->"+declaracion+";\n";
        
        resultado += declaracion + "[label=\"Declaracion Variable\"];\n";
        resultado += mut + "[label=\""+ ((this.mutabilidad)? "var":"const") +"\"];\n";
        resultado += idN + "[label=\""+this.identificador+"\"];\n";
        resultado += dosP + "[label=\":\"];\n";
        resultado += tipoN + "[label=\""+this.tipo.getTipo().toString()+"\"];\n";
        resultado += igual + "[label=\"=\"];\n";
        resultado += expN + "[label=\"EXPRESION\"];\n";
        resultado += fin + "[label=\";\"];\n";
        
        resultado += declaracion + " ->" + mut + ";\n";
        resultado += declaracion + " ->" + idN + ";\n";
        resultado += declaracion + " ->" + dosP + ";\n";
        resultado += declaracion + " ->" + tipoN + ";\n";
        resultado += declaracion + " ->" + igual + ";\n";
        resultado += declaracion + " ->" + expN + ";\n";
        resultado += declaracion + " ->" + fin + ";\n";
        
        
        return resultado += this.valor.generarast(arbol, expN);
        
    }
    
}
