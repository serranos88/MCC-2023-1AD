public class emailNotifier implements Notifier {
    @Override
    public String send(String message) {
        return "emailAlert:" + message;
    }
}
