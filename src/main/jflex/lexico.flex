package com.rudyreyes.javacraft.controlador.analisis;
//importaciones
import java_cup.runtime.Symbol;
import java.util.LinkedList;
import com.rudyreyes.javacraft.modelo.errores.*;

%%

//codigo de usuario
%{
    public LinkedList<Errores> listaErrores = new LinkedList<>();
%}

%init{
    yyline = 1;
    yycolumn = 1;
    listaErrores = new LinkedList<>();
%init}

//caracteristicas de jflex
%cup
%class scanner
%public
%line
%char
%column
%full
//%debug
%ignorecase

//simbolos del sistema
PAR1="("
PAR2=")"
MAS="+"
MENOS="-"
MULT = "*"
DIV = "/"
MOD = "%"
POTENCIA = [*]{2}
FINCADENA=";"

//simbolos de op relacionales
DOBLEIGUAL = "=="
DIFERENTE = "!="
MENOR = "<"
MENORIGUAL = "<="
MAYOR = ">"
MAYORIGUAL = ">="

//operaciones logicas
OR = "||"
AND = "&&"
XOR = "^"
NOT = "!"



BLANCOS=[\ \r\t\f\n]+

//tipo de datos

ENTERO=[0-9]+
DECIMAL=[0-9]+"."[0-9]+
CADENA = [\"](\\\"|[^\"])*[\"]
BOOLEANO = true|false
CARACTER = '([^\\'\\n\\r]|\\.)'

//comentarios
COMENTARIO = ([/]{2}(.*))
COMENTARIOMULTI = [/][*]([^/*]*)[*][/]
//palabras reservadas
IMPRIMIR="println"
INT = "int"
CHAR = "char"
DOUBLE = "double"

%%
<YYINITIAL> {IMPRIMIR} {return new Symbol(sym.IMPRIMIR, yyline, yycolumn,yytext());}
<YYINITIAL> {INT} {return new Symbol(sym.INT, yyline, yycolumn,yytext());}
<YYINITIAL> {CHAR} {return new Symbol(sym.CHAR, yyline, yycolumn,yytext());}
<YYINITIAL> {DOUBLE} {return new Symbol(sym.DOUBLE, yyline, yycolumn,yytext());}

<YYINITIAL> {DECIMAL} {return new Symbol(sym.DECIMAL, yyline, yycolumn,yytext());}
<YYINITIAL> {ENTERO} {return new Symbol(sym.ENTERO, yyline, yycolumn,yytext());}
<YYINITIAL> {BOOLEANO} {return new Symbol(sym.BOOLEAN, yyline, yycolumn,yytext());}

<YYINITIAL> {CADENA} {
    String cadena = yytext();
    cadena = cadena.substring(1, cadena.length()-1);
    return new Symbol(sym.CADENA, yyline, yycolumn,cadena);
    }

<YYINITIAL> {CARACTER} {
    String caracter = yytext();
    caracter = caracter.substring(1, caracter.length()-1);
    return new Symbol(sym.CARACTER, yyline, yycolumn,caracter);
    }



<YYINITIAL> {FINCADENA}     {return new Symbol(sym.FINCADENA, yyline, yycolumn,yytext());}
<YYINITIAL> {PAR1}          {return new Symbol(sym.PAR1, yyline, yycolumn,yytext());}
<YYINITIAL> {PAR2}          {return new Symbol(sym.PAR2, yyline, yycolumn,yytext());}

<YYINITIAL> {MAS}           {return new Symbol(sym.MAS, yyline, yycolumn,yytext());}
<YYINITIAL> {MENOS}         {return new Symbol(sym.MENOS, yyline, yycolumn,yytext());}
<YYINITIAL> {POTENCIA}      {return new Symbol(sym.POTENCIA, yyline, yycolumn,yytext());}
<YYINITIAL> {MULT}          {return new Symbol(sym.MULT, yyline, yycolumn,yytext());}
<YYINITIAL> {DIV}           {return new Symbol(sym.DIV, yyline, yycolumn,yytext());}
<YYINITIAL> {MOD}           {return new Symbol(sym.MOD, yyline, yycolumn,yytext());}

<YYINITIAL> {DOBLEIGUAL}    {return new Symbol(sym.DOBLEIGUAL, yyline, yycolumn,yytext());}
<YYINITIAL> {DIFERENTE}     {return new Symbol(sym.DIFERENTE, yyline, yycolumn,yytext());}
<YYINITIAL> {MENOR}         {return new Symbol(sym.MENOR, yyline, yycolumn,yytext());}
<YYINITIAL> {MENORIGUAL}    {return new Symbol(sym.MENORIGUAL, yyline, yycolumn,yytext());}
<YYINITIAL> {MAYOR}         {return new Symbol(sym.MAYOR, yyline, yycolumn,yytext());}
<YYINITIAL> {MAYORIGUAL}    {return new Symbol(sym.MAYORIGUAL, yyline, yycolumn,yytext());}


<YYINITIAL> {OR}    {return new Symbol(sym.OR, yyline, yycolumn,yytext());}
<YYINITIAL> {AND}    {return new Symbol(sym.AND, yyline, yycolumn,yytext());}
<YYINITIAL> {XOR}    {return new Symbol(sym.XOR, yyline, yycolumn,yytext());}
<YYINITIAL> {NOT}    {return new Symbol(sym.NOT, yyline, yycolumn,yytext());}

<YYINITIAL> {BLANCOS} {}
<YYINITIAL> {COMENTARIO} {}
<YYINITIAL> {COMENTARIOMULTI} {}


<YYINITIAL> . {
                listaErrores.add(new Errores("LEXICO","El caracter \" "+
                yytext()+" \" NO pertenece al lenguaje", yyline, yycolumn));
}