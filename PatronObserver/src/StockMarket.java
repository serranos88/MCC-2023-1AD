
import java.util.HashMap;


public class StockMarket {
	//ArrayList<Trader> traders;
    HashMap<String,Stock> stocks;
    


    public StockMarket() {
        //traders = new ArrayList<Trader>();
        stocks = new HashMap<String,Stock>();

	}

	public void register(Trader t, Stock s) {
            //stocks.get(s.getTipo()).registrarObservador(t); //agregar el trader a la lista de observadores del stock
            
            stocks.put(s.getTipo(),s).registrarObservador(t);
            System.out.println("Agregando observador al stock existente " + s.getTipo() + " " + s.getObservadores().size() +" " + t.getNombre());
            
	}

	public String trade(Trader t, Stock s, String tipo_transaccion, double precio) {
		String log = "\n";
        if(stocks.containsKey(s.getTipo())){
                   
                    String info = "The latest trade is Trader:"+t.getNombre()+ " "+tipo_transaccion+ " $" + precio +" Stock: " + s.getTipo() + "\n";
                    s.notificarObservadores(info); 
                    for (Observer o : s.getObservadores()) {
                        log += o.getNombre()+":"+info;
                    }
           }else{
             System.out.println("El stock no existe");
           }

            System.out.print(log);
		return log;
	}

    public void agregarStock(Stock s) {
        stocks.put(s.getTipo(),s);
    }
    
}