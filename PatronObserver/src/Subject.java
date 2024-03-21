

public interface Subject {
    public void registrarObservador(Observer observer);
    public void eliminarObservador(Observer observer);
    public void notificarObservadores(String transaccionLog);
}
