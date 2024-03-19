import java.util.ArrayList;
import java.util.HashMap;

public class Characters {
    private static ArrayList<String> oraciones;
    
    

    public Characters() {
        oraciones = new ArrayList<>();
        //oraciones = Characters.getLines();
    }

    // Interfaz para comunicar Input con Characters
    public void setChar(String linea) {
        oraciones.add(linea);
    }

    public void setChars(ArrayList<String> lineas) {
        oraciones = lineas;
    }


    // Interfaz para comunicar Characters con CircularShifter
    public static ArrayList<String> getLines() {
        return oraciones;
    }

    public static void setNull(){
        oraciones = null;
    }

    


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String linea : oraciones) {
            sb.append(linea).append("\n");
        }
        return sb.toString();
    }
}
