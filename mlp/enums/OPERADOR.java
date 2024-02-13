    package mlp.enums;

    public enum OPERADOR {
        POTENCIA("^", 3),
        MULTIPLICACION("*", 2),
        DIVISION("/", 2),
        SUMA("+", 1),
        RESTA("-", 1),
        PARENTESIS_ABIERTO("(", 0),
        PARENTESIS_CERRADO(")", 0);

        private final String operador;
        private final int prioridad;

        OPERADOR(String operador, int prioridad) {
            this.operador = operador;
            this.prioridad = prioridad;
        }

        public String getOperador() {
            return operador;
        }

        public int getPrioridad() {
            return prioridad;
        }
    }

