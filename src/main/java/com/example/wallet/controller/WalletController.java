package com.example.wallet.controller;

import com.example.wallet.entity.Wallet;
import com.example.wallet.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("")
@Slf4j
public class WalletController {

    @Autowired
    private WalletService walletService;

    private final Object mutex = new Object();
    private final HashMap<Integer, Object> custIdMutex = new HashMap<>();

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

    /**
     * Api used to find the wallet balance of the customer
     * @param custId Id of the customer
     * @return Integer value of balance
     */
    @GetMapping("/getBalance")
    public int getBalance(@RequestParam int custId) {

        Wallet wallet = walletService.findByCustId(custId);
        return wallet.getWalletAmount();
    }

    /**
     * Deduct the amount from the wallet of the customer
     * @param custId Id of the customer
     * @param amount Amount to be deducted
     * @return Boolean value whether amount deducted
     */
    @GetMapping("/deductAmount")
    public boolean deductAmount(@RequestParam int custId,
                                @RequestParam int amount) {

        synchronized (mutex) {
            if (!custIdMutex.containsKey(custId)) {
                custIdMutex.put(custId, new Object());
            }
        }

        synchronized (custIdMutex.get(custId)) {
            if (amount <= 0) return false;

            Wallet wallet = walletService.findByCustId(custId);
            int walletAmount = wallet.getWalletAmount();
            if (walletAmount >= amount) {
                walletService.updateWallet(wallet.getCustId(), walletAmount - amount);
                return true;
            }
        }

        return false;
    }

    /**
     * Add the amount to the customer wallet
     * @param custId Id of the customer
     * @param amount Amount to be added
     * @return Boolean value whether amount is added
     */
    @GetMapping("/addAmount")
    public boolean addAmount(@RequestParam int custId,
                             @RequestParam int amount) {

        if(amount <= 0) return false;

        synchronized (mutex) {
            if (!custIdMutex.containsKey(custId)) {
                custIdMutex.put(custId, new Object());
            }
        }
        synchronized (custIdMutex.get(custId)) {
            Wallet wallet = walletService.findByCustId(custId);
            walletService.updateWallet(wallet.getCustId(), wallet.getWalletAmount() + amount);
        }
        return true;
    }

    /**
     * Reset the amount of wallet to initial amount
     */
    @GetMapping("/reset")
    public void reset() {

        walletService.updateAllWallet();
    }
}
