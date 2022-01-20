package com.enset.blockchainservice.repositories;

import com.enset.blockchainservice.entities.Blockchain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockchainRepository extends JpaRepository<Blockchain, String> {
}
