package mlp.enums;

public enum DATA_TIPO {
ENTERO("entero"), REAL("real"), TEXTO("texto"), IGUAL("=");

		
	private String declaracion;
		
	private DATA_TIPO(String declaracion) {
		this.declaracion = declaracion;
	}
	
	public String toString() {
		return this.declaracion;
	}

	static public boolean isDeclaracion(String linea) {
		if(COMPARADOR.isComparador(linea)) {
			return false;
		}
		for(int i=0; i<DATA_TIPO.values().length;i++) {
			if(linea.contains(DATA_TIPO.values()[i].toString())) {
				return true;
			}
		}
		return false;
				
		}

	static public boolean isAsignacion(String linea) {
		if(COMPARADOR.isComparador(linea)) {
			return false;
		}
		if(linea.contains(DATA_TIPO.IGUAL.toString())) {
			return true;
		}
		return false;
	}

	static public boolean isLeer(String linea) {
		if(COMPARADOR.isComparador(linea)) {
			return false;
		}
		if(linea.contains(PalabraReservada.LEER.toString())) {
			return true;
		}
		return false;
	}

	static public boolean isImpresion(String linea) {
		if(COMPARADOR.isComparador(linea)) {
			return false;
		}
		if(linea.contains(PalabraReservada.IMPRIME.toString())) {
			return true;
		}
		return false;
	}

	static public boolean isOperacion(String linea) {
		if(COMPARADOR.isComparador(linea)) {
			return false;
		}
		for(int i=0; i<OPERADOR.values().length;i++) {
			if(linea.contains(OPERADOR.values()[i].toString())) {
				return true;
			}
		}
		return false;
	}

	static public boolean isCondicion(String linea) {
		if(COMPARADOR.isComparador(linea)) {
			return true;
		}
		return false;
	}

	static public boolean isCiclo(String linea) {
		if(COMPARADOR.isComparador(linea)) {
			return false;
		}
		if(linea.contains(PalabraReservada.MIENTRAS.toString())) {
			return true;
		}
		return false;
	}

	static public boolean isFin(String linea) {
		if(COMPARADOR.isComparador(linea)) {
			return false;
		}
		if(linea.contains(PalabraReservada.FIN.toString())) {
			return true;
		}
		return false;
	}

	

}