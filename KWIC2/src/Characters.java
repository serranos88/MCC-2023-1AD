import java.util.ArrayList;

public class Characters {
    private static ArrayList<String> oraciones;
    

    public Characters() {
        oraciones = new ArrayList<>();
    }

    // Interfaz para comunicar Input con Characters
    public void setChar(String linea) {
        oraciones.add(linea);
    }


    // Interfaz para comunicar Characters con CircularShifter
    public static ArrayList<String> getLines() {
        return oraciones;
    }


    @Override
    public String toString() {
        return oraciones.toString();
    }
}
