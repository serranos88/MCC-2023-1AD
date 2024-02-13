/*
 * Garcia Serrano Jose Antonio
 * Proyecto: Mini Lenguaje de Programacion
 * Tecnologia de Programacion 2023
 * 
 * Capaz de interpretar un codigo fuente en un lenguaje 
 * de sintaxis simple en espanol y ejecutarlo haciendo uso
 * de Colecciones de las librerias de Java.
 * Capaz de resolver expresiones algebraicas y aritmeticas
 * 
 * */


import java.util.ArrayList;
import Analisis.*;
import Lexer.Lexer;


public class App {
    public static void main(String[] args) {
        final String   RUTA_SRC = "C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\mlp3\\src\\codigo.txt";
        // Se obtienen las lineas de codigo del archivo
        ArrayList<String> lineasCodigo = Lexer.obtenerLineasCodigo(RUTA_SRC);
        // Se crea una instancia de AnalisisSintaxis para ejecutar las operaciones
        AnalisisSintaxis ejecucion = new AnalisisSintaxis();
        // Se ejecuta cada linea de codigo
        for(String linea : lineasCodigo){
        	ejecucion.operacion(linea);
            
            // DEBUG
            //ejecucion.imprimirVariables();
        }

        
    }
}