import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;



public class Input {
    private final String RUTA; 
    
    private Characters oraciones;
    

    public Input(String ruta) {
        this.oraciones = new Characters();
        this.RUTA = ruta;
    }

    public void leerTexto() {
        try (BufferedReader bf = new BufferedReader(new FileReader(RUTA))) {
            String s;
            while ((s = bf.readLine()) != null) {
                if (!s.trim().isEmpty()) {
                    oraciones.setChar(s);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /*
    public static void imprimirOraciones(ArrayList<String> lista) {
        for (String linea : lista) {
            System.out.println(linea);
        }
    }*/


}

