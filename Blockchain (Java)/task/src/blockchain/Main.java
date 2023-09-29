package blockchain;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Blockchain blockchain = Blockchain.getInstance();
        List<Thread> miners = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Miner miner = new Miner(i, blockchain);
            Thread minerThread = new Thread(miner);
            miners.add(minerThread);
            minerThread.start();
        }

        while (blockchain.isMining()) {
            // Busy-wait until all 15 blocks are mined
        }

        for (Thread minerThread : miners) {
            try {
                minerThread.join();  // Ensure all miner threads are finished before exiting
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}