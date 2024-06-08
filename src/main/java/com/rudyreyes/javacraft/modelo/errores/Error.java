/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rudyreyes.javacraft.modelo.errores;

/**
 *
 * @author rudyo
 */
public class Error {
    
    private String tipo;
    private String descripcion;
    private String linea;
    private String columna;

    public Error(String tipo, String descripcion, String linea, String columna) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.linea = linea;
        this.columna = columna;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    @Override
    public String toString() {
        return "Error{" + "tipo=" + tipo + ", descripcion=" + descripcion + ", linea=" + linea + ", columna=" + columna + '}';
    }
    
    
    
}
