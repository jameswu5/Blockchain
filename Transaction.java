import java.util.Locale;

public class Transaction {

    private Person sender;
    private Person receiver;
    private double value;

    public Transaction(Person sender, Person receiver, double value) {
        this.sender = sender;
        this.receiver = receiver;
        this.value = value;
    }

    public String toString() {
        return String.format(Locale.UK, "%s sends %s £%.2f", sender, receiver, value);
    }
}