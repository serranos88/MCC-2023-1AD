import java.util.Stack;

import javax.swing.SwingWorker;
import javax.swing.text.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


public class AEstrella extends SwingWorker<Void, Nodo>{
    private Graficos graficos;
    private Nodo goal;
    private Nodo inicio;
    private GrafoMatriz grafo;
    private GrafoMatriz grafoInterno;
    private ArrayList<String> blackList;
    private Stack<Nodo> optimalPath;
    long startTime;
    private String nombreRaton;

    
    public AEstrella(Nodo inicio, Nodo goal, GrafoMatriz grafo, Graficos graficos) {
        this.inicio = inicio;
        this.goal = goal;
        this.grafo = grafo;
        this.blackList = new ArrayList<String>();
        this.optimalPath = new Stack<Nodo>();
        this.graficos = graficos;
        this.grafoInterno = new GrafoMatriz(grafo);
        startTime = System.currentTimeMillis();
       
    }

    public AEstrella(Nodo inicio, Nodo goal, GrafoMatriz grafo, Graficos graficos, String nombreRaton) {
        this.inicio = inicio;
        this.goal = goal;
        this.grafo = grafo;
        this.blackList = new ArrayList<String>();
        this.optimalPath = new Stack<Nodo>();
        this.graficos = graficos;
        this.grafoInterno = new GrafoMatriz(grafo);
        startTime = System.currentTimeMillis();
        this.nombreRaton = nombreRaton;
       
    }
    
    @Override
    protected Void doInBackground() throws Exception {
        Stack<Nodo> F = new Stack<>();
        F.push(this.inicio);
        aEstrella(F);
        return null;
    }

    @Override
    protected void process(List<Nodo> chunks) {
        // Actualiza los cambios en la GUI
        graficos.updateGUI();
    }


    public void aEstrella(Stack<Nodo> F) {
        if (F.size() == 0) {
            return;
        } else {
            Nodo EA = F.pop();
            EA.setEstado(Nodo.Estado.ABIERTO);
            grafo.setEstado(EA);
            grafoInterno.setEstado(EA);
            
           
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            if (goalTest(EA)) {
                optimalPath = extraerRutaDirecta(optimalPath);
                marcarCamino(EA);
                EA.setEstado(Nodo.Estado.META);
                grafo.setEstado(EA);
                double tiempoFinal = (System.currentTimeMillis() - startTime) / 1000.0;
                System.out.println("Llego a la META - Inicio:"+ this.inicio +" Meta:"+this.goal+" Tiempo: " + tiempoFinal + " segs");
                
            } else {
            publish();
            Stack<Nodo> OS = expand(EA);
            evaluate(OS);
            append(F,OS);
            sort(F);
            
            aEstrella(F);
            }
        }
    }


    private void append(Stack<Nodo> F, Stack<Nodo> OS) {
        while (!OS.isEmpty()) {
            F.push(OS.pop());
        }
    }


    private void sort(Stack<Nodo> F) {
        F.sort((node1, node2) -> Double.compare(node2.getF(), node1.getF()));
    }

    private void marcarCamino(Nodo goalNode) {
    
        while (!optimalPath.isEmpty()) {
            Nodo node = optimalPath.pop();
            node.setEstado(Nodo.Estado.CERRADO);
            grafo.setEstado(node);
            grafoInterno.setEstado(node);
        }
        optimalPath.push(goalNode);
    
        publish(); // actualiza la GUI
    }
    

    //Extrae la ruta directa
    // Filtra los nodos innecesarios del recorrido original
    private Stack<Nodo> extraerRutaDirecta(Stack<Nodo> rutaOriginal) {
        Stack<Nodo> filteredPath = new Stack<>();
        
        // Agrega el Nodo meta a la ruta filtrada
        filteredPath.push(rutaOriginal.pop());
    
        // Filtra pasos innecesarios
        while (!rutaOriginal.isEmpty()) {
            Nodo currentNode = rutaOriginal.pop();
            Nodo nextNode = filteredPath.peek();
    
            // Checa si los nodos son vecinos
            if (esNodoAdyacente(currentNode, nextNode)) {
                filteredPath.push(currentNode);
            }
        }
    
        // Cambia el orden del stack
        Collections.reverse(filteredPath);
    
        return filteredPath;
    }
    
    private boolean esNodoAdyacente(Nodo node1, Nodo node2) {
        int xDelta = Math.abs(node1.getPosX() - node2.getPosX());
        int yDelta = Math.abs(node1.getPosY() - node2.getPosY());
        return (xDelta == 0 && yDelta == 1) || (xDelta == 1 && yDelta == 0);
    }
 
    private Stack<Nodo> expand(Nodo EA) {
        Stack<Nodo> OS = new Stack<>();
        Nodo nodoVecino = null;
        int posX = EA.getPosX();
        int posY = EA.getPosY();
        String posicionNodoVecino = "";

        int[] deltaX = {-1,0,1,0};
        int[] deltaY = {0,1,0,-1};

        for (int i = 0; i < 4; i++){
            int vecinoX = posX + deltaX[i];
            int vecinoY = posY + deltaY[i];

            try{
                nodoVecino = this.grafoInterno.getNodo(vecinoX, vecinoY);
                posicionNodoVecino = nodoVecino.getPosX()+""+nodoVecino.getPosY();
                if(!nodoVecino.getBloqueado() && !blackList.contains( posicionNodoVecino) && nodoVecino.getEstado().equals(Nodo.Estado.NOVISITADO) ) {
                    OS.add(nodoVecino);
                    
                }
            }
            catch(ArrayIndexOutOfBoundsException e){

            }
        }

        optimalPath.push(EA);
        return OS;

    }

    
    
    //Calcula la distancia entre dos nodos - G
    // Distancia euclidiana para calcular el COSTO de llegar a un nodo
    private double calcularG(Nodo inicio, Nodo actual){

        return Math.sqrt(Math.pow(actual.getPosX()-inicio.getPosX(),2)+Math.pow(actual.getPosY()-inicio.getPosY(),2));
    }


    //Calcula la distancia entre dos nodos - H
    // Ecuacion Manhattan para calculo de distancias ortonormales
    private double calcularH(Nodo meta, Nodo actual){
        return Math.abs(actual.getPosX()-meta.getPosX())+Math.abs(actual.getPosY()-meta.getPosY());
    }



    private boolean goalTest(Nodo EA) {
        
        return (EA.getNombre()).equals(this.goal.getNombre());
    }
    
   
    private void evaluate( Stack<Nodo> OS) {
        double H,G = 0;
        
        int index = OS.size();
        for( int i = 0;i<index;i++){
            Nodo nodoVecino = OS.pop();
            
            G = calcularG(inicio, nodoVecino);
            nodoVecino.setDistancia(G); // G
            
            H = calcularH(goal, nodoVecino);
            nodoVecino.setHeuristic(H); // H
            
            nodoVecino.setF(G + H);

            nodoVecino.setEstado(Nodo.Estado.ABIERTO);
            grafo.setEstado(nodoVecino);

            OS.add(0,nodoVecino);
        }

    }
}
