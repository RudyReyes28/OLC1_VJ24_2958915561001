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
DOSPUNTOS = ":"
IGUAL = "="
LLAVE1="{"
LLAVE2="}"
CORCHETE1 = "["
CORCHETE2 = "]"
COMA = ","
PUNTO = "."

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
ID = [a-zA-Z][a-zA-Z0-9_]*

//comentarios
COMENTARIO = ([/]{2}(.*))
COMENTARIOMULTI = "/*"([^*]|\*[^/])*"\*/"

//palabras reservadas
IMPRIMIR="println"
INT = "int"
CHAR = "char"
DOUBLE = "double"
STRING = "String"
BOOL = "bool"
CONST = "const"
VAR = "var"
IF = "if"
ELSE = "else"
MATCH = "match"
FLECHA = "=>"
DEFAULT = "_"
FOR = "for"
WHILE = "while"
DO = "do"
BREAK = "break"
CONTINUE = "continue"
LIST = "list"
NEW = "new"
APPEND = "append"
REMOVE = "remove"
ROUND = "round"
LENGTH ="length"
TOSTRING = "tostring"
FIND = "find"
VOID = "void"
STARTWITH = "start_with"
RETURN = "return"
%%
<YYINITIAL> {IMPRIMIR}  {return new Symbol(sym.IMPRIMIR, yyline, yycolumn,yytext());}
<YYINITIAL> {CONST}     {return new Symbol(sym.CONST, yyline, yycolumn,yytext());}
<YYINITIAL> {VAR}       {return new Symbol(sym.VAR, yyline, yycolumn,yytext());}
<YYINITIAL> {INT}       {return new Symbol(sym.INT, yyline, yycolumn,yytext());}
<YYINITIAL> {CHAR}      {return new Symbol(sym.CHAR, yyline, yycolumn,yytext());}
<YYINITIAL> {DOUBLE}    {return new Symbol(sym.DOUBLE, yyline, yycolumn,yytext());}
<YYINITIAL> {STRING}    {return new Symbol(sym.STRING, yyline, yycolumn,yytext());}
<YYINITIAL> {BOOL}      {return new Symbol(sym.BOOL, yyline, yycolumn,yytext());}
<YYINITIAL> {IF}        {return new Symbol(sym.IF, yyline, yycolumn,yytext());}
<YYINITIAL> {ELSE}        {return new Symbol(sym.ELSE, yyline, yycolumn,yytext());}
<YYINITIAL> {MATCH}        {return new Symbol(sym.MATCH, yyline, yycolumn,yytext());}
<YYINITIAL> {FLECHA}        {return new Symbol(sym.FLECHA, yyline, yycolumn,yytext());}
<YYINITIAL> {DEFAULT}        {return new Symbol(sym.DEFAULT, yyline, yycolumn,yytext());}
<YYINITIAL> {FOR}        {return new Symbol(sym.FOR, yyline, yycolumn,yytext());}
<YYINITIAL> {WHILE}        {return new Symbol(sym.WHILE, yyline, yycolumn,yytext());}
<YYINITIAL> {DO}        {return new Symbol(sym.DO, yyline, yycolumn,yytext());}
<YYINITIAL> {BREAK}        {return new Symbol(sym.BREAK, yyline, yycolumn,yytext());}
<YYINITIAL> {CONTINUE}        {return new Symbol(sym.CONTINUE, yyline, yycolumn,yytext());}
<YYINITIAL> {LIST}        {return new Symbol(sym.LIST, yyline, yycolumn,yytext());}
<YYINITIAL> {NEW}        {return new Symbol(sym.NEW, yyline, yycolumn,yytext());}
<YYINITIAL> {APPEND}        {return new Symbol(sym.APPEND, yyline, yycolumn,yytext());}
<YYINITIAL> {REMOVE}        {return new Symbol(sym.REMOVE, yyline, yycolumn,yytext());}
<YYINITIAL> {ROUND}        {return new Symbol(sym.ROUND, yyline, yycolumn,yytext());}
<YYINITIAL> {LENGTH}        {return new Symbol(sym.LENGTH, yyline, yycolumn,yytext());}
<YYINITIAL> {TOSTRING}        {return new Symbol(sym.TOSTRING, yyline, yycolumn,yytext());}
<YYINITIAL> {FIND}        {return new Symbol(sym.FIND, yyline, yycolumn,yytext());}
<YYINITIAL> {VOID}        {return new Symbol(sym.VOID, yyline, yycolumn,yytext());}
<YYINITIAL> {STARTWITH}        {return new Symbol(sym.STARTWITH, yyline, yycolumn,yytext());}
<YYINITIAL> {RETURN}        {return new Symbol(sym.RETURN, yyline, yycolumn,yytext());}

<YYINITIAL> {DECIMAL}   {return new Symbol(sym.DECIMAL, yyline, yycolumn,yytext());}
<YYINITIAL> {ENTERO}    {return new Symbol(sym.ENTERO, yyline, yycolumn,yytext());}
<YYINITIAL> {BOOLEANO}  {return new Symbol(sym.BOOLEAN, yyline, yycolumn,yytext());}



<YYINITIAL> {ID}        {return new Symbol(sym.ID, yyline, yycolumn,yytext());}


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
<YYINITIAL> {LLAVE1}        {return new Symbol(sym.LLAVE1, yyline, yycolumn,yytext());}
<YYINITIAL> {LLAVE2}        {return new Symbol(sym.LLAVE2, yyline, yycolumn,yytext());}
<YYINITIAL> {CORCHETE1}        {return new Symbol(sym.CORCHETE1, yyline, yycolumn,yytext());}
<YYINITIAL> {CORCHETE2}        {return new Symbol(sym.CORCHETE2, yyline, yycolumn,yytext());}
<YYINITIAL> {COMA}        {return new Symbol(sym.COMA, yyline, yycolumn,yytext());}
<YYINITIAL> {PUNTO}        {return new Symbol(sym.PUNTO, yyline, yycolumn,yytext());}
<YYINITIAL> {DOSPUNTOS}     {return new Symbol(sym.DOSPUNTOS, yyline, yycolumn,yytext());}

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

<YYINITIAL> {IGUAL}    {return new Symbol(sym.IGUAL, yyline, yycolumn,yytext());}


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