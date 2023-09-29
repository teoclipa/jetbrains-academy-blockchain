package blockchain;

public class Transaction {
    final String sender;
    final String recipient;
    final int amount;

    public Transaction(String sender, String recipient, int amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return sender + " sent " + amount + " VC to " + recipient;
    }
}
