public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        System.out.println( "Hello World!" );

        StockMarket sm;
        Stock s1, s2, s3, s4;
        Trader t1, t2, t3;

        // Creando el mercado de valores (acciones) ----------------------------
        sm = new StockMarket();

        // Creando 4 acciones (Stocks) -----------------------------------------        

        s1 = new Stock("MSFT", 1.00, sm);
        s2 = new Stock("GOOG", 2.00, sm);
        s3 = new Stock("AAPL", 3.00, sm);
        s4 = new Stock("XXXX", 4.00, sm);

        // Creando 3 accionistas (Trader) ---------------------------------------

        t1 = new Trader("t1", sm);
        t2 = new Trader("t2", sm);
        t3 = new Trader("t3", sm);

        // Vinculando al accionista con la acción que le interesa observar --------        

        sm.register(t1, s1);
        sm.register(t1, s2);

        sm.register(t2, s1);
        sm.register(t2, s2);
        sm.register(t2, s4);

        sm.register(t3, s2);
        sm.register(t3, s3);
        sm.register(t3, s4);

        
        //sm.trade(t1,s1, "buy",1.00);
        //sm.trade(t2,s2, "sell",3.00);
        //sm.trade(t3,s3, "sell",2.50);
        sm.trade(t3,s4, "buy",5.00);
    
        
    }
}
