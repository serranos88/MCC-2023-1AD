public class App {
    public static void main(String[] args) throws Exception {
        Notifier n;
        n = new emailNotifier();
        n = new SMSDecorator(n);
        n = new FacebookDecorator(n);
        n = new MSTeamsDecorator(n);

        String notificacion;
        
        notificacion = n.send("Se quema la casa!");

        System.out.println(notificacion);

        

    }
}
