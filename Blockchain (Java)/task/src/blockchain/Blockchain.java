package blockchain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Blockchain {
    private static final int MAX_BLOCK_SIZE = 15;
    private static final Blockchain INSTANCE = new Blockchain();
    private final List<Block> blocks = new ArrayList<>();
    private final AtomicInteger difficulty = new AtomicInteger(0);
    private final List<Transaction> transactionBuffer = new ArrayList<>();
    public final ConcurrentLinkedQueue<Block> jobQueue = new ConcurrentLinkedQueue<>();
    private volatile boolean mining = true;

    private Blockchain() {
        createGenesisBlock();
    }


    public static Blockchain getInstance() {
        if (INSTANCE == null) {
            return new Blockchain();
        }
        return INSTANCE;
    }

    public void createGenesisBlock() {
        Block genesisBlock = new Block(1, new Date().getTime(), "0", -1, new ArrayList<>());  // -1 indicates an uninitialized minerId
        jobQueue.add(genesisBlock);
    }

    public synchronized void addTransaction(String sender, String recipient, int amount) {
        if (getBalance(sender) >= amount) {
            transactionBuffer.add(new Transaction(sender, recipient, amount));
        }
    }

    public int getBalance(String user) {
        int balance = user.equals("miner" + blocks.get(blocks.size() - 1).getMinerId()) ? 100 : 0;
        for (Block block : blocks) {
            for (Transaction transaction : block.transactions) {
                if (transaction.sender.equals(user)) {
                    balance -= transaction.amount;
                }
                if (transaction.recipient.equals(user)) {
                    balance += transaction.amount;
                }
            }
        }
        return balance;
    }

    public synchronized void addBlock(Block block) {
        blocks.add(block);
        if (blocks.size() >= MAX_BLOCK_SIZE) {
            mining = false;
        } else {
            // Create a new block and add it to the jobQueue for miners to process
            Block latestBlock = getLatestBlock();
            Block newBlock = new Block(latestBlock.getId() + 1, new Date().getTime(), latestBlock.getHash(), -1, new ArrayList<>(transactionBuffer));
            jobQueue.add(newBlock);
            transactionBuffer.clear();  // Clear the message buffer for next block

        }
    }


    public void adjustDifficulty(long generationTime) {
        if (generationTime < 1) {
            difficulty.incrementAndGet();
        } else if (generationTime >= 60) {
            difficulty.decrementAndGet();
        }
    }

    public Block getLatestBlock() {
        return blocks.get(blocks.size() - 1);
    }

    public boolean isMining() {
        return mining;
    }

    public synchronized void proofOfWork(Block block) {
        String prefixString = new String(new char[difficulty.get()]).replace('\0', '0');
        long magicNumber = 0;
        long startTime = System.currentTimeMillis();
        while (true) {
            String hashInput = block.calculateStringForHash() + magicNumber;
            String hash = StringUtil.applySha256(hashInput);
            if (hash.substring(0, difficulty.get()).equals(prefixString)) {
                block.setHash(hash);
                block.setMagicNumber(magicNumber);
                long endTime = System.currentTimeMillis();
                //dont adjust for tests, they need to run fast
                adjustDifficulty((endTime - startTime) / 1000);
                block.printBlock((endTime - startTime) / 1000, difficulty.get());
                break;
            }
            magicNumber++;
        }
    }
}
