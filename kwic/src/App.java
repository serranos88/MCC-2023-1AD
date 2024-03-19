import java.io.Reader;
import java.util.Scanner;
import java.io.File;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) throws Exception {
        Input input;
        InputPDF inputPDF;
        Output output;
        Indexador indexador;
        Sort sort;
        RutaArchivo rutaArchivo;
        String nombreLibro;
        String nombreArchivo;
        final String RUTARAIZ = "C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\kwic\\src\\"; 
        indexador = new Indexador();
        rutaArchivo = new RutaArchivo(RUTARAIZ);
        
        
        Scanner scanner = new Scanner(System.in);
        
        
        
        


        //input = new Input("C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\kwic\\src\\palabras.txt");
        //input = new Input("C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\kwic\\src\\palabras2.txt");
        //input  = new Input("C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\kwic\\src\\palabras3.txt");
        //input = new Input("C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\kwic\\src\\palabras4.txt");
        //inputPDF = new InputPDF("C:\\Users\\josek\\Documents\\gitMCC\\MCC-2023-1AD\\kwic\\src\\documento.pdf");

        System.out.println("Escriba el nombre del libro a indexar");
        // leer string de consola
        System.out.println("Archivos en la carpeta:");
        rutaArchivo.listarArchivos(".pdf");
        nombreLibro = scanner.nextLine();
        inputPDF = new InputPDF(RUTARAIZ + nombreLibro + ".pdf");


        System.out.println("Proporcione el nombre del archivo con la extensión .txt con las palabras a indexar");
        System.out.println("Archivos en la carpeta:");
        rutaArchivo.listarArchivos(".txt");
        nombreArchivo = scanner.nextLine();
        input = new Input(RUTARAIZ + nombreArchivo + ".txt");


        // EJEMPLO KWIC PARA INDEXAR UN LIBRO EN PDF
        //Characters oraciones = new Characters();
        //Libro paginas = new Libro();
        inputPDF.leerPDF();
        input.leerTexto();
        

        //indexador = new Indexador();
        indexador.indexar();

        sort = new Sort();
        sort.alfabeticamente();

        output = new Output();
        output.imprimir();



        /* 
        // EJEMPLO KWIC CON ARCHIVO DE TEXTO
        input.leerTexto();
        shifter = new CircularShifter();
        shifter.setup();

        sort = new Sort();
        sort.alfabeticamente();
        
        output = new Output();
        output.imprimir();
        */


    }
}
