import java.util.ArrayList;

public class Libro {
    private static ArrayList<String> paginas;

    public Libro() {
        paginas = new ArrayList<>();
    }

    public void setPagina(String paginaTexto) {
        paginas.add(paginaTexto);
    }

    public static ArrayList<String> getPaginas() {
        return paginas;
    }

    public static void setNull() {
        paginas = null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String pagina : paginas) {
            sb.append(pagina).append("\n");
        }
        return sb.toString();
    }
}
