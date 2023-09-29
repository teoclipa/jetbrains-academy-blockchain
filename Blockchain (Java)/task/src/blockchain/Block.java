package blockchain;
import java.util.List;

public class Block {
    private final int id;
    private final long timestamp;
    private final String previousHash;
    private int minerId;
    private String hash;
    final List<Transaction> transactions;
    private long magicNumber;

    public Block(int id, long timestamp, String previousHash, int minerId, List<Transaction> transactions) {
        this.id = id;
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        this.minerId = minerId;
        this.transactions = transactions;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setMinerId(int minerId) {
        this.minerId = minerId;
    }

    public int getMinerId() {
        return minerId;
    }

    public void setMagicNumber(long magicNumber) {
        this.magicNumber = magicNumber;
    }

    public String calculateStringForHash() {
        String transactionData = transactions.stream()
                .map(Transaction::toString)
                .reduce("", (acc, transaction) -> acc + transaction);
        return id + timestamp + previousHash + magicNumber + transactionData;
    }

    public void printBlock(long generationTime, int difficulty) {
        System.out.println("Block:");
        System.out.println("Created by: miner" + minerId);
        System.out.println("miner" + minerId + " gets 100 VC");
        System.out.println("Id: " + id);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Magic number: " + magicNumber);
        System.out.println("Hash of the previous block: \n" + previousHash);
        System.out.println("Hash of the block: \n" + hash);
        System.out.println("Block data:");
        if (transactions.isEmpty()) {
            System.out.println("No transactions");
        } else {
            transactions.forEach(transaction -> System.out.println(transaction.toString()));
        }
        System.out.println("Block was generating for " + generationTime + " seconds");

        if (difficulty < 0) {
            System.out.println("N was decreased by 1\n");
        } else if (difficulty > 0) {
            System.out.println("N was increased to " + difficulty + "\n");
        } else {
            System.out.println("N stays the same\n");
        }
    }

    public int getId() {
        return id;
    }

    public String getHash() {
        return hash;
    }
}