package mlp.enums;

public enum COMPARADOR{
    MAYOR(">"),
    MAYOR_IGUAL(">="),
    MENOR("<"),
    MENOR_IGUAL("<="),
    DIFERENTE("<>"),
    IGUAL_LOGICO("==");

    private final String operador;

    COMPARADOR(String operador) {
        this.operador = operador;
    }

    public String getOperador() {
        return operador;
    }

    public String toString() {
		return operador;
	}

	static public boolean isComparador(String linea) {
		for(int i=0 ; i < DATA_TIPO.values().length ; i++ ) {
			if( linea.contains( COMPARADOR.values()[i].toString() ) ) {
				return true;
			}
		}
		return false;
		}
}
