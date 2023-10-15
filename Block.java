import java.util.Arrays;

public class Block {
    private int previousHash;
    private Transaction[] transactions;

    private int blockHash;

    public Block(int previousHash, Transaction[] transactions) {
        this.previousHash = previousHash;
        this.transactions = transactions;

        // I'm not a fan of converting it to strings, but this will make do until I create my own hash function
        String[] transactionStrings = new String[transactions.length];
        for (int i = 0; i < transactions.length; i++) {
            transactionStrings[i] = transactions[i].toString();
        }

        Object[] contens = {Arrays.hashCode(transactionStrings), previousHash};
        this.blockHash = Arrays.hashCode(contens);
    }

    public int getPreviousHash() {
        return previousHash;
    }
    
    public Transaction[] getTransactions() {
        return transactions;
    }

    public int getBlockhash() {
        return blockHash;
    }
}