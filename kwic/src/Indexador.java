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
        ArrayList<String> paginasAux = new ArrayList<>();
        ArrayList<String> palabrasAux = new ArrayList<>();
        int numPagina=0;
      
       for (String palabra : Characters.getLines()){
                
                String palabraAux = palabra; //palabra.split(" | ")[0];
                palabraAux = palabraAux.split(" | ")[0];
                palabraAux.toLowerCase();

              for (String pagina : Libro.getPaginas()){
                pagina.toLowerCase();
                if (pagina.contains(palabraAux)){ // Cambiar por contienePalabras()
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

    private boolean contienePalabras(String palabraIndice, String pagina){
        // palabra necesita un split con el siguiente patron " AND "
        String [] palabras = palabraIndice.split(" AND ");
        for (String p : palabras){
            if (!pagina.contains(p)){
                return false;
            }
        }
        return true;
        //return pagina.contains(palabra);
    }

    
}
