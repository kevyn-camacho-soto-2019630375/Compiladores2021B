%{
	#include <stdlib.h>
	#include "calculadora.tab.h"
%}

%%
[0-9]+	{yylval=atof(yytext); return NUM;}
[+]	{return '+';}
[-]	{return '-';}
[*]	{return '*';}
[/]	{return '/';}
\n	{return '\n';}
.

