package com.enset.blockchainservice.services;

import com.enset.blockchainservice.entities.Block;
import com.enset.blockchainservice.entities.Blockchain;
import com.enset.blockchainservice.entities.Transaction;
import com.enset.blockchainservice.repositories.BlockRepository;
import com.enset.blockchainservice.repositories.BlockchainRepository;
import com.enset.blockchainservice.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BlockchainServiceImpl implements BlockchainService {
    private BlockService blockService;
    private BlockRepository blockRepository;
    private BlockchainRepository blockchainRepository;
    private TransactionRepository transactionRepository;

    public BlockchainServiceImpl(BlockService blockService, BlockRepository blockRepository,
                                 BlockchainRepository blockchainRepository, TransactionRepository transactionRepository) {
        this.blockService = blockService;
        this.blockRepository = blockRepository;
        this.blockchainRepository = blockchainRepository;
        this.transactionRepository = transactionRepository;
    }

    private final List<Transaction> pendingTransactions = new ArrayList<>();

    @Override
    public Blockchain addNewBlockToBlockchain(int difficulty, int miningReward) {
        Blockchain chain = new Blockchain();
        chain.setId(UUID.randomUUID().toString());
        chain.setNom("Blockchain");
        chain.setDifficulty(difficulty);
        chain.setMiningReward(miningReward);
        List<Block> blocks = new ArrayList<>();
        Block genesisBlock = blockService.saveBlock("0");
        blocks.add(genesisBlock);

        chain.setBlocks(blocks);
        return blockchainRepository.save(chain);
    }

    @Override
    public Block addBlock(Blockchain chain, String miner) {
        // Ajouter une transaction bonus à l'adresse du mineur
        Transaction bonus = new Transaction("casa", miner, chain.getMiningReward());
        pendingTransactions.add(bonus);
        transactionRepository.save(bonus);

        Block lastBlock = getLastBlock();
        String previousHash = lastBlock.getHash();
        Block newBlock = blockService.saveBlock(previousHash);
        newBlock.setTransactions(new ArrayList<>(pendingTransactions));
        blockService.minerBlock(newBlock, chain.getDifficulty());
        pendingTransactions.clear();
        chain.getBlocks().add(newBlock);
        blockRepository.save(newBlock);
        blockchainRepository.save(chain);

        return newBlock;
    }

    @Override
    public Transaction addTransaction(Transaction transaction) {
        pendingTransactions.add(transaction);
        return transaction;
    }

    @Override
    public Block getLastBlock() {
        return blockRepository.findTopByOrderByCreationDateDesc();
    }

    @Override
    public boolean verifyBlockchain(Blockchain chain) {
        boolean isValid = true;
        List<Block> blocks = chain.getBlocks();
        for (int i = 0; i < blocks.size(); i++) {
            String previousHash = i == 0 ? "0" : blocks.get(i - 1).getHash();
            isValid = blocks.get(i).getHash().equals(blockService.calculHashBloc(blocks.get(i)))
                    && previousHash.equals(blocks.get(i).getPrevHash());
            if (!isValid)
                break;
        }
        return isValid;
    }

    @Override
    public double calculSolde(String address) {
        return transactionRepository.calculerSolde(address);
    }
}
