package com.wissallaassiri.blockchainservice.services;

import com.wissallaassiri.blockchainservice.entities.Block;
import com.wissallaassiri.blockchainservice.entities.Transaction;
import com.wissallaassiri.blockchainservice.repositories.BlockRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class BlockServiceImpl implements BlockService {

    private BlockRepository blockRepository;

    Logger logger = LoggerFactory.getLogger(BlockServiceImpl.class);

    public BlockServiceImpl(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    @Override
    public Block saveBlock(String prevHash) {
        Block block = new Block();
        block.setId(UUID.randomUUID().toString());
        block.setPrevHash(prevHash);
        String hash = calculHashBloc(block);
        block.setHash(hash);
        block.setCreationDate(LocalDateTime.now());
        System.out.println("in service imp ============");
        System.out.println(block);
        return blockRepository.save(block);
    }

    @Override
    public String calculHashBloc(Block block) {
        int hashCodeTransactions = block.getTransactions() != null ? block.getTransactions().hashCode() : 0;
        System.out.println(hashCodeTransactions+"========");
        String str = String.valueOf(block.getPrevHash()+ block.getNonce()+ hashCodeTransactions);
        System.out.println(hashCodeTransactions);
        String hashStr = DigestUtils.sha256Hex(str);
        System.out.println(hashStr+"--------");
        String hash="";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
                    hashStr.getBytes(StandardCharsets.UTF_8));
            hash =  DatatypeConverter.printHexBinary(encodedhash);
            System.out.println(hash);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return hash;
    }

    @Override
    public void minerBlock(Block block, int difficulty) {
        String prefix = new String(new char[difficulty]).replace('\0', '0');
        while (!block.getHash().substring(0, difficulty).equals(prefix)) {
            block.setNonce(block.getNonce()+1);
            block.setHash(calculHashBloc(block));
        }
        blockRepository.save(block);
        logger.info("Bloc minee : "+block.getHash());
    }
}
