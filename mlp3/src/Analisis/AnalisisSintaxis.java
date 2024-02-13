package Analisis;

import java.util.Scanner;
import Lexer.Lexer;


public class AnalisisSintaxis {
    private Scanner sc = new Scanner(System.in);
    private Matematica mate = new Matematica();

    //-----------------------------------------------------------------------------------------
    //	Recibe de parametro String una sola linea de codigo  analisa la operacion a realizar
    //	ejecuta el codigo segun la palabra reservada para dicha operacion
    //----------------------------------------------------------------------------------------
    public void operacion(String linea){

        String[] tokens = Lexer.tokenizarLinea(linea);
        String operacion =  tokens[0];
        String variable ="";
        boolean saltoLinea = false;
        String instruccionSubstring = "";
        switch (operacion){
            
        	case "Leer": // Lee un valor desde consola y lo asigna a una variable
                variable = tokens[1];
                System.out.print("> "+variable+": ");
                mate.asignarValor(variable, sc.nextDouble());
                break;
            
            case "ImprimeS": // Imprime con salto de linea
                saltoLinea = true;
            case "Imprime": // Imprime sin salto de linea
                instruccionSubstring = linea.substring(linea.indexOf("(")+1, linea.indexOf(")"));   
                String [] datos = instruccionSubstring.split(",");
                for (int i = 0; i<datos.length; i++){
                    if (mate.esVariableDeclarada(datos[i])){
                        System.out.print(mate.obtenerValor(datos[i]));
                    }else{
                        System.out.print(datos[i].replace("'", ""));
                    }
                        
                }
                if (saltoLinea){
                    System.out.println();
                }

                break;
                
            case "Entero":
            case "Real": // Declaracion de variables Real o Entero  
                variable = linea.split(" ")[1];
                mate.inicializarVariables(operacion, variable);
                break;
                
            default: // Asignacion de variables
                variable = linea.split(" ")[0];
                for(int i = 2; i<linea.split(" ").length; i++){
                    if (esNumero(linea.split(" ")[i])){
                        mate.colocarDatoEnPila(Double.parseDouble(linea.split(" ")[i]));
                }else{
                        mate.colocarDatoEnPila(linea.split(" ")[i]);
                    }                  
                }
                mate.vaciarPilaOperador();
                mate.asignarValor(variable, mate.obtenerResultado());
        }

    }
    
    //----------------------------------------------
    // Convierte los numeros del codigo a Double
    //----------------------------------------------
    public boolean esNumero(String numero){
        try{
            Double.parseDouble(numero);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //-----------------------------------------------------------------------------------------
    // DEBUG: Imprime las variables declaradas
    //-----------------------------------------------------------------------------------------
    public void imprimirVariables(){
        System.out.println("Variables enteras");
        for (String key : mate.memoriaVariablesInt.keySet()) {
            System.out.println(key + " = " + mate.memoriaVariablesInt.get(key));
        }
        System.out.println("Variables reales");
        for (String key : mate.memoriaVariablesDouble.keySet()) {
            System.out.println(key + " = " + mate.memoriaVariablesDouble.get(key));
        }
    }

}
