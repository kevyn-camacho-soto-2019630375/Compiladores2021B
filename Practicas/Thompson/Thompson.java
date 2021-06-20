package Thompson;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

public class Thompson {
    private String expresion_regular;
    private int numero_estado = 0;
    private Stack pila = new Stack();

    public Thompson(String expresion_regular) { // constructor
        this.expresion_regular = expresion_regular;
    }

    // plantillas de Thompson
    public String plantillas(int opc, String expresion) {

        switch (opc) {
            case 1: // epsylon
                expresion = "estadoi" + " " + "e" + " " + "estadof";
                break;
            case 2:// cualquier simbolo solo
                expresion = (numero_estado) + " " + expresion + " " + (numero_estado + 1);
                break;
            case 3:// a|b
                String c[] = expresion.split("\\|");
                expresion = numero_estado + " e " + c[0] + " e " + (numero_estado + 1) + "\n" + (numero_estado) + " e "
                        + c[1] + " e " + (numero_estado + 1);
                break;
            case 4:// ab
                String c2[] = expresion.split("\\.");
                expresion = c2[0] + "," + c2[1];
                break;
            case 5:// a*
                String vi = "", vf = "";
                vi = "" + expresion.charAt(0);
                vf = "" + expresion.charAt(expresion.length() - 1);
                expresion = (numero_estado) + " e " + expresion + " e " + (numero_estado + 1) + "\n" + vf + " e " + vi
                        + "\n" + (numero_estado) + " e " + (numero_estado + 1);

                break;
        }
        // numero_estado++;
        return expresion;
    }

    public void construir_afn() {// construimos el afn dependiendo del simbolo d entrada
        int contador = 0;
        Infija_Postfija postfija = new Infija_Postfija();
        String expresionrp = postfija.infija_postfija(poner_puntos(expresion_regular));
        for (int i = 0; i < expresionrp.length(); i++) {
            String caracter = "" + expresionrp.charAt(i);

            if (Character.isLetter(expresionrp.charAt(i))) {
                numero_estado++;
                pila.push(plantillas(2, "" + expresionrp.charAt(i)));
                numero_estado++;

            } else if (expresionrp.charAt(i) == '|') {
                String cad = pila.peek().toString();
                pila.pop();
                String cad2 = pila.peek().toString();
                pila.pop();
                String cadf = cad2 + "|" + cad;
                numero_estado++;
                pila.push(plantillas(3, cadf));
                numero_estado++;
            } else if (expresionrp.charAt(i) == '*') {
                String cad = pila.peek().toString();
                pila.pop();
                numero_estado++;
                pila.push(plantillas(5, cad));
                numero_estado++;
            } else if (expresionrp.charAt(i) == '.') {
                String cad = pila.peek().toString();
                pila.pop();
                String cad2 = pila.peek().toString();
                pila.pop();
                String cadf = cad2 + "." + cad;
                numero_estado++;
                pila.push(plantillas(4, cadf));
                numero_estado++;
            }
        }
        // System.out.println(pila);
    }

    // Imprimimos el afn en consola y en un archivo txt (AFN.txt)
    public void imprimir_afn() throws FileNotFoundException {
        construir_afn();
        String separar[] = pila.toString().replaceAll("^\\[|\\]$", "").split(" ");
        String afn = "";
        for (int i = 0; i < separar.length; i++) {
            if (i == 0) {
                separar[i] = separar[i] + "+";
            }
            if (i == (separar.length - 1)) {
                separar[i] = "-" + separar[i];
            }
        }
        /*
         * for (int i = 0; i < separar.length; i++) { if(separar[i].contains(",")){
         * if(separar[i].charAt(0)>=separar[i].charAt(2)){
         * separar[i]=separar[i].replaceAll("\\,\\d", ""); }else{
         * separar[i]=separar[i].replaceAll("\\d\\,", ""); } } }
         */
        for (int i = 0; i < separar.length; i++) {
            // System.out.println(separar[i]);
            afn = afn + separar[i] + " ";
        }
        System.out.println(afn);
        PrintWriter escritor = new PrintWriter("Thompson/AFN.txt");
        escritor.print("expresion regular = " + expresion_regular + "\n" + "AFN:" + "\n" + afn);
        escritor.close();
    }

    // ponemos "." para poder concatenar
    public String poner_puntos(String expresion) {
        String cadpuntos = expresion.replaceAll("", ".");
        String cad_final = cadpuntos.replaceAll("\\(\\.", "(").replaceAll("^\\.|\\.$", "").replaceAll("\\.\\+\\.", "+")
                .replaceAll("\\.\\)", ")").replaceAll("\\.\\*", "*").replaceAll("\\.\\|\\.", "|");
        // System.out.println(cad_final);
        return cad_final;
    }
}
