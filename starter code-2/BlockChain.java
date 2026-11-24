import java.util.ArrayList;
import java.util.HashMap;

/* Block Chain should maintain only limited block nodes to satisfy the functions
   You should not have the all the blocks added to the block chain in memory 
   as it would overflow memory
 */

public class BlockChain {
   public static final int CUT_OFF_AGE = 10;

   private HashMap<ByteArrayWrapper, BlockNode> blockMap = new HashMap<>();
   private BlockNode maxHeightNode = null;
   private TransactionPool txPool = new TransactionPool();

   // all information required in handling a block in block chain
   private class BlockNode {
      public Block b;
      public BlockNode parent;
      public ArrayList<BlockNode> children;
      public int height;
      // utxo pool for making a new block on top of this block
      private UTXOPool uPool;

      public BlockNode(Block b, BlockNode parent, UTXOPool uPool) {
         this.b = b;
         this.parent = parent;
         children = new ArrayList<BlockNode>();
         this.uPool = uPool;
         if (parent != null) {
            height = parent.height + 1;
            parent.children.add(this);
         } else {
            height = 1;
         }
      }

      public UTXOPool getUTXOPoolCopy() {
         return new UTXOPool(uPool);
      }
   }

   // TODO(Member Khoa Do): Implement the constructor of BlockChain
   /* create an empty block chain with just a genesis block.
    * Assume genesis block is a valid block
    */
   public BlockChain(Block genesisBlock) {
      throw new UnsupportedOperationException("TODO: Member Khoa Do");
   }

   /* Get the maximum height block
    */
   public Block getMaxHeightBlock() {
      return maxHeightNode.b;
   }
   
   /* Get the UTXOPool for mining a new block on top of 
    * max height block
    */
   public UTXOPool getMaxHeightUTXOPool() {
      return maxHeightNode.getUTXOPoolCopy();
   }
   
   /* Get the transaction pool to mine a new block
    */
   public TransactionPool getTransactionPool() {
      return txPool;
   }

   // TODO(Member 2): Implement block acceptance.
   /* Add a block to block chain if it is valid.
    * For validity, all transactions should be valid
    * and block should be at height > (maxHeight - CUT_OFF_AGE).
    * For example, you can try creating a new block over genesis block 
    * (block height 2) if blockChain height is <= CUT_OFF_AGE + 1. 
    * As soon as height > CUT_OFF_AGE + 1, you cannot create a new block at height 2.
    * Return true of block is successfully added
    */
   public boolean addBlock(Block b) {
      if (b == null || b.getHash() == null || b.getPrevBlockHash() == null)
         return false;
   
      ByteArrayWrapper blockHash = new ByteArrayWrapper(b.getHash());
      if (blockMap.containsKey(blockHash))
         return false;
   
      BlockNode parent = blockMap.get(new ByteArrayWrapper(b.getPrevBlockHash()));
      if (parent == null)
         return false;
   
      int newHeight = parent.height + 1;
      if (newHeight <= maxHeightNode.height - CUT_OFF_AGE)
         return false;
   
      UTXOPool newPool = validateBlockTransactions(b, parent.getUTXOPoolCopy());
      if (newPool == null)
         return false;
   
      BlockNode node = new BlockNode(b, parent, newPool);
      blockMap.put(blockHash, node);
   
      if (node.height > maxHeightNode.height)
         maxHeightNode = node;
   
      removeIncludedTransactions(b);
      pruneOldBlocks();
      return true;
   }

   // TODO(Member 3): Add transaction to global transaction pool.
   /* Add a transaction in transaction pool
    */
   public void addTransaction(Transaction tx) {
      throw new UnsupportedOperationException("TODO: Member 3");
   }

   // TODO(Member 2): Implement obsolete block pruning.
   private void pruneOldBlocks() {
      int cutoffHeight = maxHeightNode.height - CUT_OFF_AGE;
      ArrayList<ByteArrayWrapper> obsoleteHashes =
            new ArrayList<ByteArrayWrapper>();
      ArrayList<BlockNode> retainedNodes = new ArrayList<BlockNode>();
   
      for (ByteArrayWrapper hash : blockMap.keySet()) {
         BlockNode node = blockMap.get(hash);
         if (node.height < cutoffHeight)
            obsoleteHashes.add(hash);
         else
            retainedNodes.add(node);
      }
   
      for (BlockNode node : retainedNodes) {
         if (node.parent != null && node.parent.height < cutoffHeight) {
            node.parent.children.remove(node);
            node.parent = null;
         }
      }
   
      for (ByteArrayWrapper hash : obsoleteHashes) {
         BlockNode obsolete = blockMap.remove(hash);
         if (obsolete.parent != null)
            obsolete.parent.children.remove(obsolete);
         obsolete.children.clear();
      }
   }

   // TODO(Member 3): Validate block transactions and compute UTXO pool.
   private UTXOPool validateBlockTransactions(Block b, UTXOPool parentPool) {
      throw new UnsupportedOperationException("TODO: Member 3");
   }

   // TODO(Member 3): Remove transactions included transactions from global pool.
   private void removeIncludedTransactions(Block b) {
      throw new UnsupportedOperationException("TODO: Member 3");
   }
}
