import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class StockMarket {
	//ArrayList<Trader> traders;
    HashMap<String,Stock> stocks;
    


    public StockMarket() {
        //traders = new ArrayList<Trader>();
        stocks = new HashMap<String,Stock>();

	}

	public void register(Trader t, Stock s) {
        //traders.add(t);
        //stocks.add(s);
        if(stocks.containsKey(s.getTipo()) ){
            stocks.get(s.getTipo()).registrarObservador(t); //agregar el trader a la lista de observadores del stock
        } else {
            System.out.println("El stock no existe");
        }
	}

	public String trade(Trader t, Stock s, String tipo_transaccion, double precio) {
		String log = "";
            //for (Observer o : s.getObservadores()) {

                    //trader.actualizar(":The latest trade is Trader:"+t.getNombre()+ " "+tipo_transaccion+ " $" + precio +" Stock: " + s.getTipo() + "\n");
                   
                    String info = "The latest trade is Trader:"+t.getNombre()+ " "+tipo_transaccion+ " $" + precio +" Stock: " + s.getTipo() + "\n";
                    s.notificarObservadores(info); 
                    for (Observer o : s.getObservadores()) {
                        log += o.getNombre()+":"+info;
                    }
           // }

            //System.out.print(log);
		return log;
	}

    public void agregarStock(Stock s) {
        stocks.put(s.getTipo(),s);
    }
    
}