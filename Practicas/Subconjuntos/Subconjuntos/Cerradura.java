package Subconjuntos;

import java.util.*;

public class Cerradura {
    private int V; // numero de vertices
    private LinkedList<Integer> adj[];// lista de adyacencia

    public Cerradura(int v) {// constructor
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; i++)
            adj[i] = new LinkedList();
    }

    // funcion para agregar una arista al grafo
    public void agregaArista(int v, int w) {
        adj[v].add(w);
    }

    // imprime el recorrido BFS desde una fuente determinada
    public String BFS(int s) {
        String cad_final = "";
        // marcamos todos los vertices como not visitados por default en falso
        boolean visitado[] = new boolean[V];
        // creamos una cola para BFS
        LinkedList<Integer> cola = new LinkedList<Integer>();
        // Marque el nodo actual como visitado y lo mete a la cola
        visitado[s] = true;
        cola.add(s);

        while (cola.size() != 0) {
            // retira un vertice de la cola y lo imprime
            s = cola.poll();
            // System.out.print(s+" ");
            cad_final = cad_final + s + " ";
            // Obtiene todos los vértices adyacentes del vertice s quitado de la cola
            // Si no se ha visitado un adyacente, márquelo
            // visitado y ponerlo en cola
            Iterator<Integer> i = adj[s].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!visitado[n]) {
                    visitado[n] = true;
                    cola.add(n);
                }
            }
        }
        // System.out.println();
        return cad_final;
    }
}
