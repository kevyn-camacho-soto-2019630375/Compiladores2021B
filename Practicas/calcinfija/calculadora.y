%{
	#include <stdio.h>
	#include <math.h>
	void yyerror(char *mensaje){
		printf("Error: %s\n",mensaje);
	}
%}

%define api.value.type {double}
%token NUM
%left '-' '+'
%left '*' '/'
%precedence NEG
%right '^'

%%
entrada:
 %empty
| entrada linea
;
linea:
     '\n'
| exp '\n' {printf ("\t%.10g\n",$1);}
;
exp:
   NUM
| exp '+' exp	{ $$ = $1 + $3; }
| exp '-' exp	{ $$ = $1 - $3; }
| exp '*' exp	{ $$ = $1 * $3; }
| exp '/' exp	{ $$ = $1 / $3; }
| '-' exp %prec NEG { $$ = -$2; }
| '(' exp ')'	{ $$ = $2; }
;
%%

int main(){
	yyparse();
	return 0;
}









