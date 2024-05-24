public class MSTeamsDecorator extends NotifierDecorator {
    public MSTeamsDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public String send(String message) {
        return super.send(message) + "\n" + "MSTeamsAlert:" + message;
    }
}
