public class SMSDecorator extends NotifierDecorator {
    public SMSDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public String send(String message) {
        return super.send(message) + "\n" + "SMSAlert:" + message;
    }
}
