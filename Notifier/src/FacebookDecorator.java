public class FacebookDecorator extends NotifierDecorator {
    public FacebookDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public String send(String message) {
        return super.send(message) + "\n" + "FacebookAlert:" + message;
    }
}
