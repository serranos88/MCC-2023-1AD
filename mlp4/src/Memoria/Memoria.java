/* GARCIA SERRANO JOSE ANTONIO */
package Memoria;

import java.util.HashMap;

import Analisis.Depurardor;
//import Lexer.Funcion;

public final class Memoria {
    private HashMap<String, Variable> variables = new HashMap<String, Variable>();
    //private HashMap<String, Funcion> funciones = new HashMap<String, Funcion>(); 
    //private HashMap<int, Bloque> bloques = new HashMap<int, Bloque>();


    private static Memoria INSTANCIA = null;

    private Memoria() {}

    public static Memoria getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new Memoria();
        }
        return INSTANCIA;
    }

    //-----------------------------------------------------------------------------------------
    // Declara una variable en la memoria
    //-----------------------------------------------------------------------------------------
    public void declararVariable(String tipo, String nombre) {
        if(variables.containsKey(nombre)){
            System.out.println("Error: Variable ya declarada");
        }else{
            variables.put(nombre, new Variable(nombre, tipo, ""));
        }
    }


/*
    //-----------------------------------------------------------------------------------------
    // OBSOLETO - Guarda una variable en la memoria
    //-----------------------------------------------------------------------------------------
    public void guardarVariable(String nombre, int valor) {
        if(variables.containsKey(nombre)){
            System.out.println("Error: Variable ya declarada");
        }else{
            variables.put(nombre, new Variable(nombre, "Entero", String.valueOf(valor)));
        }
    }

    public void guardarVariable(String nombre, double valor) {
        if(variables.containsKey(nombre)){
            System.out.println("Error: Variable ya declarada");
            Depurardor.printError("Error variable ya declarada "+nombre);
        }else{
            variables.put(nombre, new Variable(nombre, "Real", String.valueOf(valor)));
        }
    }

    public void guardarVariable(String nombre, String valor) {
        if(variables.containsKey(nombre)){
            System.out.println("Error: Variable ya declarada");
        }else{
            variables.put(nombre, new Variable(nombre, "Cadena", valor));
        }
    }*/

    //-----------------------------------------------------------------------------------------
    // Asigna un valor a una variable
    //-----------------------------------------------------------------------------------------
    public void asignarValor(String nombre, double valor){
        if(variables.containsKey(nombre)){
            if(variables.get(nombre).getTipo().equals("Entero")){
                variables.get(nombre).setValor((int)valor);
            }else if(variables.get(nombre).getTipo().equals("Real")){
                variables.get(nombre).setValor(valor);
            }else{
                System.out.println("ERROR: Variable no es Entero o Real");
            }
        }else{
            System.out.println("ERROR: Variable no declarada");
        }
    }

    public void asignarValor(String nombre, int valor){
        if(variables.containsKey(nombre)){
            if(variables.get(nombre).getTipo().equals("Entero")){
                variables.get(nombre).setValor(valor);
            }else if(variables.get(nombre).getTipo().equals("Real")){
                variables.get(nombre).setValor((double)valor);
            }else{
                System.out.println("ERROR: Variable no es Entero o Real");
            }
        }else{
            System.out.println("ERROR: Variable no declarada");
        }
    }

    
    public void asignarValor(String nombre, String valor){
        String tipoVariable = variables.get(nombre).getTipo();
        String tipoVar2 = "";
        String valorStringVar2 = "";
        int valorIntVar2 = 0;
        double valorDoubleVar2 = 0.0;
        

        if(variables.containsKey(nombre) && !esVariableDeclarada(valor)){ // Evalua si el parametro valor NO ES una variable


            if(tipoVariable.equals("Cadena")){
                System.out.println("DEBUG: Asignando valor a variable tipo Cadena");
                variables.get(nombre).setValor(valor);
            } else if (tipoVariable.equals("Entero")){
                System.out.println("DEBUG: Asignando valor a variable tipo Entero");
                variables.get(nombre).setValor(Integer.parseInt(valor));
            } else if (tipoVariable.equals("Real")){
                System.out.println("DEBUG: Asignando valor a variable tipo Real");
                variables.get(nombre).setValor(Double.parseDouble(valor));
            }else{
                System.out.println("ERROR: Variable no es Cadena");
            }

        }else if(esVariableDeclarada(nombre) && esVariableDeclarada(valor)){ // En caso de que el parametro valor SEA una variable
                
                tipoVar2 = obtenerTipo(valor); // La Asignacion del valor siempre se hara con el tipo de la Variable a la que se asigna
                if(tipoVariable.equals("Cadena") && tipoVar2.equals("Cadena")){

                    System.out.println("DEBUG: Asignando Variable tipo Cadena a Variable tipo Cadena");
                    valorStringVar2 = obtenerValorCadena(valor);
                    variables.get(nombre).setValor(valorStringVar2);

                } else if (tipoVariable.equals("Entero") && tipoVar2.equals("Entero")){

                    System.out.println("DEBUG: Asignando Variable tipo Entero a Variable tipo Entero");
                    valorIntVar2 = obtenerValor(valor).intValue();
                    variables.get(nombre).setValor(valorIntVar2); // varEntero = varEntero --> Se asigna el valor como Entero

                } else if (tipoVariable.equals("Real") && tipoVar2.equals("Real")){

                    System.out.println("DEBUG: Asignando Variable tipo Real a Variable tipo Real");
                    valorDoubleVar2 = obtenerValor(valor).doubleValue();
                    variables.get(nombre).setValor(valorDoubleVar2); // varReal = varReal --> Se asigna el valor como Real

                } else if (tipoVariable.equals("Real") && tipoVar2.equals("Entero")){

                    System.out.println("DEBUG: Asignando Variable tipo Entero a Variable tipo Real");
                    valorDoubleVar2 = obtenerValor(valor).doubleValue();
                    variables.get(nombre).setValor(valorDoubleVar2); // varReal = varEntero --> Se asigna el valor como Real

                } else if (tipoVariable.equals("Entero") && tipoVar2.equals("Real")){
                    
                    System.out.println("DEBUG: Asignando Variable tipo Real a Variable tipo Entero");
                    valorIntVar2 = obtenerValor(valor).intValue();
                    variables.get(nombre).setValor(valorIntVar2); // varEntero = varReal --> Se asigna el valor como Entero

                } else{
                    System.out.println("ERROR: Variable no es Cadena o Numerica");
                }
                
            } else {
                System.out.println("ERROR: Variable no declarada: " + nombre);
            }


    }

    //-----------------------------------------------------------------------------------------
    // Obtiene el valor de una variable
    //-----------------------------------------------------------------------------------------
    public Number obtenerValor(String nombre){
        // Con try catch se evita evaluar si la variable esta en memoria como declarada
        // Igualmente evitamos evaluar si la variable es Entero o Real
        try {
            return Integer.parseInt(variables.get(nombre).getValor());
        } catch (NumberFormatException e) {
            try {
                return Double.parseDouble(variables.get(nombre).getValor());
            } catch (NumberFormatException e2) {
                System.out.println("ERROR: Variable numerica no declarada");
                return 0;
            }
        }
    }
    

    public String obtenerValorCadena2(String nombre){
        try {
            return variables.get(nombre).getValor();
        } catch (Exception e) {
            System.out.println("ERROR: Variable tipo Cadena no declarada");
            return "";
        }
    }

    public String obtenerValorCadena(String nombre){
        if(variables.containsKey(nombre)){
            if(variables.get(nombre).getTipo().equals("Cadena")){
                return variables.get(nombre).getValor();
            }else{
                System.out.println("ERROR: Variable no es Cadena");
                return "";
            }
        }else{
            System.out.println("ERROR: Variable no declarada");
            return "";
        }
    }

    //-----------------------------------------------------------------------------------------
    // Obtiene el tipo de una variable
    //-----------------------------------------------------------------------------------------
    public String obtenerTipo(String nombre){
        if(variables.containsKey(nombre)){
            return variables.get(nombre).getTipo();
        }else{
            System.out.println("ERROR: Variable no declarada");
            return "";
        }
    }

    //-----------------------------------------------------------------------------------------
    // Verifica si una variable existe en la tabla de variables
    //-----------------------------------------------------------------------------------------
    public boolean esVariableDeclarada(String nombre){
        return variables.containsKey(nombre);
    }

    //-----------------------------------------------------------------------------------------
    // Imprime las tablas de variables
    //-----------------------------------------------------------------------------------------
    public void imprimirTablaVariables(){
        System.out.println("\nVARIABLES EN MEMORIA");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-10s%-10s%-10s\n", "Nombre", "Tipo", "Valor");
        System.out.println("--------------------------------------------------");
        for (String key : variables.keySet()) {
            System.out.printf("%-10s%-10s%-10s\n", key, variables.get(key).getTipo(), variables.get(key).getValor());
        }
    }

    //-----------------------------------------------------------------------------------------
    // Guarda una funcion en la memoria
    //-----------------------------------------------------------------------------------------
    public void guardarFuncion(String nombre, Funcion funcion) {
        if(funciones.containsKey(nombre)){
            System.out.println("Error: Funcion ya declarada");
        }else{
            funciones.put(nombre, funcion);
        }
    }

    //-----------------------------------------------------------------------------------------
    // Obtiene una funcion de la memoria
    //-----------------------------------------------------------------------------------------
    public Funcion obtenerFuncion(String nombre){
        if(funciones.containsKey(nombre)){
            return funciones.get(nombre);
        }else{
            System.out.println("ERROR: Funcion no declarada");
            return null;
        }
    }

}
