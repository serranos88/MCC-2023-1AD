import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        // Nombres de ciudades
        String[] nombresCiudades = {
            "MADRID", "PARIS", "AMSTERDAM", "ROMA", "ATHENS", "KIEV", "BRUGES", "LONDON", "LISBON", "BERLIN", "MOSCOW", "WARSAW"
        };

        // Distancias entre ciudades via terrestre
        int[][] distancias = {
            {0, 1051, 1480, 1364, 2368, 2861, 1312, 1261, 502, 1869, 3440, 2293},
            {1051, 0, 430, 1051, 2095, 2023, 268, 344, 1453, 878, 2480, 1370},
            {1480, 430, 0, 1480, 2164, 1780, 172, 359, 1863, 577, 2147, 1098},
            {1364, 1107, 1298, 0, 1048, 1675, 1253, 1436, 1863, 1184, 2375, 1318},
            {2368, 2095, 2164, 1048, 0, 1487, 2176, 2393, 2850, 1803, 2231, 1598},
            {2861, 2023, 1780, 1675, 1487, 0, 1908, 2136, 3351, 1203, 754, 685},
            {1312, 268, 172, 1253, 2176, 1908, 0, 237, 1090, 713, 2305, 1231},
            {1261, 344, 359, 1436, 2393, 2136, 237, 0, 1583, 934, 2503, 1455},
            {502, 1453, 1863, 1863, 2850, 3351, 1090, 1583, 0, 2312, 3905, 2763},
            {1869, 878, 577, 1184, 1803, 1203, 713, 934, 2312, 0, 1608, 1520},
            {3440, 2486, 2147, 2375, 2231, 754, 2305, 2503, 3905, 1608, 0, 1146},
            {2293, 1370, 1098, 1318, 1598, 685, 1231, 1455, 2763, 520, 1146, 0}
        };


        ArrayList<Integer> ciudades = new ArrayList<>();
        ciudades.add(0);
        ciudades.add(5);
        ciudades.add(11);
        ciudades.add(8);
        ciudades.add(3);
        ciudades.add(4);
        ciudades.add(6);
        ciudades.add(0);

        ArrayList<Integer> rutaOptima = FuerzaBruta.fuerzaBruta(ciudades, distancias);
        System.out.println("Ruta óptima: " + obtenerNombresCiudades(rutaOptima, nombresCiudades));
        System.out.println("Longitud óptima: " + FuerzaBruta.calcularLongitudRuta(rutaOptima, distancias));
    }

    // Método para obtener los nombres de las ciudades dada una ruta
    private static String obtenerNombresCiudades(ArrayList<Integer> ruta, String[] nombresCiudades) {
        StringBuilder nombres = new StringBuilder();
        for (int i = 0; i < ruta.size(); i++) {
            nombres.append(nombresCiudades[ruta.get(i)]);
            if (i < ruta.size() - 1) {
                nombres.append(" -> ");
            }
        }
        return nombres.toString();
    }
}
