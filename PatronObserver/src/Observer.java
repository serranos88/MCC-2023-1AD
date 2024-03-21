
public interface Observer {
    public void actualizar(String transaccion);

    // No es parte del patron de diseño 
    //pero se agrega para poder imprimir el nombre del trader
    // y coincidir con el TEST de la evaluacion
    public String getNombre();

}
