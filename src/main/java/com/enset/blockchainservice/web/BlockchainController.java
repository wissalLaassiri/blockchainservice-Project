package com.enset.blockchainservice.web;

import com.enset.blockchainservice.repositories.BlockchainRepository;
import com.enset.blockchainservice.repositories.TransactionRepository;
import com.enset.blockchainservice.services.BlockchainService;
import com.enset.blockchainservice.entities.Block;
import com.enset.blockchainservice.entities.Blockchain;
import com.enset.blockchainservice.entities.Transaction;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BlockchainController {
    private BlockchainRepository blockchainRepository;
    private BlockchainService blockchainService;
    private TransactionRepository transactionRepository;

    public BlockchainController(BlockchainRepository blockchainRepository, BlockchainService blockchainService,
                                TransactionRepository transactionRepository) {
        this.blockchainRepository = blockchainRepository;
        this.blockchainService = blockchainService;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/chain")
    public Blockchain displayChain() {
        List<Blockchain> blockchainList = blockchainRepository.findAll();
        if (blockchainList.size() > 0)
            return blockchainList.get(0);
        return null;
    }

    @GetMapping("/mine")
    public Block mine(@RequestParam String miner) {
        Block block = blockchainService.addBlock(blockchainRepository.findAll().get(0), miner);
        return block;
    }

    @PostMapping("/creer_transaction")
    public Transaction creerTransaction(@RequestBody Transaction transaction) {
        transaction = new Transaction(transaction.getSrcAddress(),
                transaction.getDestAddress(),
                transaction.getAmount());
        blockchainService.addTransaction(transaction);
        return transactionRepository.save(transaction);
    }

    @GetMapping("/solde/{adr}")
    public String getSoldeByAddress(@PathVariable String adr) {
        return String.valueOf(transactionRepository.calculerSolde(adr));
    }

}
