import java.util.*;

class GrafoCiudades {
    private int V; // Número de vértices (ciudades)
    private LinkedList<Integer> listaAdy[]; // Lista de adyacencia para representar el grafo

    public GrafoCiudades(int v) {
        V = v;
        listaAdy = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            listaAdy[i] = new LinkedList();
    }

    // Método para agregar una arista al grafo
    public void agregarArista(int v, int w) {
        listaAdy[v].add(w);
    }

    ///------------------------------------------------------------------------------------------------------------------/
    //              SECCION BFS
    //-------------------------------------------------------------------------------------------------------------------/
    
    // Metodo que realiza la busqueda en BFS a partir de un nodo
    public void BFS(int s) {
        boolean visitado[] = new boolean[V]; // Arreglo para rastrear nodos visitados

        LinkedList<Integer> cola = new LinkedList<>(); // Cola para el BFS

        visitado[s] = true;
        cola.add(s);

        if (cola.size() == 0) {
            System.out.println("No hay nodos en la cola");
        }
        while (cola.size() != 0) {
            s = cola.poll();
            System.out.print(s + " ");

            Iterator<Integer> i = listaAdy[s].listIterator();
            while (i.hasNext()) {
                int n = i.next();
                if (!visitado[n]) {
                    visitado[n] = true;
                    cola.add(n);
                }
            }
        }
    }

    // Método que realiza la búsqueda en profundidad (DFS)
    private void DFSUtil(int v, boolean visitado[]) {
        visitado[v] = true;
        System.out.print(v + " ");

        Iterator<Integer> i = listaAdy[v].listIterator();
        while (i.hasNext()) {
            int n = i.next();
            if (!visitado[n])
                DFSUtil(n, visitado);
        }
    }

    // Método para iniciar la búsqueda en profundidad
    public void DFS(int v) {
        boolean visitado[] = new boolean[V];

        DFSUtil(v, visitado);
    }


    //-------------------------------------------------------------------------------------------------------------------/
    //              SECCION MAIN
    //-------------------------------------------------------------------------------------------------------------------/

    public static void main(String args[]) {
        GrafoCiudades g = new GrafoCiudades(4);

        g.agregarArista(0, 1);
        g.agregarArista(0, 2);
        g.agregarArista(1, 2);
        g.agregarArista(2, 0);
        g.agregarArista(2, 3);
        g.agregarArista(3, 3);

        System.out.println("Recorrido BFS a partir del nodo 2:");
        g.BFS(2);
        System.out.println("Recorrido DFS a partir del nodo 2:");
        g.DFS(2);
    }
}
