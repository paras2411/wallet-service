package com.example.wallet.service;

import com.example.wallet.entity.Wallet;
import com.example.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public Wallet save(Wallet wallet) {

        return walletRepository.save(wallet);
    }

    public Wallet findByCustId(int custId) {
        return walletRepository.findByCustId(custId);
    }

    public void addWallet(int custId, int amount) {
        walletRepository.addWallet(custId, amount);
    }

    public void updateWallet(int custId, int amountUpdate) {
        walletRepository.updateWallet(custId, amountUpdate);
    }
}
