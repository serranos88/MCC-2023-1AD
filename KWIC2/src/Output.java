import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Output {
    private static ArrayList<String> oracionesOrdenadas;

    public Output() {
        oracionesOrdenadas = Sort.getOracionesOrdenadas();
    }


    public void imprimir(){

        System.out.println("ArrayList ordenado alfabéticamente:");
        oracionesOrdenadas.forEach(System.out::println);
   }

   public static void guardar(String ruta){
       // Guardar el ArrayList ordenado alfabéticamente en un archivo de texto
       File archivo = new File(ruta);
       try (PrintWriter pw = new PrintWriter(archivo)) {
           for (String linea : oracionesOrdenadas) {
               pw.println(linea);
           }
       } catch (FileNotFoundException ex) {
           ex.printStackTrace();
       }
   }
}
