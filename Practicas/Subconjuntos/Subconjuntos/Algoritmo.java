package Subconjuntos;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Algoritmo {
    private ArrayList<String> afn = new ArrayList<>(); //AFN de entrada
    private ArrayList<String> estados = new ArrayList<>(); //Estados obtenidos de hacer cerradura()
    private ArrayList<String> simbolos = new ArrayList<>(); //simbolos de transicion que hay en nuestro AFN
    private ArrayList<String> kernels = new ArrayList<>(); //Kernels obtenidos por la funcion cerradura_mover()
    private ArrayList<String> estados_finales = new ArrayList<>(); //estados que contienen al estado final del AFN
    private ArrayList<String> tabla_transiciones = new ArrayList<>(); //almacena las transiciones que hacen los estados con los simbolos
    private ArrayList<String> afd = new ArrayList<>(); //AFD de salida
    private String estado_inicial;

    public Algoritmo(ArrayList<String> afn) {
        this.afn = afn;
    }

    // nos devuelve el estado inicial que esta marcado con un "+"
    public int einicial() {
        int inicial = 0;
        for (int i = 0; i < afn.size(); i++) {
            if (afn.get(i).contains("+")) {
                String[] cad = afn.get(i).split("(\\b)");
                inicial = Integer.parseInt(cad[0]);
            }
        }
        return inicial;
    }

    // nos devuelve el estado de aceptacion (final) que esta marcado con "-"
    public int efinal() {
        int efinal = 0;
        for (int i = 0; i < afn.size(); i++) {
            if (afn.get(i).contains("-")) {
                String[] cad = afn.get(i).split("(\\b)");
                efinal = Integer.parseInt(cad[cad.length - 1]);
            }
        }
        return efinal;
    }

    // regresa los simbolos que hacen transiciones Ej [a, b]
    public ArrayList<String> simbolos() {
        for (int i = 0; i < afn.size(); i++) {
            if (!afn.get(i).contains("e")) {
                String[] cad = afn.get(i).split("(\\b)");
                for (int j = 0; j < cad.length; j++) {
                    simbolos.add(cad[cad.length / 2]);
                }
            }
        }
        Set<String> set = new HashSet<String>(simbolos);
        simbolos.clear();
        simbolos.addAll(set);
        return simbolos;
    }

    // funcion cerradura
    public void cerradura(String estado) {
        Cerradura c = new Cerradura(efinal() + 1);
        String[] kernel = estado.split(" ");
        String cerraduras = "";
        if (kernel.length == 1) {
            for (int i = 0; i < afn.size(); i++) {
                if (afn.get(i).contains("e")) {
                    String[] cad = afn.get(i).split("(\\b)");
                    c.agregaArista(Integer.parseInt(cad[0]), Integer.parseInt(cad[cad.length - 1]));
                }
            }
            estados.add(c.BFS(Integer.parseInt(kernel[0])));
        } else {
            for (int j = 0; j < kernel.length; j++) {
                for (int i = 0; i < afn.size(); i++) {
                    if (afn.get(i).contains("e")) {
                        String[] cad = afn.get(i).split("(\\b)");
                        c.agregaArista(Integer.parseInt(cad[0]), Integer.parseInt(cad[cad.length - 1]));
                    }
                }
                cerraduras = cerraduras + c.BFS(Integer.parseInt(kernel[j]));
            }
            estados.add(juntar_cerraduras(cerraduras));
        }
    }

    // funcion cerradura sin agregar el resultado a los estados.
    public String cerradura_sin(String estado) {
        Cerradura c = new Cerradura(efinal() + 1);
        String[] kernel = estado.split(" ");
        String cerraduras = "";
        if (kernel.length == 1) {
            for (int i = 0; i < afn.size(); i++) {
                if (afn.get(i).contains("e")) {
                    String[] cad = afn.get(i).split("(\\b)");
                    c.agregaArista(Integer.parseInt(cad[0]), Integer.parseInt(cad[cad.length - 1]));
                }
            }
            return cerraduras = cerraduras + c.BFS(Integer.parseInt(kernel[0]));
        } else {
            for (int j = 0; j < kernel.length; j++) {
                for (int i = 0; i < afn.size(); i++) {
                    if (afn.get(i).contains("e")) {
                        String[] cad = afn.get(i).split("(\\b)");
                        c.agregaArista(Integer.parseInt(cad[0]), Integer.parseInt(cad[cad.length - 1]));
                    }
                }
                cerraduras = cerraduras + c.BFS(Integer.parseInt(kernel[j]));
            }
        }
        return juntar_cerraduras(cerraduras);
    }

    // funcion para juntar las cerraduras por ej cerradura("6 12 16") T = "6 12 16"
    // da como entrada a esta funcion 3 cerraduras la de 6 la d 12
    // y la de 16 en una misma cadena, aqui las junto sin repetir elementos y es lo
    // que me regresa esta funcion la cerradura(T)
    public String juntar_cerraduras(String cerraduras) {
        String[] separar_cerraduras = cerraduras.split(" ");
        ArrayList<String> auxiliar = new ArrayList<>();
        cerraduras = "";
        for (int i = 0; i < separar_cerraduras.length; i++) {
            auxiliar.add(separar_cerraduras[i]);
        }
        Set<String> set = new HashSet<String>(auxiliar);
        auxiliar.clear();
        auxiliar.addAll(set);
        for (int i = 0; i < auxiliar.size(); i++) {
            cerraduras = cerraduras + auxiliar.get(i) + " ";
        }
        return cerraduras;
    }

    // funcion mover
    public String mover(String estado, String simbolo) {
        String cad_temporal = "";
        String separar_estados[] = estado.split(" ");
        for (int i = 0; i < afn.size(); i++) {
            if (afn.get(i).contains(simbolo)) {
                String[] cad = afn.get(i).split("(\\b)");
                for (int j = 0; j < separar_estados.length; j++) {
                    if (cad[0].equals(separar_estados[j])) {
                        cad_temporal = cad_temporal + cad[cad.length - 1] + " ";
                    }
                }
            }
        }
        if (cad_temporal.equals("")) {
            cad_temporal = "vacio";
        }
        tabla_transiciones.add(simbolo);
        return cad_temporal;
    }

    // funcion cerradura y mover c(mover())
    public void cerradura_mover(String estado, String simbolo) {
        String temporal = mover(estado, simbolo);
        tabla_transiciones.add(temporal);
        if (temporal.equals("vacio")) {
            kernels.add(temporal);
        } else {
            kernels.add(temporal);
        }
        int contador = 0;
        for (int i = 0; i < kernels.size(); i++) {
            contador = Collections.frequency(kernels, kernels.get(i).toString());// contar el numero de veces q se
                                                                                 // repite el kernel
        }
        if (contador <= 1 && !temporal.equals("vacio")) {
            cerradura(temporal);
        }
    }

    // funcion algoritmo subconjuntos llamamos a cerradura_mover() e iteramos con los estados y los simbolos
    public void algoritmo_subconjuntos() {
        simbolos();
        for (int i = 0; i < estados.size(); i++) {
            for (int j = 0; j < simbolos.size(); j++) {
                cerradura_mover(estados.get(i).toString(), simbolos.get(j).toString());
            }
        }
    }

    // funcion que construye el afd
    public void construir_afd() {
        algoritmo_subconjuntos();
        for (int i = 0; i < estados.size(); i++) {
            String separar_estado[] = estados.get(i).toString().split(" ");
            for (int j = 0; j < separar_estado.length; j++) {
                if (separar_estado[j].equals("" + efinal())) {
                    estados_finales.add(estados.get(i).toString());// aqui almaceno los estaods finales
                }
            }
        }
        estado_inicial = estados.get(0).toString();
        for (int i = 0; i < tabla_transiciones.size(); i++) {
            if (tabla_transiciones.get(i).length() > 1 && !tabla_transiciones.get(i).equals("vacio")) {
                tabla_transiciones.set(i, cerradura_sin(tabla_transiciones.get(i)));
            }
        }
        int con = 0, con2 = 0, con3 = 1;
        for (int i = 0; i < estados.size(); i++) {
            for (int j = 0; j < tabla_transiciones.size(); j++) {
                if (i == 0 && tabla_transiciones.get(con3).contains("" + efinal())) {
                    afd.add("[" + estados.get(i) + "]+" + " " + tabla_transiciones.get(con2) + " " + "-["
                            + tabla_transiciones.get(con3) + "]" + "\n");
                } else if (i == 0) {
                    afd.add("[" + estados.get(i) + "]+" + " " + tabla_transiciones.get(con2) + " " + "["
                            + tabla_transiciones.get(con3) + "]" + "\n");
                } else if (tabla_transiciones.get(con3).contains("" + efinal())) {
                    afd.add("[" + estados.get(i) + "]" + " " + tabla_transiciones.get(con2) + " " + "-["
                            + tabla_transiciones.get(con3) + "]" + "\n");
                } else {
                    afd.add("[" + estados.get(i) + "]" + " " + tabla_transiciones.get(con2) + " " + "["
                            + tabla_transiciones.get(con3) + "]" + "\n");
                }
                con++;
                con2 = con2 + 2;
                con3 = con3 + 2;
                if (con > 1) {
                    break;
                }
            }
            con = 0;
        }
    }

    // funcion para imprimir el afd en un txt
    public void salida_afd() throws FileNotFoundException {
        construir_afd();
        PrintWriter escritor = new PrintWriter("Subconjuntos/AFD.txt");
        for (int i = 0; i < afd.size(); i++) {
            escritor.print(afd.get(i).toString());
        }
        escritor.close();
    }

    public void imprimir_afd() {
        for (int i = 0; i < afd.size(); i++) {
            System.out.println(afd.get(i));
        }
    }

    public void imprimir_afn() {
        for (int i = 0; i < afn.size(); i++) {
            System.out.println(afn.get(i));
        }
    }
}
