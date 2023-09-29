# Simplified Blockchain Simulation

The project is a simplified simulation of a blockchain implemented in Java, demonstrating the core concepts of blockchain technology including block creation, hashing, proof of work, and transaction management within a basic decentralized network where multiple miners compete to add new blocks to the blockchain.

## Core Components

### 1. Block:
- The `Block` class represents a block in the blockchain, containing fields such as `id`, `timestamp`, `previousHash`, `hash`, `minerId`, `transactions`, `magicNumber`, and `minerReward`.
- It also contains methods to calculate the string for hashing and print block details.

### 2. Blockchain:
- A singleton `Blockchain` class serves as the core of the blockchain, managing block creation, transaction buffer, difficulty adjustment, and proof of work.
- It initiates the genesis block and manages a job queue for miners.

### 3. Transaction:
- The `Transaction` class represents a transaction with fields `sender`, `recipient`, and `amount`.

### 4. Miner:
- Miners are simulated through the `Miner` class which implements `Runnable` for parallel block mining.
- Each miner attempts to mine blocks from the blockchain's job queue, and upon successful mining, adds the block back to the blockchain.

### 5. SHA-256 Hashing:
- Utilizes a `StringUtil` class to apply SHA-256 hashing to strings, crucial for block hashing and proof of work.

### 6. Proof of Work:
- Implements a simplified proof-of-work algorithm where miners compete to find a hash with a prefix of zeros, the number of zeros being determined by the current difficulty level.

### 7. Difficulty Adjustment:
- The difficulty level adjusts dynamically based on the time taken to mine the previous block, aiming to maintain a consistent block generation time.

### 8. Concurrent Mining:
- Utilizes concurrency to simulate multiple miners working simultaneously to mine new blocks.

### 9. Transaction Management:
- Manages a buffer of transactions, which are added to new blocks as they are mined.

## Simulation Flow

- The simulation initiates with the creation of a genesis block.
- Multiple miner threads are launched to concurrently mine new blocks.
- Miners pick blocks from a job queue, perform proof of work to mine the blocks, and then add them back to the blockchain.
- Transactions are generated and added to a buffer, which are then included in newly mined blocks.
- The simulation continues until a predefined maximum block size is reached.

## Objective

The objective of this simulation is to provide a hands-on understanding of how a blockchain operates, the role of miners, and how transactions are added to blocks. Through this simplified simulation, users can grasp the core concepts and mechanisms underlying blockchain technology.
