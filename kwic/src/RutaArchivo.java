import java.io.File;
import java.util.ArrayList;


public class RutaArchivo {
    private String rutaCarpeta;

    public RutaArchivo(String rutaCarpeta) {
        this.rutaCarpeta = rutaCarpeta;
    }

    public void listarArchivos() {
        // Crear un objeto File con la ruta de la carpeta
        File carpeta = new File(rutaCarpeta);

        // Verificar si la ruta corresponde a una carpeta y si existe
        if (carpeta.isDirectory()) {
            // Obtener una lista de archivos en la carpeta
            File[] archivos = carpeta.listFiles();

            // Iterar sobre los archivos y mostrar sus nombres
            if (archivos != null) {
                for (File archivo : archivos) {
                    System.out.println(archivo.getName());
                }
            }
        } else {
            System.out.println("La ruta no corresponde a una carpeta o no existe.");
        }
    }

    public void listarArchivos(String palabraClave) {
        // Crear un objeto File con la ruta de la carpeta
        File carpeta = new File(rutaCarpeta);

        // Verificar si la ruta corresponde a una carpeta y si existe
        if (carpeta.isDirectory()) {
            // Obtener una lista de archivos en la carpeta
            File[] archivos = carpeta.listFiles();

            // Iterar sobre los archivos y mostrar sus nombres si contienen la palabra clave o tienen la extensión especificada
            if (archivos != null) {
                for (File archivo : archivos) {
                    String nombreArchivo = archivo.getName();
                    if (nombreArchivo.contains(palabraClave) || nombreArchivo.endsWith(palabraClave)) {
                        System.out.println(nombreArchivo);
                    }
                }
            }
        } else {
            System.out.println("La ruta no corresponde a una carpeta o no existe.");
        }
    }

}
