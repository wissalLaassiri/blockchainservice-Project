package com.wissallaassiri.blockchainservice.repositories;

import com.wissallaassiri.blockchainservice.entities.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, String> {
    Block findTopByOrderByCreationDateDesc();
}
