import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Indexador {
    private Libro paginas;
    private Characters palabrasIndice;

    public Indexador() {
        this.paginas = new Libro(); // El ArrayList de paginas es el mismo en memoria, es static
        this.palabrasIndice = new Characters(); // El ArrayList de palabrasIndice es el mismo en memoria, es static
    }

    
    public void indexar(){
        ArrayList<String> palabrasAux = new ArrayList<>();
        int numPagina=0;
      
       for (String palabra : Characters.getLines()){
                
                String palabraAux = palabra; 

              for (String pagina : Libro.getPaginas()){
                
                if (contienePalabras( palabraAux, pagina ) ) {
                     // Busca si pagina tiene numero de pagina al inicio de la cadena
                    Pattern patron = Pattern.compile("^\\d+");
                    Matcher matcher = patron.matcher(pagina);
                    // Verificar si se encuentra una coincidencia
                    if (matcher.find()) {
                        // Devolver la cadena de números encontrada
                        numPagina =  Integer.parseInt(matcher.group());//matcher.group();
                        palabra += "," + numPagina;

                    } else {
                        // No tiene numero de pagina    
                    }   
                }
              }

                palabrasAux.add(palabra);
       }
        //Libro.setNull();
        palabrasIndice.setChars(palabrasAux);
    }

    // Metodo para verificar si una pagina contiene las palabras del indice
    // Acepta: palabra1 -- una sola palabra
    // Acepta: palabra1 AND palabra2 AND palabra3  --- palabras no necesariamente consecutivas
    // Acepta: palabra1 palabra2 --- palabras consecutivas
    public static boolean contienePalabras(String palabraIndice, String pagina){
        String paginaAux = pagina.toLowerCase();
        
        String [] palabras = palabraIndice.split(" AND ");
        for (String p : palabras){
            
            if (!paginaAux.contains(p.toLowerCase())){
                return false;
            }
        }
        return true;
    }

    public static ArrayList<String> getPalabrasIndexadas(){
        return Characters.getLines();
    }

    
}
