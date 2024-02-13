package Constantes;

public enum TIPO_INSTRUCCION {
    DECLARAR(""), ASIGNAR(""), LEER(""), IMPRIMIR(""), SI(""), MIENTRAS("");

    private String estructura;

    TIPO_INSTRUCCION(String estructura) {
        this.estructura = estructura;
    }

    public String getEstructura() {
        return estructura;
    }

    
}


