package com.example.wallet.controller;

import com.example.wallet.entity.Wallet;
import com.example.wallet.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@Slf4j
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/")
    public Wallet saveWallet(@RequestBody Wallet wallet) {

        return walletService.save(wallet);
    }

    @GetMapping("/balance")
    public int getBalance(@RequestParam int custId) {

        // return current wallet balance of custId

        return 2;
    }

    @GetMapping("/deduct-amount")
    public boolean deductAmount(@RequestParam int custId,
                                @RequestParam int amount) {

        // If custId has balance >= amount, then reduce their balance and return true else false.
        // used by rideservice.requestRide

        // isolation

        return true;
    }

    @GetMapping("/add-amount")
    public boolean addAmount(@RequestParam int custId,
                             @RequestParam int amount) {

        //isolation

        return true;
    }

    @GetMapping("/reset")
    public void reset() {

        // reset balance of all customers to the initial balance as given in txt file.
        // for testing
    }
}
