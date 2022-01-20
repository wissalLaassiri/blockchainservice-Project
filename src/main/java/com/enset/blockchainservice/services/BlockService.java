package com.enset.blockchainservice.services;

import com.enset.blockchainservice.entities.Block;

public interface BlockService {
    Block saveBlock(String prevHash);
    String calculHashBloc(Block block);
    void minerBlock(Block block,int diff);
}
