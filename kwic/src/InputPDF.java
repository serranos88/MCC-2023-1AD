import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.Splitter;


public class InputPDF {
    private final String RUTA;
    //private Characters paginas;
    private Libro paginas;

     public InputPDF(String ruta) {
         this.paginas = new Libro();
         this.RUTA = ruta;
     }

        public void leerPDF() {
            try (PDDocument documento = Loader.loadPDF(new File(RUTA))) {

                int paginaActual = 1;
                Splitter splitter = new Splitter();

                List<PDDocument> paginasPDF = splitter.split(documento);

                PDFTextStripper stripper = new PDFTextStripper();
                
                System.out.println(stripper.getStartPage());
                

                for(PDDocument pagina : paginasPDF){
                    
                    this.paginas.setPagina(paginaActual+ "|" + stripper.getText(pagina));
                    
                    paginaActual++;   
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
