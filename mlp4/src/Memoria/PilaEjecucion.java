package Memoria;
import java.util.Stack;

public final class PilaEjecucion {
    private Stack<String[]> pila = new Stack<String[]>();
    private static PilaEjecucion INSTANCIA = null;

    private PilaEjecucion() {}

    public static PilaEjecucion getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new PilaEjecucion();
        }
        return INSTANCIA;
    }

    public void push(String[] instruccion){
        pila.push(instruccion);
    }

    public String[] pop(){
        return pila.pop();
    }



}
