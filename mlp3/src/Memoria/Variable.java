package Memoria;

public class Variable {
    private String nombre;
    private String tipo; // Entero, Real, Cadena
    private String valor; 
    private boolean esParametro; // TODO: Implementar para manejo de declaracion de Funciones


    public Variable(String nombre, String tipo, String valor) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
    }


    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }

    public void asignarValor(String valor){
        this.valor = valor;
    }

    public void asignarValor(double valor){
        this.valor = String.valueOf(valor);
    }

    public void asignarValor(int valor){
        this.valor = String.valueOf(valor);
    }
}
