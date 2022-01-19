package com.wissallaassiri.blockchainservice.services;

import com.wissallaassiri.blockchainservice.entities.Block;

public interface BlockService {
    Block saveBlock(String prevHash);
    String calculHashBloc(Block block);
    void minerBlock(Block block,int diff);
}
