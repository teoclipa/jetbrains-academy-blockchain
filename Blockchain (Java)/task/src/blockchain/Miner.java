package blockchain;

public class Miner implements Runnable {
    private final int minerId;
    private final Blockchain blockchain;

    public Miner(int minerId, Blockchain blockchain) {
        this.minerId = minerId;
        this.blockchain = blockchain;
    }

    @Override
    public void run() {
        while (blockchain.isMining()) {
            Block block = blockchain.jobQueue.poll();
            if (block != null) {
                if (block.getMinerId() == -1) {
                    block.setMinerId(getMinerId());
                }
                blockchain.proofOfWork(block);
                blockchain.addBlock(block);

                // Transactions are just examples. They can be generated and added differently.
                blockchain.addTransaction("miner" + minerId, "miner" + (minerId + 1) % 10, 10);
            }
        }
    }

    public int getMinerId() {
        return minerId;
    }
}