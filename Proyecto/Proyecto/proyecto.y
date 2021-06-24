%{
	#include <stdio.h>
	void yyerror(char *mensaje){
		printf("Error: %s\n",mensaje);
	}
%}
%token IDENTIFICADOR CONSTANTE STRING_LITERAL
%token EQ_OP
%token DO ELSE IF WHILE
%token CHAR DOUBLE FLOAT INT LONG SHORT
%%
expresion_primaria:
		  IDENTIFICADOR {printf("Identificador\n");}
;
expresion_unaria:
		operador_unario expresion_cast
;
operador_unario:
	       '*'
	|	'+'
	|	'-'
;
expresion_cast:
	      expresion_unaria
;
expresion_multiplicacion:
		expresion_cast
	|	expresion_multiplicacion '*' expresion_cast
	|	expresion_multiplicacion '/' expresion_cast
;
expresion_aditiva:
		 expresion_multiplicacion
	|	expresion_aditiva '+' expresion_multiplicacion
	|	expresion_aditiva '-' expresion_multiplicacion
;
expresion_cambio:
		expresion_aditiva
;
expresion_relacional:
		    expresion_cambio
	|	expresion_relacional '<' expresion_cambio
	|	expresion_relacional '>' expresion_cambio
;
expresion_igualdad:
		  expresion_relacional
	|	expresion_igualdad EQ_OP expresion_relacional
;
expresion_asignacion:
		    expresion_unaria operador_asignacion expresion_asignacion
;

operador_asignacion:
		   '='	{
				printf("1. Calcular/obtener el valor de la expresion\n");
				printf("2. Obtener la direccion de VARIABALE\n");
				printf("3. Copiar el valor de la expresion a la direccion 					de VARIABLE\n");
			}
;
expresion:
	 expresion ',' expresion_asignacion
;
especificador_tipo:
		  CHAR
	|	SHORT
	|	INT
	|	LONG
	|	FLOAT
	|	DOUBLE
;


%%

int main(){
	yyparse();
	return 0;
}

