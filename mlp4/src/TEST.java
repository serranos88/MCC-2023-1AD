import java.util.ArrayList;
import java.util.Arrays;

import Analisis.AnalisisSintaxis;
import Analisis.Keyboard;
import Lexer.Lexer;
import Memoria.Memoria;

public class TEST {
    
    public static void main(String[] args) {
        
        final String RUTA_SRC = "src/codigo.txt";
        Memoria memoria = Memoria.getInstancia();
        AnalisisSintaxis analisis = AnalisisSintaxis.getInstancia();
        //Lexer.obtenerLineasCodigo(RUTA_SRC);
        ArrayList<String> arr = Lexer.obtenerLineasCodigo(RUTA_SRC);
        ArrayList<String[]> arr2 = new ArrayList<String[]>();
        for (String linea : arr) {
            System.out.println(linea);
            arr2.add(Lexer.tokenizarLinea(linea)); //Lexer.tokenizarLinea(linea);
            
        }
        
        for (String[] linea : arr2) {
            for (String token : linea) {
                System.out.print("["+token + "]");
            }
            System.out.println();
            System.out.println();
        }

        
        //memoria.guardarVariable("base", 5);
        
        memoria.imprimirTablaVariables();
       
        analisis.imprimir();
        System.out.println("--------------------");
        

        for (String[] linea : arr2) {
            System.out.println("--------------------");
            System.out.println("--------------------");
            analisis.instruccion(linea);
            System.out.println("--------------------");
            System.out.println("--------------------");
            
            analisis.imprimir();
           System.out.println(Arrays.asList(linea));
           for (String token : linea) {
               System.out.print("["+token + "]");
           }
              System.out.println();
              System.out.println();
        }
        
    }
}
