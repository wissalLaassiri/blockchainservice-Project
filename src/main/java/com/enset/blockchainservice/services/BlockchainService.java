package com.enset.blockchainservice.services;

import com.enset.blockchainservice.entities.Block;
import com.enset.blockchainservice.entities.Blockchain;
import com.enset.blockchainservice.entities.Transaction;

public interface BlockchainService {
    Blockchain addNewBlockToBlockchain(int difficulty, int miningReward);
    Block addBlock(Blockchain chain, String miner);
    Transaction addTransaction(Transaction transaction);
    Block getLastBlock();
    boolean verifyBlockchain(Blockchain chain);
    double calculSolde(String address);
}
