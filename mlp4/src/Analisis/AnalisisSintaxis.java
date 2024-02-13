package Analisis;

import java.security.Key;
import java.util.Scanner;
import Lexer.Lexer;
import Memoria.Memoria;
import Memoria.Variable;
import Memoria.PilaEjecucion;
import Analisis.Mate;
import Constantes.EXPRESION;

/*
 * Clase AnalisisSintaxis que analiza la sintaxis del codigo
 * y ejecuta las operaciones correspondientes
 */


public final class AnalisisSintaxis {
    private Scanner sc = new Scanner(System.in);
    //private Matematica mate = new Matematica();
    private Memoria memoria = Memoria.getInstancia();
    private Mate mate = Mate.getInstancia();
    

    private static AnalisisSintaxis INSTANCIA = null;

    private AnalisisSintaxis() {}

    public static AnalisisSintaxis getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new AnalisisSintaxis();
        }
        return INSTANCIA;
    }

    public void imprimirVariables(){
        memoria.imprimirTablaVariables();
    }

    //-----------------------------------------------------------------------------------------
    //	Recibe de parametro String una sola linea de codigo  analisa la instruccion a realizar
    //	ejecuta el codigo segun la palabra reservada para dicha instruccion
    //----------------------------------------------------------------------------------------
    public void instruccion(String[] tokens){

        
        String instruccion =  tokens[1];
        String nombreVariable ="";
        String tipoVariable = "";
        String valorVariable = "";
        
        
        switch (instruccion){
            
        	case "Leer": // Lee un valor desde consola y lo asigna a una variable
                nombreVariable = tokens[2];
                System.out.print("> "+nombreVariable+": ");
                
                if(memoria.esVariableDeclarada(nombreVariable)){ //REVISAR
                    if(memoria.obtenerTipo(nombreVariable).equals("Cadena")){
                        memoria.asignarValor(nombreVariable, Keyboard.readString());//memoria.asignarValor(nombreVariable, sc.nextLine());
                    } else if(memoria.obtenerTipo(nombreVariable).equals("Entero")){
                        memoria.asignarValor(nombreVariable, Keyboard.readInt());
                    } else if(memoria.obtenerTipo(nombreVariable).equals("Real")){
                        memoria.asignarValor(nombreVariable, Keyboard.readDouble());
                    }
                }
                
                break;
            
            case "Imprime": // Imprime sin salto de linea
                Depurardor.printEstado("Imprimiendo...linea "+tokens[0]);
                String cadena = tokens[3].replace("'", "");
                System.out.print(cadena);

                if(!tokens[4].equals(")")){
                    nombreVariable = tokens[4];
                    nombreVariable = nombreVariable.replace(")", "");
                    nombreVariable = nombreVariable.replace(",", "");
                    if(memoria.esVariableDeclarada(nombreVariable)){
                        //System.out.print(cadena); 
                        if(memoria.obtenerTipo(nombreVariable).equals("Cadena")){
                            System.out.print(memoria.obtenerValorCadena(nombreVariable));
                        } else if(memoria.obtenerTipo(nombreVariable).equals("Entero")){
                            System.out.print(memoria.obtenerValor(nombreVariable).intValue());
                        } else if(memoria.obtenerTipo(nombreVariable).equals("Real")){
                            System.out.print(memoria.obtenerValor(nombreVariable).doubleValue());
                        }
                    } else if (mate.esNumero(nombreVariable)){
                        System.out.print(nombreVariable);
                    } else {
                        System.out.print("Error: Variable no declarada");
                    }
                }
                
                System.out.println();
                break;
                
            case "Entero":
            case "Real": // Declaracion de variables Real o Entero  
                tipoVariable = tokens[1];
                nombreVariable = tokens[2];
                memoria.declararVariable(tipoVariable, nombreVariable);
                break;
            case "Cadena": // Declaracion de variables Cadena
                tipoVariable = tokens[1];
                nombreVariable = tokens[2];
                memoria.declararVariable(tipoVariable, nombreVariable);
                break;

            default: // Casos especiales con estructura compleja
            //-----------------------------------------------------------------------------------------    
            // INICIO -- Analisa sintaxis de ASIGNACION de valor a variable
            //-----------------------------------------------------------------------------------------
                if (tokens[2].matches(EXPRESION.ASIGNACION.getExpresion())){
                    Depurardor.printEstado("Asignacion de valor a variable...en linea " + tokens[0]);
                    nombreVariable = tokens[1];
                    
                    if(tokens.length == 4 && memoria.esVariableDeclarada(nombreVariable) && memoria.obtenerTipo(nombreVariable).equals("Cadena")){
                        valorVariable = tokens[3];
                        memoria.asignarValor(nombreVariable, valorVariable);
                    }else if(tokens.length == 4 && mate.esNumero(tokens[3])){
                        valorVariable = tokens[3];
                        memoria.asignarValor(nombreVariable, Double.parseDouble(valorVariable));
                    }else{
                        Double resultado = mate.resolverExpresion(tokens);
                        memoria.asignarValor(nombreVariable, resultado);
                    }
                }else{
                    Depurardor.printEstado("ERROR SINTAXIS: no se reconoce instruccion...linea "+tokens[0]);
                }
            //-----------------------------------------------------------------------------------------    
            // FIN ---- Analisa sintaxis de ASIGNACION de valor a variable
            //-----------------------------------------------------------------------------------------

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
    // Interpretacion de Bloques Si, Sino Si, Sino
    //-----------------------------------------------------------------------------------------
    public void analisisBloqueSi(String bloque){
        // TODO resolver como manejar el bloque utilizando solo Tokens por linea

        // Los tokens deberian traer Numero de linea, (Si, Sino Si, Sino), (Condicion), (Bloque de codigo)

        // Manejo de condiciones:
        // Buscar en memoria si la variable esta declarada
        // Si esta declarada, obtener su valor
        // Si no esta declarada arrojar error
        // obtener el operador de comparacion
        // obtener el segundo valor de la comparacion (numero o variable)
        // evaluarlo en un switch(operador) y comparar los valores

        // Si el resultado de la comparacion en el swtich es verdadero, ejecutar el bloque de codigo

        
        // tokens[1] = numero linea, tokens[2] = Si, Sino Si, Sino, tokens[3] = Condicion, tokens[4] = Bloque de codigo
        
        
        /*
        boolean condicion = false;
        switch (operador){
            case ">":
                condicionCumplida = num2 > num1;
                break;
            case "<":
                condicionCumplida = num2 < num1;
                break;
            case ">=":
                condicionCumplida = num2 >= num1;
                break;
            case "<=":
                condicionCumplida = num2 <= num1;
                break;
            case "==":
                condicionCumplida = num2 == num1;
                break;
            case "!=":
                condicionCumplida = num2 != num1;
                break;
            default:
                System.out.println("Error: Operador no valido");
        }
        */
    }

    //-----------------------------------------------------------------------------------------
    // Interpretacion de Bloques Mientras
    //-----------------------------------------------------------------------------------------
    public void analisisBloqueMientras(String bloque){
        // TODO resolver como manejar el bloque utilizando solo Tokens por linea

        // Los tokens deberian traer Numero de linea, (Mientras), (Condicion), (Bloque de codigo)

        // Manejo de condiciones:
        // Buscar en memoria si la variable esta declarada
        // Si esta declarada, obtener su valor
        // Si no esta declarada arrojar error
        // obtener el operador de comparacion
        // obtener el segundo valor de la comparacion (numero o variable)
        // evaluarlo en un switch(operador) y comparar los valores

        // Si el resultado de la comparacion en el swtich es verdadero, ejecutar el bloque de codigo
        // Al final de la ejecucion debe volver a evaluar la condicion y repetir el bloque de codigo hasta que no se cumpla
        // Se puede solucionar implementando una pila de Ejecucion, donde se guarden los bloques de codigo a ejecutar como identificadores

        
        // tokens[1] = numero linea, tokens[2] = Mientras, tokens[3] = Condicion, tokens[4] = Bloque de codigo
        
        
        /*
        boolean condicion = false;
        switch (operador){
            case ">":
                condicionCumplida = num2 > num1;
                break;
            case "<":
                condicionCumplida = num2 < num1;
                break;
            case ">=":
                condicionCumplida = num2 >= num1;
                break;
            case "<=":
                condicionCumplida = num2 <= num1;
                break;
            case "==":
                condicionCumplida = num2 == num1;
                break;
            case "!=":
                condicionCumplida = num2 != num1;
                break;
            default:
                System.out.println("Error: Operador no valido");
        }
        */
    }


    



}
