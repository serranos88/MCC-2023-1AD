package Analisis;
import java.util.HashMap;
import java.util.Stack;
import Constantes.Operador;
import Lexer.Lexer;
import Memoria.Memoria;

import java.lang.Math;
/*Clase Mate es in singleton */
/*
 * Clase Matematica que realiza las operaciones matematicas
 * y gestiona las variables en una Coleccion de memoriaVariables
 * Realiza operaciones de declaracion, asignacion y obtencion de variables
 */
public final class Mate {
    private Memoria memoria;
    protected Stack<String> pilaOperadores = new Stack<String>();
    protected Stack<Double> pilaNumeros = new Stack<Double>();

    private static Mate INSTANCIA = null;

    private Mate() {
       this.memoria = Memoria.getInstancia();
    }

    public static Mate getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new Mate();
        }
        return INSTANCIA;
    }

    //-----------------------------------------------------------------------------------------
    // Realiza la operacion matematica entre dos numeros
    //-----------------------------------------------------------------------------------------
    public double realizarOperacion(double num1, double num2, String operador) {
        switch (operador) {
            case "+":
                
                return num1 + num2;
            case "-":
                
                return num1 - num2;
            case "/":
                
                return num1 / num2;
            case "*":
                
                return num1 * num2;
            case "^":
                
                return Math.pow(num1, num2);
            default:
                return 0;
        }
    }

   

    //-----------------------------------------------------------------------------------------
    // Metodo que resuelve una expresion algebraica
    //-----------------------------------------------------------------------------------------
    public double resolverExpresion(String expresion) {
        String[] tokens = Lexer.tokenizarLinea(expresion);
        for (String token : tokens) {
            colocarTokenEnPila(token);
        }
        retirarPilaOperador();
        return obtenerResultado();
        }
    
    public double resolverExpresion(String[] tokens) {
        if(tokens.length == 4){
                return Double.parseDouble(tokens[4]);
        }else if(tokens.length > 4){
            for (int i = 3; i < tokens.length; i++) {
                if(esNumero(tokens[i])){
                    pilaNumeros.push(Double.parseDouble(tokens[i]));
                    Depurardor.printEstado("Se coloco NUMERO en la pila de numeros: " + tokens[i]);

                }else if(Operador.esOperador(tokens[i])){
                    colocarOperadorenPila(tokens[i]);

                } else if(memoria.esVariableDeclarada(tokens[i]) && memoria.obtenerTipo(tokens[i]).matches("(Entero|Real)")){
                    pilaNumeros.push(memoria.obtenerValor(tokens[i]).doubleValue());
                    Depurardor.printEstado("Se coloco Variable NUMERICA en la pila de numeros: " + memoria.obtenerValor(tokens[i]).doubleValue());

                }
            }
            retirarPilaOperador();
            return obtenerResultado();
        }else {
            Depurardor.printEstado("ERROR SINTAXIS: Asignacion de valor a variable despues de =");
            return 0;
        }
    }

    

    //-----------------------------------------------------------------------------------------
    // Coloca el operador en la pila de operadores
    //-----------------------------------------------------------------------------------------
    public void colocarOperadorenPila(String operador) {
        if (pilaOperadores.isEmpty() || operador.equals(Operador.PARENTESIS_ABIERTO.getOperador())) {
            pilaOperadores.push(operador);
        } else {
            if(operador.equals(Operador.PARENTESIS_CERRADO.getOperador())){
                retirarPilaOperador();
            }
            // Si el operador tiene MAYOR Prioridad que el ultimo operador en la pila
            else if (Operador.esMayorPrioridad(operador, pilaOperadores.peek())) {
                pilaOperadores.push(operador);
            } else { // Si el operador tiene MENOR Prioridad que el ultimo operador en la pila, resuelve las operaciones
                while (!pilaOperadores.isEmpty() && !Operador.esMayorPrioridad(operador, pilaOperadores.peek())) {
                    double num2 = pilaNumeros.pop();
                    double num1 = pilaNumeros.pop();
                    String operadorAux = pilaOperadores.pop();
                    double resultado = realizarOperacion(num1, num2, operadorAux);
                    pilaNumeros.push(resultado);
                    //BORRAR
                    Depurardor.printInfo("Se realizo Operacion: " + num2 + " " + operadorAux + " " + num1);
                    
                    //DEBUG
                    for(double num : pilaNumeros){
                        Depurardor.printInfo("Pila Numeros: " + num);
                    }
                }

                pilaOperadores.push(operador);
                // DEBUG
                for(String operadorAux : pilaOperadores){
                    Depurardor.printInfo("Pila Operadores: " + operadorAux);
                }
            }
        }
    }

    //-----------------------------------------------------------------------------------------
    // Si el token es un operador, lo coloca en la pila de operadores
    // Si el token es una Variable numero, lo coloca en la pila de numeros
    //-----------------------------------------------------------------------------------------
    
    public void colocarTokenEnPila(String token){
        if (Operador.esOperador(token)) {
            colocarOperadorenPila(token);
            Depurardor.printEstado("Se coloco Operador en la pila de operadores: " + token);

        }else if(memoria.esVariableDeclarada(token)&& memoria.obtenerTipo(token).matches("(Entero|Real)")){
            pilaNumeros.push(memoria.obtenerValor(token).doubleValue());
            Depurardor.printEstado("Se coloco Variable en la pila de numeros: " + memoria.obtenerValor(token).doubleValue());

        }
        else {
            System.out.println("Error: " + token + " no es un operador");
        }
    }

    public void colocarTokenEnPila(double token){
        pilaNumeros.push(token);
        Depurardor.printEstado("Se coloco NUMERO en la pila de numeros: " + token);
    }

    //-----------------------------------------------------------------------------------------
    // Vaciar la pila de operadores
    //-----------------------------------------------------------------------------------------
    public void retirarPilaOperador() {
        while (!pilaOperadores.isEmpty()) {
            if(pilaOperadores.peek().equals(Operador.PARENTESIS_ABIERTO.getOperador())){
                pilaOperadores.pop();
                break;

            } else if(pilaOperadores.peek().equals(Operador.PARENTESIS_CERRADO.getOperador())){
                pilaOperadores.pop();

            }else {
            double num2 = pilaNumeros.pop(); // ejemplo 2 + 5  {2,5} -> 5 --> {2,}
            double num1 = pilaNumeros.pop(); // ejemplo 2 + 5  {2,} -> 2 --> {}
            String operador = pilaOperadores.pop();
            
            Depurardor.printInfo("Vaciando la pila Operadores y numeros num1: " + num1 + " num2: " + num2 + " operador: " + operador);
            pilaNumeros.push(realizarOperacion(num1, num2, operador));
            }
        }
    }

    //-----------------------------------------------------------------------------------------
    // Resultado de la operacion
    //-----------------------------------------------------------------------------------------
    public double obtenerResultado() {
        return pilaNumeros.pop();
    }


    //----------------------------------------------
    // Valida si el token es un numero
    //----------------------------------------------
    public boolean esNumero(String token){
        try{
            Double.parseDouble(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    

}
