package com.wissallaassiri.blockchainservice.repositories;

import com.wissallaassiri.blockchainservice.entities.Blockchain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockchainRepository extends JpaRepository<Blockchain, String> {
}
