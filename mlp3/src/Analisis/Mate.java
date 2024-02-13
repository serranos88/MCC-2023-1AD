package Analisis;
import java.util.HashMap;
import java.lang.Math;
/*Clase Mate es in singleton */
public final class Mate {
    
    private static Mate INSTANCIA = null;

    private Mate() {}

    public static Mate getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new Mate();
        }
        return INSTANCIA;
    }

    public static double redondear(double numero, int decimales) {
        return Math.round(numero, decimales);
    }

    public static double raizCuadrada(double numero) {
        return Math.sqrt(numero);
    }



}
