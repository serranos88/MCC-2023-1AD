

import java.util.HashMap;
import java.util.ArrayList;


public class StockMarket {
	ArrayList<Stock> stocks;
    

    public StockMarket() {
        stocks = new ArrayList<Stock>();

	}

	public void register(Trader t, Stock s) {
            stocks.contains(s);
            int index = stocks.indexOf(s);
            stocks.get(index).registrarObservador(t);
            
	}

	public String trade(Trader t, Stock s, String tipo_transaccion, double precio) {
		String log = "";
        if (stocks.contains(s)) {
            
					String info = "The latest trade is Trader:"+t.getNombre()+ " "+tipo_transaccion+ " $" + precio +" Stock: " + s.getTipo() + "\n";
                    s.notificarObservadores(info); 
                    for (Observer o : s.getObservadores()) {
                        log += o.getNombre()+":"+info;
                    }
           }else{
             System.out.print("El stock no existe");
           }
		   // Eliminar el salto de linea al final de log
			if (log.length() > 0) {
				log = log.substring(0, log.length() - 1);
			}
            //System.out.print(log);
		return log;
	}

    public void agregarStock(Stock s) {
       
        stocks.add(s);
    }
    
}