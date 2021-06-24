D	[0-9]
L	[a-zA-Z_]
H	[a-fA-F0-9]
E	[Ee][+-]?{D}+
FS	(f|F|l|L)
IS	(u|U|l|L)*
%{
	#include <stdlib.h>
	#include "proyecto.tab.h"
%}

%%
"char"	{ return(CHAR);}
"do"	{return(DO);}
"double"	{return(DOUBLE);}
"else"	{return(ELSE);}
"float"	{return(FLOAT);}
"if"	{return(IF);}
"int"	{return(INT);}
"long"	{return(LONG);}
"short"	{return(SHORT);}
"while"	{return(WHILE);}

{L}({L}|{D})*	{return(IDENTIFICADOR);}
0[xX]{H}+{IS}?	{return(CONSTANTE);}
0{D}+{IS}?	{return(CONSTANTE);}
{D}+{IS}?	{return(CONSTANTE);}
L?'(\\.|[^\\'])+'	{return(CONSTANTE);}
{D}+{E}{FS}?	{return(CONSTANTE);}
{D}*"."{D}+({E})?{FS}?	{return(CONSTANTE);}
{D}+"."{D}*({E})?{FS}?	{return(CONSTANTE);}
L?\"(\\.|[^\\"])*\"	{return(STRING_LITERAL);}
"=="	{return(EQ_OP);}
";"	{return(';');}
("{"|"<%")	{return('{');}
("}"|"%>")	{return('}');}
","	{return(',');}
":"	{return(':');}
"="	{return('=');}
"-"	{return('-');}
"+"	{return('+');}
"*"	{return('*');}
"/"	{return('/');}
"<"	{return('<');}
">"	{return('>');}

[ \t\v\n\f]	{}
.


 
