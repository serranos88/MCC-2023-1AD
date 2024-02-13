package Analisis;

import java.util.HashMap;
import java.util.Stack;
import Constantes.Operador;
import Lexer.Lexer;

/*
 * Clase Matematica que realiza las operaciones matematicas
 * y gestiona las variables en una Coleccion de memoriaVariables
 * Realiza operaciones de declaracion, asignacion y obtencion de variables
 */

public class Matematica {

    protected Stack<String> pilaOperadores = new Stack<String>();
    protected Stack<Double> pilaNumeros = new Stack<Double>();

    protected HashMap<String, Double> memoriaVariablesInt = new HashMap<String, Double>();
    protected HashMap<String, Integer> memoriaVariablesDouble = new HashMap<String, Integer>();

    //-----------------------------------------------------------------------------------------
    // Metodo que resuelve una expresion algebraica 
    //-----------------------------------------------------------------------------------------
    public double resolverExpresion(String expresion) {
        String[] tokens = Lexer.tokenizarLinea(expresion);
        for (String token : tokens) {
            colocarDatoEnPila(token);
        }
        vaciarPilaOperador();
        return obtenerResultado();
    }

    //-----------------------------------------------------------------------------------------
    // Realiza la operacion matematica entre dos numeros
    //-----------------------------------------------------------------------------------------
    public double realizarOperacion(double num1, double num2, String operador) {
        switch (operador) {
            case "+":
                return num2 + num1;
            case "-":
                return num2 - num1;
            case "/":
                return num2 / num1;
            case "*":
                return num2 * num1;
            case "^":
                return Math.pow(num2, num1);
            default:
                return 0;
        }
    }

    //-----------------------------------------------------------------------------------------
    // Coloca el operador en la pila de operadores
    //
    //  Si la pila esta vacia o el operador es un parentesis abierto -> agrega pila
    //  Si el operador es un parentesis cerrado -> 
    //-----------------------------------------------------------------------------------------
    public void colocarOperadorenPila(String operador) {
        if (pilaOperadores.isEmpty() || operador.equals(Operador.PARENTESIS_ABIERTO.getOperador())) {
            pilaOperadores.push(operador);
        } else { 
            if(operador.equals(Operador.PARENTESIS_CERRADO.getOperador())){
                vaciarPilaOperador();
            }
            // Si el operador tiene MAYOR Prioridad que el ultimo operador en la pila
            else if (Operador.esMayorPrioridad(operador, pilaOperadores.peek())) { 
                pilaOperadores.push(operador);
            } else { // Si el operador tiene MENOR Prioridad que el ultimo operador en la pila
                while (!pilaOperadores.isEmpty() && !Operador.esMayorPrioridad(operador, pilaOperadores.peek())) {
                    double num2 = pilaNumeros.pop();
                    double num1 = pilaNumeros.pop();
                    String operadorAux = pilaOperadores.pop();
                    pilaNumeros.push(realizarOperacion(num2, num1, operadorAux));
                }
                pilaOperadores.push(operador);
            }
        }
    }

    //-----------------------------------------------------------------------------------------
    // Metodo sobrecargado Coloca el dato en la pila de operadores o en la pila de numeros
    //-----------------------------------------------------------------------------------------
    public void colocarDatoEnPila(String dato){
        if (Operador.esOperador(dato)) {
            colocarOperadorenPila(dato);
        }else if(esVariableDeclarada(dato)){
            pilaNumeros.push(obtenerValor(dato));
        }
        else {
            System.out.println("Error: " + dato + " no es un operador");
        }
    }

    public void colocarDatoEnPila(double dato){
        pilaNumeros.push(dato);
    }

    //-----------------------------------------------------------------------------------------
    // Vacía la pila de operadores
    // Si el operador es un parentesis cerrado -> vacia la pila hasta encontrar un parentesis abierto
    // Si el operador tiene MENOR Prioridad que el ultimo operador en la pila -> vacia la pila
    // Si el operador tiene MAYOR Prioridad que el ultimo operador en la pila -> agrega el operador a la pila
    // Si la pila esta vacia -> agrega el operador a la pila
    //-----------------------------------------------------------------------------------------
    public void vaciarPilaOperador(){
        while(true){
            // Si la pila esta vacia
            if(pilaOperadores.isEmpty()){
                return;
            }
            else if(pilaOperadores.peek().equals(Operador.PARENTESIS_ABIERTO.getOperador())){
                pilaOperadores.pop();
                return;
            } else if(pilaOperadores.peek().equals(Operador.PARENTESIS_CERRADO.getOperador())){// Si el operador es un parentesis cerrado
                pilaOperadores.pop();
            }
            else{ // Si el operador tiene MENOR Prioridad que el ultimo operador en la pila
                double num2 = pilaNumeros.pop(); // Se obtiene el ultimo numero en la pila
                double num1 = pilaNumeros.pop();  // Se obtiene el penultimo numero en la pila
                String operadorAux = pilaOperadores.pop(); // Se obtiene el ultimo operador en la pila
                pilaNumeros.push(realizarOperacion(num2, num1, operadorAux));
                
            }
        }
    }


    //-----------------------------------------------------------------------------------------
    // Obtiene el resultado de la operacion
    //-----------------------------------------------------------------------------------------
    public double obtenerResultado(){
        return pilaNumeros.pop();
    }

    //-----------------------------------------------------------------------------------------
    // Inicializa las variables en la tabla de memoriaVariables, sea Int o Double con valor 0
    //-----------------------------------------------------------------------------------------
    public void inicializarVariables(String tipo, String nombre){
        switch (tipo){
            case "Entero":
                memoriaVariablesInt.put(nombre, 0.0);
                break;
            case "Real":
                memoriaVariablesDouble.put(nombre, 0);
                break;
            default:
                System.out.println("Error. Tipo de dato no valido");
        }
    }

    //-----------------------------------------------------------------------------------------
    // Asigna un valor a una variable
    //-----------------------------------------------------------------------------------------
    public void asignarValor(String nombre, double valor){
        if(memoriaVariablesInt.containsKey(nombre)){
            memoriaVariablesInt.put(nombre, valor);
        }else if(memoriaVariablesDouble.containsKey(nombre)){
            memoriaVariablesDouble.put(nombre, (int)valor);
        }else{
            System.out.println("Error: Variable no declarada");
        }
    }

    //-----------------------------------------------------------------------------------------
    // Obtiene el valor de una variable
    //-----------------------------------------------------------------------------------------
    public double obtenerValor(String nombre){
        if(memoriaVariablesInt.containsKey(nombre)){
            return memoriaVariablesInt.get(nombre);
        }else if(memoriaVariablesDouble.containsKey(nombre)){
            return memoriaVariablesDouble.get(nombre);
        }else{
            System.out.println("ERROR: Variable no declarada");
            return 0;
        }
    }

    //-----------------------------------------------------------------------------------------
    // Verifica si una variable existe en la tabla de memoriaVariables
    //-----------------------------------------------------------------------------------------
    public boolean esVariableDeclarada(String nombre){
        return memoriaVariablesInt.containsKey(nombre) || memoriaVariablesDouble.containsKey(nombre);
    }


    //-----------------------------------------------------------------------------------------
    // Imprime las tablas de memoriaVariables
    //-----------------------------------------------------------------------------------------
    public void imprimirTablas(){
        System.out.println("Memoria Tabla de Enteros");
        for(String key : memoriaVariablesInt.keySet()){
            System.out.println(key + " = " + memoriaVariablesInt.get(key));
        }
        System.out.println("Memoria Tabla de Reales");
        for(String key : memoriaVariablesDouble.keySet()){
            System.out.println(key + " = " + memoriaVariablesDouble.get(key));
        }
    }


        
}
