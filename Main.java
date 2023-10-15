import java.util.ArrayList;

public class Main {
    
    ArrayList<Block> blockchain = new ArrayList<>();

    public static void main(String[] args) {

        String[] genesisTransactions = {"I sent 10 coins", "She sent 5 coins"};
        Block genesisBlock = new Block(0, genesisTransactions);

        System.out.println(genesisBlock.getBlockhash());

        String[] block2Transations = {"She sent 10 coins", "He sent 1 coin"};
        Block block2 = new Block(genesisBlock.getBlockhash(), block2Transations);

        System.out.println(block2.getBlockhash());
    }
}