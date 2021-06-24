%{
    #include <stdio.h>
    void yyerror(char *mensaje){
        printf("Error: %s\n",mensaje);
    }
%}

%token NUMERO

%%
entrada:
%empty
;
entrada: entrada linea
;
linea: '\n'
;
linea: expresion '\n'   {printf("\n Resultado = %d \n",$1);}
;
expresion: NUMERO   {$$=$1;}
;
expresion: expresion expresion '+'  {$$=$1+$2;}
;
expresion: expresion expresion '*'  {$$=$1*$2;}
;

%%

int main(){
    yyparse(); /*dispara/inicia el analisis sintactico */
    return 0;
}
	
