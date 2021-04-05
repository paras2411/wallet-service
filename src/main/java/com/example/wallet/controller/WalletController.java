package com.example.wallet.controller;

import com.example.wallet.entity.Wallet;
import com.example.wallet.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@Slf4j
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/")
    public Wallet saveWallet(@RequestBody Wallet wallet) {

        return walletService.save(wallet);
    }

    @GetMapping("/feedInitialData")
    public void addInitialCustomers(){
        walletService.feedInitialData();
    }

    @GetMapping("/addCustomer")
    public void addCustomer(@RequestParam int custId) {
        walletService.addCustomer(custId);
    }

    @GetMapping("/getBalance")
    public int getBalance(@RequestParam int custId) {

        Wallet wallet = walletService.findByCustId(custId);
        return wallet.getWalletAmount();
    }

    @GetMapping("/deductAmount")
    public boolean deductAmount(@RequestParam int custId,
                                @RequestParam int amount) {

        if(amount <= 0) return false;

        Wallet wallet = walletService.findByCustId(custId);
        int walletAmount = wallet.getWalletAmount();
        if(walletAmount >= amount) {
            walletService.updateWallet(wallet.getCustId(), walletAmount - amount);
            return true;
        }
        return false;
    }

    @GetMapping("/addAmount")
    public boolean addAmount(@RequestParam int custId,
                             @RequestParam int amount) {

        if(amount <= 0) return false;

        Wallet wallet = walletService.findByCustId(custId);
        walletService.updateWallet(wallet.getCustId(), wallet.getWalletAmount() + amount);
        return true;
    }

    @GetMapping("/reset")
    public void reset() {

        walletService.updateAllWallet();
    }
}
