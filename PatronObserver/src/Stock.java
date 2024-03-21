import java.util.ArrayList;

public class Stock implements Subject {
    private String tipo;
    private double precio_inicial;
    //private StockMarket mercado_acciones;
    private ArrayList<Observer> observadores;
    private ArrayList<String> transacciones;

	public Stock(String tipo, double precio_inicial, StockMarket mercado_acciones) {
        this.tipo = tipo;
        this.precio_inicial = precio_inicial;
        //this.mercado_acciones = mercado_acciones;
        mercado_acciones.agregarStock(this);
        observadores = new ArrayList<Observer>();
    }

    @Override
    public void registrarObservador(Observer observer) {
        observadores.add(observer);
    }
    
    @Override
    public void eliminarObservador(Observer observer) {
        observadores.remove(observer);
    }

    @Override
    public void notificarObservadores(String transaccionLog) {
        for (Observer observer : observadores) {
            observer.actualizar(transaccionLog);
        }

    }


    public double getPrecio() {
        return precio_inicial;
    }

    public String getTipo() {
        return tipo;
    }

    public ArrayList<Observer> getObservadores() {
        return observadores;
    }

    
}

    
    
