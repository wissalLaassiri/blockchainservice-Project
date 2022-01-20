package com.enset.blockchainservice;

import com.enset.blockchainservice.services.BlockService;
import com.enset.blockchainservice.services.BlockchainService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlockchainServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockchainServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BlockService blockService, BlockchainService blockchainService) {
        return args -> {
            try {
                blockchainService.addNewBlockToBlockchain(1, 200);

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

}
