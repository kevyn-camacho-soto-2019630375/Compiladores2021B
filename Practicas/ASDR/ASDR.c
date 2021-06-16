//Kevyn Camacho Soto Compiladores 3CV16
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
char entrada[8]="abaaaba";
int i=0;
void consumir(char x){
	if(entrada[i]==x){
		i++;
	}else{
		exit(1);
	}
}
void A();
void B();
int main(){
	A();
	if(entrada[i]=='\0'){
		printf("\n%s: si pertenece a L(G)\n", entrada);
	}
	return 0;
}
void A(){
	consumir('a');
	B();
	consumir('a');
}
void B(){
	switch(entrada[i]){
		case 'b':
			consumir('b');
			A();
			consumir('b');
			break;
		case 'a':
			consumir('a');
			break;
		default:
			exit(1);
	}
}