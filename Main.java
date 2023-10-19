import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        System.out.println(Hash.convertToBinary("hello world"));
    }
    
    public static void tryBlockchain() {
        ArrayList<Block> blockchain = new ArrayList<>();
    
        Person alice = new Person("Alice");
        Person bob = new Person("Bob");
        Person charlie = new Person("Charlie");
        
        Transaction[] genesisTransactions = {new Transaction(alice, bob, 10), new Transaction(bob, charlie, 5)};
        Block genesisBlock = new Block(0, genesisTransactions);
        System.out.println(genesisBlock.getBlockhash());
        blockchain.add(genesisBlock);
    
        Transaction[] block2Transations = {new Transaction(charlie, bob, 15), new Transaction(charlie, alice, 2)};
        Block block2 = new Block(genesisBlock.getBlockhash(), block2Transations);
        System.out.println(block2.getBlockhash());
        blockchain.add(block2);
    }
}