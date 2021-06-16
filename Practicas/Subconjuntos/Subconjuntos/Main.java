package Subconjuntos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        // Aqui se pone el AFN que queremos probar Subconjuntos/AFN.txt o Subconjuntos/AFN2.txt
        BufferedReader buffer = new BufferedReader(new FileReader("Subconjuntos/AFN2.txt"));
        ArrayList<String> afn = new ArrayList<>();
        String linea = buffer.readLine();
        while (linea != null) {
            afn.add(linea);
            linea = buffer.readLine();
        }
        buffer.close();
        Algoritmo a = new Algoritmo(afn);
        a.cerradura("1");// indicamos el estado inicial del AFN
        a.salida_afd();// obtenemos el AFD en un txt
    }
}
