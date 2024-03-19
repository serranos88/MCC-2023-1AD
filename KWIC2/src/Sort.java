import java.util.ArrayList;

public class Sort {

    private static ArrayList<String> oracionesOrdenadas;
    private static ArrayList<String> oracionesRotadas;

    public Sort() {
        oracionesOrdenadas = new ArrayList<>();
        oracionesRotadas = CircularShifter.getOracionesRotadas();
    }

    public void alfabeticamente() {
        //oracionesOrdenadas = sort(oracionesRotadas);
        oracionesOrdenadas = new ArrayList<>(oracionesRotadas);

        int n = oracionesOrdenadas.size();
        String aux;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (oracionesOrdenadas.get(j - 1).compareToIgnoreCase(oracionesOrdenadas.get(j)) > 0) {
                    aux = oracionesOrdenadas.get(j - 1);
                    oracionesOrdenadas.set(j - 1, oracionesOrdenadas.get(j));
                    oracionesOrdenadas.set(j, aux);
                }
            }
        }


    }

    // Metodo get para obtener el ArrayList ordenado
    public static ArrayList<String> getOracionesOrdenadas() {
        return oracionesOrdenadas;
    }

}
