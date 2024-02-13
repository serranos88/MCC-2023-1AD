package Constantes;

public enum BloqueSi{
    SI("SI" , "\\s*SI\\s*\\(.*\\)\\s*\\{\\s*"),
    SINO("SINO" , "\\s*SINO\\s*SI\\s*\\(.*\\)\\s*\\{\\s*"),
    SINO_SI("SINO_SI" , "\\s*SINO\\s*\\{\\s*");

    public final String bloque;
    public final String estructura;

    BloqueSi(String bloque, String estructura) {
        this.bloque = bloque;
        this.estructura = estructura;
    }

    //----------------------------------------------------------------------
    // Obtiene el bloque segun el simbolo
    //----------------------------------------------------------------------
    public static BloqueSi getBloque(String bloqueSimbolo) {
        for(BloqueSi op : BloqueSi.values()) {
            if(op.getBloque().equals(bloqueSimbolo)) {
                return op;
            }
        }
        return null; // No se encontro el bloque  
    }

    //----------------------------------------------------------------------
    // Getters
    //----------------------------------------------------------------------
    public String getBloque() {
        return bloque;
    }

    public String getEstructura() {
        return estructura;
    }

    
}