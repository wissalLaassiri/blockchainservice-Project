package com.enset.blockchainservice.repositories;

import com.enset.blockchainservice.entities.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, String> {
    Block findTopByOrderByCreationDateDesc();
}
