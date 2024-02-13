package Constantes;
//----------------------------------------------------------
// GARCIA SERRANO JOSE ANTONIO	
//El enum Operador tiene todos los simbolos utilizados
//	para las operaciones Matematicas, y la prioridad 
//	de ejecucion en una ecuacion
//----------------------------------------------------------
// 	Se utiliza en las clases AnalisisSintaxis y Mate

public enum Operador {
    POTENCIA("^", 3),
    MULTIPLICACION("*", 2),
    DIVISION("/", 2),
    SUMA("+", 1),
    RESTA("-", 1),
    PARENTESIS_ABIERTO("(", 0),
    PARENTESIS_CERRADO(")", 0);

        public final String operador;
        public final int prioridad;

        Operador(String operador, int prioridad) {
            this.operador = operador;
            this.prioridad = prioridad;
        }

        //----------------------------------------------------------------------
        // Obtiene el operador segun el simbolo
        //----------------------------------------------------------------------
        public static Operador getOperador(String operadorSimbolo) {
            for(Operador op : Operador.values()) {
                if(op.getOperador().equals(operadorSimbolo)) {
                    return op;
                }
            }
            return null; // No se encontro el operador  
        }

        //----------------------------------------------------------------------
        // Getters
        //----------------------------------------------------------------------
        public String getOperador() {
            return operador;
        }

        public int getPrioridad() {
            return prioridad;
        }
    
    //----------------------------------------------------------------------
    // Verifica si el operador es un simbolo valido
    //----------------------------------------------------------------------
    public static boolean esOperador(String operador) {
        for (Operador op : Operador.values()) {
            if (op.getOperador().equals(operador)) {
                //DEBUG System.out.println("ES OPERADOR: "+operador); //DEBUG
                return true;
            }
        }
        //DEBUG System.out.println("no ES OPERADOR: "+operador); //DEBUG
        return false;
    }
    
    //----------------------------------------------------------------------
    // Compara la prioridad de dos operadores
    //----------------------------------------------------------------------
    public static boolean esMayorPrioridad(String operador1, String operador2) {
        return Operador.getOperador(operador1).getPrioridad() > Operador.getOperador(operador2).getPrioridad();
    }

}



