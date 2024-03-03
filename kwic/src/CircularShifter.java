import java.util.ArrayList;
import java.util.Arrays;



public class CircularShifter {
    private static ArrayList<String> oracionesRotadas;

    public CircularShifter() {
        oracionesRotadas = new ArrayList<>();
    }

    public void setup(){
        oracionesRotadas = rotarOraciones(Characters.getLines());
    }


    
    private static ArrayList<String> rotarOraciones(ArrayList<String> informacion) {
        ArrayList<String> oracionesRotadas = new ArrayList<>();

        for (String oracion : informacion) {
           

            String[] palabras = oracion.split("\\s+");
            ArrayList<String> listaPalabras = new ArrayList<>(Arrays.asList(palabras));

            ArrayList<String> oracionRotada = new ArrayList<>();

            for (int i = 0; i < listaPalabras.size(); i++) {
                // Sacar el primer elemento y colocarlo al final del arreglo
                String elementoSacado = listaPalabras.remove(0);
                listaPalabras.add(elementoSacado);

                // Construir la oración completa después de rotar todas las palabras
                StringBuilder oracionCompleta = new StringBuilder();
                for (String palabra : listaPalabras) {
                    oracionCompleta.append(palabra).append(" ");
                }
                // Agregar la oración rotada a la lista de oraciones rotadas
                oracionRotada.add(oracionCompleta.toString().trim());
            }
            // Agregar la lista de oraciones rotadas al resultado final
            oracionesRotadas.addAll(oracionRotada);
        }
    
        return oracionesRotadas;
    }

    // Metodo get para obtener las oraciones rotadas
    public static ArrayList<String> getOracionesRotadas() {
        return oracionesRotadas;
    }

       


}