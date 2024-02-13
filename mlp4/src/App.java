/*
 * GARCIA SERRANO JOSE ANTONIO
 * Proyecto: Mini Lenguaje de Programacion
 * Tecnologia de Programacion 2023
 * 
 * Capaz de interpretar un codigo fuente en un lenguaje 
 * de sintaxis simple en espanol y ejecutarlo haciendo uso
 * de Colecciones de las librerias de Java.
 * Capaz de resolver expresiones algebraicas y aritmeticas
 * 
 * - Matematicas
 * - Variables
 * - Condicionales
 * - Ciclos
 * 
 * */
import java.util.ArrayList;
import Lexer.Lexer;
import Analisis.AnalisisSintaxis;
import Memoria.Memoria;

public class App {
    public static void main(String[] args) throws Exception {
        
        final String RUTA_SRC = "src/codigo.txt";
        Memoria memoria = Memoria.getInstancia();
        AnalisisSintaxis analisis = AnalisisSintaxis.getInstancia();

        ArrayList<String> lineas = Lexer.obtenerLineasCodigo(RUTA_SRC);
        ArrayList<String[]> lineasTokenizadas = new ArrayList<String[]>();

        for (String linea : lineas) {
            System.out.println(linea);
            lineasTokenizadas.add(Lexer.tokenizarLinea(linea));
        }

        for (String[] lineaTokens : lineasTokenizadas) {
            //System.out.println("--------------------");
            //System.out.println("--------------------");

            analisis.instruccion(lineaTokens);
            
            //System.out.println("--------------------");
            //System.out.println("--------------------");
            
            //analisis.imprimirVariables();
            //System.out.println();
            //System.out.println();
        }

        analisis.imprimirVariables();
       
    }
}
