package com.wissallaassiri.blockchainservice.services;

import com.wissallaassiri.blockchainservice.entities.Block;
import com.wissallaassiri.blockchainservice.entities.Blockchain;
import com.wissallaassiri.blockchainservice.entities.Transaction;

public interface BlockchainService {
    Blockchain addNewBlockToBlockchain(int difficulty, int miningReward);
    Block addBlock(Blockchain chain, String miner);
    Transaction addTransaction(Transaction transaction);
    Block getLastBlock();
    boolean verifyBlockchain(Blockchain chain);
    double calculSolde(String address);
}
