package Analisis;

public class Depurardor {
    
    public static void printEstado(String mensaje) {
        System.out.println("\u001B[32m" + mensaje + "\u001B[0m"); // color verde
    }

    public static void printError(String mensaje) {
        System.out.println("\u001B[31m" + mensaje + "\u001B[0m"); // color rojo
    }

    public static void printWarning(String mensaje) {
        System.out.println("\u001B[33m" + mensaje + "\u001B[0m"); // color amarillo
    }

    public static void printInfo(String mensaje) {
        System.out.println("\u001B[34m" + mensaje + "\u001B[0m"); // color azul
    }
}
