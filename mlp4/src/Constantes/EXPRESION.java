package Constantes;

public enum EXPRESION {
    ASIGNACION("=");

    private String expresion;

    EXPRESION(String expresion) {
        this.expresion = expresion;
    }

    public String getExpresion() {
        return expresion;
    }

    public static EXPRESION getExpresion(String expresion) {
        for (EXPRESION e : EXPRESION.values()) {
            if (e.getExpresion().equals(expresion)) {
                return e;
            }
        }
        return null;
    }

    
}
