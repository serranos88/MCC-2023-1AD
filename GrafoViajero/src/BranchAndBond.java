// Java program to solve Traveling Salesman Problem
// using Branch and Bound.
import java.util.*;


public class BranchAndBond {
   static String[] CIUDADES = {"MADRID","PARIS","ARMSTERDAN","ROMA","ATHENAS","KIEU","BRUJAS",
    "LONDRES","LISBOA","BERLIN","MOSCOW","VARSOVIA"};
    static int ADYACENCIA[][] = {
            {0, 1051, 1480, 1364, 2368, 2861, 1312, 1261, 502, 1869, 3440, 2293},
            {1051, 0, 430, 1051, 2095, 2023, 268, 344, 1453, 878, 2480, 1370},
            {1480, 430, 0, 1480, 2164, 1780, 172, 359, 1863, 577, 2147, 1098},
            {1364, 1107, 1298, 0, 1048, 1675, 1253, 1436, 1863, 1184, 2375, 1318},
            {2368, 2095, 2164, 1048, 0, 1487, 2176, 2393, 2850, 1803, 2231, 1598},
            {2861, 2023, 1780, 1675, 1487, 0, 1908, 2136, 3351, 1203, 754, 685},
            {1312, 268, 172, 1253, 2176, 1908, 0, 237, 1090, 713, 2305, 1231},
            {1261, 344, 359, 1436, 2393, 2136, 237, 0, 1583, 934, 2503, 1455},
            {502, 1453, 1863, 1863, 2850, 3351, 1090, 1583, 0, 2312, 3905, 2763},
            {1869, 878, 577, 1184, 1803, 1203, 713, 934, 2312, 0, 1608, 1520},
            {3440, 2486, 2147, 2375, 2231, 754, 2305, 2503, 3905, 1608, 0, 1146},
            {2293, 1370, 1098, 1318, 1598, 685, 1231, 1455, 2763, 520, 1146, 0}
        };
    static int adjSeleccionada[][];
    static int numeroCiudades = 4;
    static String[] ciudadesSeleccionadas;
    Scanner sc = new Scanner(System.in);

    //-------------------------------------------------------------------------- /
    //              SECCION  - SELECCION DE CIUDADES
    //-------------------------------------------------------------------------- /
    static void seleccionarCiudades(int numeroCiudades, String[] CIUDADES){
        ciudadesSeleccionadas = new String[numeroCiudades];
        adjSeleccionada = new int[numeroCiudades][numeroCiudades];
        int[] indexCiudadesSeleccionadas = new int[numeroCiudades];

        System.out.println("Ciudades disponibles: ");
        for(int i = 0; i < CIUDADES.length; i++){
            System.out.println(i+" - "+ CIUDADES[i]);
        }
        int j = 0;
        
        do{
            System.out.println("Ingrese el index de una ciudad a visitar: ");
            int index = Keyboard.readInt();
            if(index >= 0 && index < CIUDADES.length){
                ciudadesSeleccionadas[j] = CIUDADES[index];
                
                indexCiudadesSeleccionadas[j] = index;
                j++;
            }
            else{
                System.out.println("Index invalido");
            }

        }while(j < numeroCiudades);  
        System.out.println("Ciudades seleccionadas: " + Arrays.toString(ciudadesSeleccionadas));
        System.out.println("Index de ciudades seleccionadas: " + Arrays.toString(indexCiudadesSeleccionadas));

        for(int i = 0; i < numeroCiudades; i++){
            for(int k = 0; k < numeroCiudades; k++){
                adjSeleccionada[i][k] = ADYACENCIA[indexCiudadesSeleccionadas[i]][indexCiudadesSeleccionadas[k]];
            }
        }
    }

    static void imprimirAdayacenciaSeleccionada(int[][] adjSeleccionada){
        for(int i = 0; i < adjSeleccionada.length; i++){
            for(int j = 0; j < adjSeleccionada.length; j++){
                System.out.print(adjSeleccionada[i][j]+" ");
            }
            System.out.println();
        }
    }


    static void imprimirCiudadesSeleccionadas(String[] ciudadesSeleccionadas){
        for(int i = 0; i < ciudadesSeleccionadas.length; i++){
            System.out.println("i - "+ ciudadesSeleccionadas[i]);
        }
    }


 
    // ruta_final[] guarda la solucion final para el viaje
    static int ruta_final[] = new int[numeroCiudades + 1];
 
    // visitados[] lleva registro de los nodos(ciudades) ya visitadas
    // en una ruta en particular
    static boolean visitados[] = new boolean[numeroCiudades];
 
    // Guarda el peso minimo final de la Ruta mas corta
    static int final_res = Integer.MAX_VALUE;
 
    // Funcion para copiar la solucion temporal a ruta_final[]
    static void copiarAFinal(int ruta_actual[])
    {
        for (int i = 0; i < numeroCiudades; i++)
            ruta_final[i] = ruta_actual[i];
        ruta_final[numeroCiudades] = ruta_actual[0];
    }
 
   
    // Funcion para encontrar el costo minimo de la arista
    // teniendo un final en el vertice i
    static int primerRutaMinima(int adj[][], int i)
    {
        int min = Integer.MAX_VALUE;
        for (int k = 0; k < numeroCiudades; k++)
            if (adj[i][k] < min && i != k)
                min = adj[i][k];
        return min;
    }
    
    // Funcion para encontrar el segundo costo minimo de la arista
    // teniendo un final en el vertice i
    static int secondMin(int adj[][], int i)
    {
        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        for (int j=0; j<numeroCiudades; j++)
        {
            if (i == j)
                continue;
 
            if (adj[i][j] <= first)
            {
                second = first;
                first = adj[i][j];
            }
            else if (adj[i][j] <= second &&
                    adj[i][j] != first)
                second = adj[i][j];
        }
        return second;
    }
 

    // FUNCION QUE TOMA COMO ARGUMENTOS:
    // curr_bound -> limite inferior del nodo raiz
    // curr_weight-> almacena el peso del camino hasta ahora
    // level-> nivel actual mientras se mueve en la busqueda
    //         espacio arbol
    // curr_path[] -> donde se almacena la solucion que se esta almacenando que
    //             luego se copiara en final_path[]
    static void TSPRec(int adj[][], int curr_bound, int curr_weight,
                int level, int curr_path[])
    {
        // CASO Base es cuando hemos alcanzado el nivel N que
        // significa que hemos cubierto todos los nodos una vez
        if (level == numeroCiudades)
        {
            
            // checa si hay una arista del ultimo vertice en
            // el camino de regreso al primer vertice
            if (adj[curr_path[level - 1]][curr_path[0]] != 0)
            {
                
                // curr_weight is como el peso total de la ruta
                // que se esta evaluando
                int curr_res = curr_weight +
                        adj[curr_path[level-1]][curr_path[0]];
     
               
                // Actualiza el resultado final y la ruta final si
                // el resultado actual es mejor.
                if (curr_res < final_res)
                {
                    copiarAFinal(curr_path);
                    final_res = curr_res;
                }
            }
            return;
        }
 
        // for any other level iterate for all vertices to
        // build the search space tree recursively
        for (int i = 0; i < numeroCiudades; i++)
        {
            // Consider next vertex if it is not same (diagonal
            // entry in adjacency matrix and not visited
            // already)
            if (adj[curr_path[level-1]][i] != 0 &&
                    visitados[i] == false)
            {
                int temp = curr_bound;
                curr_weight += adj[curr_path[level - 1]][i];
 
                
                // curr_bound se procesa distinto para el nivel 2 con respecto a los demas niveles
                if (level==1)
                curr_bound -= ((primerRutaMinima(adj, curr_path[level - 1]) +
                                primerRutaMinima(adj, i))/2);
                else
                curr_bound -= ((secondMin(adj, curr_path[level - 1]) +
                                primerRutaMinima(adj, i))/2);
 
                // curr_bound + curr_weight es el limite inferior actual
                // para el nodo en el que hemos llegado
                // Si el limite inferior actual < final_res, necesitamos explorar
                // el nodo mas a fondo
                if (curr_bound + curr_weight < final_res)
                {
                    curr_path[level] = i;
                    visitados[i] = true;
 
                    
                    // TSPRec para el siguiente nivel
                    TSPRec(adj, curr_bound, curr_weight, level + 1,
                        curr_path);
                }
 
                // Necesitamos podar el Nodo reseteando
                // todos los cambios hechos a curr_weight y curr_bound
                curr_weight -= adj[curr_path[level-1]][i];
                curr_bound = temp;
 
                
                // Resetear el arreglo visitados
                Arrays.fill(visitados,false);
                for (int j = 0; j <= level - 1; j++)
                    visitados[curr_path[j]] = true;
            }
        }
    }
 
    // FUNCION QUE CONFIGURA final_path[] 
    static void TSP(int adj[][])
    {
        int curr_path[] = new int[numeroCiudades + 1];
 
        // Calcula el limite bajo inicial para el nodo raiz
        // usando la formula 1/2 * (suma del primer minimo +
        // segundo minimo) para todas las aristas.
        // Tambien inicializa el curr_path y el arreglo visitados
        int curr_bound = 0;
        Arrays.fill(curr_path, -1);
        Arrays.fill(visitados, false);
 
        // Procesamiento de limite inicial
        for (int i = 0; i < numeroCiudades; i++)
            curr_bound += (primerRutaMinima(adj, i) +
                        secondMin(adj, i));
 
        
        // Redondeando el limite inferior a un entero
        curr_bound = (curr_bound==1)? curr_bound/2 + 1 :
                                    curr_bound/2;
 
        // Inicializamos desde el vertice 1 asi que el primer vertice
        // en curr_path[] es 0
        visitados[0] = true;
        curr_path[0] = 0;
 
        
        // Llamada a TSPRec para curr_weight igual a
        // 0 y nivel 1
        TSPRec(adj, curr_bound, 0, 1, curr_path);
    }
     
    
    public static void main(String[] args) 
    {
        seleccionarCiudades(numeroCiudades, CIUDADES);
        imprimirAdayacenciaSeleccionada(adjSeleccionada);
        //Adjacency matrix for the given graph
        //10+25+30+15 = 80
        /* 
        int adj[][] = {{0, 10, 15, 20},
                        {10, 0, 35, 25},
                        {15, 35, 0, 30},
                        {20, 25, 30, 0}    };
        */
        
        int adj[][] = {
            {0, 1051, 1480, 1364, 2368, 2861, 1312, 1261, 502, 1869, 3440, 2293},
            {1051, 0, 430, 1051, 2095, 2023, 268, 344, 1453, 878, 2480, 1370},
            {1480, 430, 0, 1480, 2164, 1780, 172, 359, 1863, 577, 2147, 1098},
            {1364, 1107, 1298, 0, 1048, 1675, 1253, 1436, 1863, 1184, 2375, 1318},
            {2368, 2095, 2164, 1048, 0, 1487, 2176, 2393, 2850, 1803, 2231, 1598},
            {2861, 2023, 1780, 1675, 1487, 0, 1908, 2136, 3351, 1203, 754, 685},
            {1312, 268, 172, 1253, 2176, 1908, 0, 237, 1090, 713, 2305, 1231},
            {1261, 344, 359, 1436, 2393, 2136, 237, 0, 1583, 934, 2503, 1455},
            {502, 1453, 1863, 1863, 2850, 3351, 1090, 1583, 0, 2312, 3905, 2763},
            {1869, 878, 577, 1184, 1803, 1203, 713, 934, 2312, 0, 1608, 1520},
            {3440, 2486, 2147, 2375, 2231, 754, 2305, 2503, 3905, 1608, 0, 1146},
            {2293, 1370, 1098, 1318, 1598, 685, 1231, 1455, 2763, 520, 1146, 0}
        };
 
        TSP(adj);
 
        System.out.printf("Distancia minima : %d\n", final_res);
        System.out.printf("Ruta tomada : ");
        for (int i = 0; i <= numeroCiudades; i++) 
        {
            System.out.printf("%d ", ruta_final[i]);
        }
    }
}
