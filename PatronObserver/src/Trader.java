

public class Trader implements Observer {
    private String nombre;
    private StockMarket mercado_acciones;
    

	public Trader(String nombre, StockMarket mercado_acciones) {
        this.nombre = nombre;
        this.mercado_acciones = mercado_acciones;
    }

    // MEJORA: agregar un update al precio actual del stock
    public void comprar(Stock s, double precio) {
        mercado_acciones.trade(this, s, "buy", precio);
    }

    // MEJORA: agregar un update al precio actual del stock
    public void vender(Stock s, double precio) {
        mercado_acciones.trade(this, s, "sell", precio);
    }


    public void actualizar(String transaccionLog) {
        System.out.print(nombre+":"+transaccionLog);
    }


    public String getNombre() {
        return nombre;
    }
    
}